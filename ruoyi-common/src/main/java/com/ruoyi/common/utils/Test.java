package com.ruoyi.common.utils;

import com.alibaba.fastjson2.JSON;
import org.opentsdb.client.OpenTSDBClient;
import org.opentsdb.client.bean.request.Query;
import org.opentsdb.client.bean.request.SubQuery;
import org.opentsdb.client.bean.response.QueryResult;
import org.opentsdb.client.bean.request.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //获取客户端
        OpenTSDBClient client = OpenTsDbUtil.getClient();
        /*//查询条件集合
        List<SubQuery.Filter> filterList = new ArrayList<>();
        //查询条件
        SubQuery.Filter filter = new SubQuery.Filter();
        //设置成true, 不设置或设置成false会导致读超时
        filter.setGroupBy(Boolean.TRUE);
        //设置过滤类型
        //LiteralOr：等于查询，或查询，类似 SQL 里的 IN 查询；
        //NotLiteralOr：等于查询，或查询，类似 SQL 里的 NOT IN 查询；
        //Wildcard：模糊匹配，类似 SQL 里的 like 查询；
        //Regexp：正则匹配；
        filter.setType(SubQuery.Filter.FilterType.LITERAL_OR);
        //设置tag,即查询的条件对象
        filter.setTagk("dataV");
        //要查询的tag
        filter.setFilter("count");
        filterList.add(filter);
        //查询的时间范围，3m-ago：3秒前到当前数据
        Query query = Query.begin("50m-ago")
                //要查询的库
                .sub(SubQuery.metric("waterCurrent")
                        //查询的聚合类型
                        .aggregator(SubQuery.Aggregator.NONE)
                        .filter(filterList)
                        .build())
                .build();
        // 同步查询
        List<QueryResult> resultList = client.query(query);
        System.out.println(JSON.toJSONString(resultList));*/
            //获取当前秒
        Long timestamp = System.currentTimeMillis() / 1000;
        //创建数据对象
        Point point = Point.metric("water111").tag("testTag", "test").value(timestamp, 1.0).build();
        //将对象插入数据库
        client.put(point);
        //关闭资源
        OpenTsDbUtil.close(client);
    }
}
