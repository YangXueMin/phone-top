package com.ruoyi.common.utils;
import org.apache.http.nio.reactor.IOReactorException;
import org.opentsdb.client.OpenTSDBClient;
import org.opentsdb.client.OpenTSDBClientFactory;
import org.opentsdb.client.OpenTSDBConfig;
import org.opentsdb.client.bean.request.Point;
import org.opentsdb.client.bean.response.DetailResult;
import org.opentsdb.client.http.callback.BatchPutHttpResponseCallback;

import java.io.IOException;
import java.util.List;
import java.util.Map;
public class OpenTsDbUtil {
    private static final String OPENTSDB_HOST = "http://192.168.30.77";
    private static final int OPENTSDB_PORT = 4242;
    private static final OpenTSDBConfig config;

    static {
        config = OpenTSDBConfig
                // OpenTsDb数据库地址和端口号
                .address(OPENTSDB_HOST, OPENTSDB_PORT)
                // http连接池大小，默认100
                .httpConnectionPool(100)
                // http请求超时时间，默认100s
                .httpConnectTimeout(100)
                // 异步写入数据时，每次http提交的数据条数，默认50
                .batchPutSize(50)
                // 异步写入数据中，内部有一个队列，默认队列大小20000
                .batchPutBufferSize(20000)
                // 异步写入等待时间，如果距离上一次请求超多300ms，且有数据，则直接提交
                .batchPutTimeLimit(300)
                // 当确认这个client只用于查询时设置，可不创建内部队列从而提高效率
                .readonly()
                // 每批数据提交完成后回调
                .batchPutCallBack(new BatchPutHttpResponseCallback.BatchPutCallBack() {
                    @Override
                    public void response(List<Point> points, DetailResult result) {
                        // 在请求完成并且response code成功时回调
                    }

                    @Override
                    public void responseError(List<Point> points, DetailResult result) {
                        // 在response code失败时回调
                    }

                    @Override
                    public void failed(List<Point> points, Exception e) {
                        // 在发生错误是回调
                    }
                }).config();
    }

    /**
     * 获取客户端
     */
    public static OpenTSDBClient getClient() {
        OpenTSDBClient client = null;
        try {
            client = OpenTSDBClientFactory.connect(config);
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 插入单个tag数据
     */
    public static void insertOne(OpenTSDBClient client, String metric, String tagName, String tagValue, Number value) {
        //获取当前秒
        Long timestamp = System.currentTimeMillis() / 1000;
        //创建数据对象
        Point point = Point.metric(metric).tag(tagName, tagValue).value(timestamp, value).build();
        //将对象插入数据库
        client.put(point);
    }

    /**
     * 插入多个tag数据
     */
    public static void insertMap(OpenTSDBClient client, String metric, Map<String, String> tags, Number value) {
        //获取当前秒
        Long timestamp = System.currentTimeMillis() / 1000;
        //创建数据对象
        Point point = Point.metric(metric).tag(tags).value(timestamp, value).build();
        //将对象插入数据库
        client.put(point);
    }

    /**
     * 优雅关闭连接，会等待所有异步操作完成
     *
     * @param client 需要关闭的客户端
     */
    public static void close(OpenTSDBClient client) {
        if (client != null) {
            try {
                client.gracefulClose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
