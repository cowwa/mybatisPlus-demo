package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.entity.CaseFollowupRecord;
import com.example.mybatisplus.entity.PersonalPerAddr;
import com.example.mybatisplus.service.IPersonalPerAddrService;
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
 * @Date: 2019/12/11 14:20
 * @Description:
 */
@Component
public class PersonalPerAddrServiceFetch {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    IPersonalPerAddrService personalPerAddrService;

    Logger logger= LoggerFactory.getLogger(PersonalPerAddrServiceFetch.class);

    @Async("asyncServiceExecutor")
    public void fecthData(){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 拼接查询条件
        //queryBuilder.must(QueryBuilders.termQuery("status", 1));
        try {
            SearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withIndices("personal_per_addr_01")
                    .withTypes("personal_per_addr")
                    .withQuery(queryBuilder)
                    .withPageable(PageRequest.of(0, 1000))
                    .build();
            // 滚动查询
            ScrolledPage<PersonalPerAddr> scroll = elasticsearchTemplate.startScroll(5000, searchQuery, PersonalPerAddr.class);
            // 判断是否有内容
            while (scroll.hasContent()) {
                personalPerAddrService.dataDumpRecord(scroll.getContent());
                //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
                scroll = elasticsearchTemplate.continueScroll(scroll.getScrollId(), 5000, PersonalPerAddr.class);
            }
            // 最后释放查询
            elasticsearchTemplate.clearScroll(scroll.getScrollId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
