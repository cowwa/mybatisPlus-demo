package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.entity.Personal;
import com.example.mybatisplus.service.IPersonalService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @author: cowwa
 * @Date: 2019/12/11 11:28
 * @Description:
 */
@Component
public class PersonalDataFetchService  {

    private Logger logger= LoggerFactory.getLogger(PersonalDataFetchService.class);

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    IPersonalService personalService;

    @Async("asyncServiceExecutor")
    public void fecthData(){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 拼接查询条件
        //queryBuilder.must(QueryBuilders.termQuery("status", 1));
        try {
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withIndices("personal_01")
                    .withTypes("personal")
                    .withQuery(queryBuilder)
                    .withPageable(PageRequest.of(0, 1000))
                    .build();
            // 滚动查询
            ScrolledPage<Personal> scroll = elasticsearchTemplate.startScroll(5000, searchQuery, Personal.class);
            // 判断是否有内容
            while (scroll.hasContent()) {
                // 业务逻辑省略
                personalService.dataDumpRecord(scroll.getContent());
                //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
                scroll = elasticsearchTemplate.continueScroll(scroll.getScrollId(), 5000, Personal.class);
            }
            // 最后释放查询
            elasticsearchTemplate.clearScroll(scroll.getScrollId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
