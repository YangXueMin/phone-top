package com.ruoyi.phone.controller.common;


import com.qcloud.cos.transfer.Upload;
import com.ruoyi.common.config.MinioConfig;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.*;
import com.ruoyi.common.utils.qrCode.EwmEntity;
import com.ruoyi.common.utils.qrCode.EwmUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private EwmUtils ewmUtils;

    /**
     * 网页授权域名，需要把用到的文件下载下来，文件名是需要请求的接口
     * 返回的是文件对应的内容
     */
    @GetMapping("MP_verify_cXlH68VBATL5Mc34.txt")
    private String returnConfigFile() {
        //把 MP_verify_xxxxxx.txt 中的内容返回
        return "cXlH68VBATL5Mc34";
    }

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String fileName = UUID.randomUUID() + "." + FileUploadUtils.getExtension(file);
            // 上传并返回新文件名称
            String url = MinioUtil.uploadFile(MinioConfig.getBucketName(), fileName, file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/uploadCos")
    public AjaxResult uploadCosFile(MultipartFile file) throws Exception {
        try {
            // 上传并返回新文件名称
            String extension = FileUploadUtils.getExtension(file);
            String param = "";
            boolean containsValue = Arrays.asList(MimeTypeUtils.IMAGE_EXTENSION).contains(extension);
            if (containsValue) {
                param = "?imageMogr2/format/webp";
            }
            String fileName = UUID.randomUUID() + file.getName();
            Upload upload = TxCosUtils.upload(fileName, file.getInputStream());
            if (upload != null) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("fileName", fileName);
                ajax.put("url", TxCosUtils.URL + fileName + param);
                return ajax;
            }
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error("文件上传失败");
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 生成二维码
     */
    @PostMapping("/common/qrCode")
    public AjaxResult qrCode(@RequestBody EwmEntity ewmEntity) {
        return AjaxResult.success("生成二维码成功", ewmUtils.generateBase64(ewmEntity.getContent(), ewmEntity.getImageType()));
    }
}

