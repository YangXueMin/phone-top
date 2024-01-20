package com.ruoyi.common.utils.file;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Copy;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author yangxuemin
 * @ClassName TxCosUtil
 * @Description
 * @date 2024/1/1 9:54 PM
 */
public class TxCosUtils {
    /*配置参数自行填写*/
    private static final String APP_ID = "1323517301";
    private static final String SECRET_ID = "AKIDim3PvVJRsJi5jT5Zs19CB464zlS2i1Fg";
    private static final String SECRET_KEY = "KuVWkZN7uEavDWadfYM2WowuiKkwpa8x";
    private static final String REGION_NAME = "ap-beijing";
    private static final String BUCKET_NAME = "ygmfood";
    public static final String URL = "https://image.ygmfood.com/";

    private TxCosUtils() {
    }

    /**
     * 方法描述：获取cos存储桶客户端.
     * 创建时间：2019-02-27 18:09:46
     *
     * @author "lixingwu"
     */
    private static COSClient getCOSClient() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者接口文档 FAQ 中说明。
        ClientConfig clientConfig = new ClientConfig(new Region(REGION_NAME));
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    /**
     * 方法描述：bucket 的命名规则为{name}-{appid} ，
     * name 仅支持小写字母、数字和 - 的组合，不能超过40字符.
     * 创建时间：2019-02-27 18:09:46
     *
     * @author "lixingwu"
     */
    private static String getBucketName(String bucketName) {
        Validator.validateNotEmpty(bucketName, "bucketName不能为空");
        Validator.validateMatchRegex("^[a-z!-@0-9]+$", bucketName,
                "bucketName仅支持小写字母、数字和 - 的组合");
        if (bucketName.length() > 40) {
            throw new ValidateException("bucketName不能超过40字符");
        }
        return (bucketName + "-" + APP_ID);
    }

    /**
     * 方法描述：创建一个存储桶.
     * 创建时间：2019-02-27 18:09:46
     *
     * @author "lixingwu"
     */
    public static Bucket createBucket(String bucketName) throws CosClientException {
        Bucket bucket = null;
        COSClient client = getCOSClient();
        bucket = createBucket(getBucketName(bucketName));
        //设置存储桶的权限为 公有读私有写
        if (null != bucket) {
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        return bucket;
    }

    /**
     * 方法描述：获取存储桶列表.
     * 创建时间：2019-02-27 18:09:46
     *
     * @author "lixingwu"
     */
    public static List<Bucket> listBuckets() throws CosClientException {
        return getCOSClient().listBuckets();
    }

    /**
     * 方法描述：检测存储桶是否存在.
     * 创建时间：2019-02-27 18:09:46
     *
     * @author "lixingwu"
     */
    public static boolean doesBucketExist(String bucketName) throws CosClientException {
        return getCOSClient().doesBucketExist(getBucketName(bucketName));
    }

    /**
     * 方法描述：删除文件.
     * 创建时间：2019-03-05 14:25:08
     * 创建作者：李兴武
     *
     * @param fileName 文件地址
     * @throws CosClientException the cos client exception
     * @author "lixingwu"
     */
    public static void deleteObject(String fileName)
            throws CosClientException {
        deleteObject(BUCKET_NAME, fileName);
    }

    /**
     * 方法描述：删除文件.
     * 创建时间：2019-03-05 14:25:08
     * 创建作者：李兴武
     *
     * @param bucketName bucketName
     * @param fileName   文件地址
     * @throws CosClientException the cos client exception
     * @author "lixingwu"
     */
    public static void deleteObject(String bucketName, String fileName)
            throws CosClientException {
        getCOSClient().deleteObject(bucketName, fileName);
    }

    /**
     * 方法描述：获取文件的信息.
     * 创建时间：2019-03-05 14:54:09
     * 创建作者：李兴武
     *
     * @param bucketName bucketName
     * @param fileName   文件地址
     * @return the object metadata
     * @throws CosClientException the cos client exception
     * @author "lixingwu"
     */
    public static ObjectMetadata findObjectMetadata(String bucketName, String fileName)
            throws CosClientException {
        return getCOSClient().getObjectMetadata(getBucketName(bucketName), fileName);
    }

    /**
     * 方法描述：获取一个高级的API TransferManager，项目中应该使用这些接口.
     * 创建时间：2019-03-05 15:35:12
     * 创建作者：李兴武
     *
     * @return the transfer manager
     * @author "lixingwu"
     */
    public static TransferManager cosTransferManager() {
        ExecutorService threadPool = ThreadUtil.newExecutor();
        return new TransferManager(getCOSClient(), threadPool);
    }

    /**
     * 方法描述：上传文件
     * 创建时间：2019-03-05 15:53:54
     * 创建作者：李兴武
     *
     * @param bucketName 存储桶名称
     * @param filePath   文件存储地址
     * @param stream     文件流
     * @return the upload
     * @author "lixingwu"
     */
    public static Upload upload(String bucketName, String filePath, InputStream stream) {
        TransferManager manager = cosTransferManager();
        Upload result = null;
        try {
            result = manager.upload(getBucketName(bucketName), filePath, stream, new ObjectMetadata());
            result.waitForUploadResult();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            manager.shutdownNow();
        }
        return result;
    }

    /**
     * 方法描述：上传文件
     * 创建时间：2019-03-05 15:53:54
     * 创建作者：李兴武
     *
     * @param filePath 文件存储地址
     * @param stream   文件流
     * @return the upload
     * @author "lixingwu"
     */
    public static Upload upload(String filePath, InputStream stream) {
        return upload(BUCKET_NAME, filePath, stream);
    }

    /**
     * 方法描述：下载文件.
     * 创建时间：2019-03-05 16:20:16
     * 创建作者：李兴武
     *
     * @param bucketName 存储桶名称
     * @param filePath   文件存储地址
     * @param destFile   存储到的本地目标文件
     * @return the download
     * @author "lixingwu"
     */
    public static Download download(String bucketName, String filePath, File destFile) {
        TransferManager manager = cosTransferManager();
        Download download = null;
        try {
            download = manager.download(getBucketName(bucketName), filePath, destFile);
            download.waitForCompletion();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            manager.shutdownNow();
        }
        return download;
    }

    /**
     * 方法描述：下载文件.
     * 创建时间：2019-03-05 16:20:16
     * 创建作者：李兴武
     *
     * @param filePath 文件存储地址
     * @param destFile 存储到的本地目标文件
     * @return the download
     * @author "lixingwu"
     */
    public static Download download(String filePath, File destFile) {
        return download(BUCKET_NAME, filePath, destFile);
    }


    /**
     * 方法描述：复制文件-不同存储桶.
     * 创建时间：2019-03-05 16:28:11
     * 创建作者：李兴武
     *
     * @param srcBucketName  源存储桶名称
     * @param srcKey         源文件地址
     * @param destBucketName 目标存储桶名称
     * @param destKey        目标件地址
     * @return the copy
     * @author "lixingwu"
     */
    public static Copy copy(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        TransferManager manager = cosTransferManager();
        Copy copy = null;
        try {
            copy = manager.copy(getBucketName(srcBucketName), srcKey, getBucketName(destBucketName), destKey);
            copy.waitForCompletion();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            manager.shutdownNow();
        }
        return copy;
    }

    /**
     * 方法描述：复制文件-同存储桶.
     * 创建时间：2019-03-05 16:28:11
     * 创建作者：李兴武
     *
     * @param bucketName 源存储桶名称
     * @param srcKey     源文件地址
     * @param destKey    目标件地址
     * @return the copy
     * @author "lixingwu"
     */
    public static Copy copy(String bucketName, String srcKey, String destKey) {
        return copy(bucketName, srcKey, bucketName, destKey);
    }

    /*测试*/
    public static void main(String[] args) throws IOException {
        //Bucket bucket = TxCosUtils.createBucket("test");
        //List<Bucket> listBuckets = TxCosUtils.listBuckets();
        //System.out.println(JSON.toJSONString(listBuckets));
        // boolean test = TxCosUtils.doesBucketExist("web-js-css01");
        // File file = new File("E:\\codeList01.html");
        // BufferedInputStream stream = FileUtil.getInputStream(file);
        // PutObjectResult result = TxCosUtils.putObject("test", "html/codeList01.html", stream);
        // COSObject test = downObject("test", "html/codeList.html");
        // COSObjectInputStream inputStream = test.getObjectContent();
        // ObjectMetadata test = downObject("test", "html/codeList.html", new File("E:\\codeList.xml"));
        // ObjectMetadata metadata = findObjectMetadata("test", "html/codeList.html");
        // CopyObjectResult result = TxCosUtils.copyObject("web-js-css", "css/ch233.min.css", "test", "css/ch233.min.css");
        // CopyObjectResult result = TxCosUtils.copyObject("test", "css/ch233.min.css", "css/ch233.min.css.bak");
        // deleteObject(getBucketName("test"), "html/codeList01.html");

        // 高级API
        // 上传文件
        File file = new File("/Users/yangxuemin/IdeaProjects/Food-Vue/ruoyi-ui/src/assets/images/profile.jpg");
        BufferedInputStream stream = FileUtil.getInputStream(file);
        Upload test = TxCosUtils.upload("ygmfood", "profile.jpg", stream);
        System.err.println(JSON.toJSONString(test));

        // 下载文件
        // Download test = TxCosUtils.download("test", "css/ch233.min.css.bak", new File("E:\\ch233.min.css"));
        // Console.log(test);

        // 复制文件
        // Copy copy = TxCosUtils.copy("web-js-css", "layui/layui.js", "test", "layui/layui.js");
        // Copy copy = TxCosUtils.copy("test", "layui/layui.js", "layui/layui.js.bak");
        // Console.log(copy);
    }
}
