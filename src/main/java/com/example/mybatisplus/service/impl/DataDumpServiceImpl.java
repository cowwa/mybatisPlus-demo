package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.entity.BaseCase;
import com.example.mybatisplus.entity.CaseFollowupRecord;
import com.example.mybatisplus.service.ICaseFollowupRecordService;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @author: cowwa
 * @Date: 2019/12/6 11:30
 * @Description: 数据转储
 */
@Service()
public class DataDumpServiceImpl {

    private Logger logger= LoggerFactory.getLogger(DataDumpServiceImpl.class);

    @Autowired
    ICaseFollowupRecordService caseFollowupRecordService;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 迁移数据
     * @param index
     */
    public void dataDumpRecord(String index){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 拼接查询条件
        //queryBuilder.must(QueryBuilders.termQuery("status", 1));
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(index)
                .withTypes("case_followup_record")
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(0, 1000))
                .build();
        // 滚动查询
        ScrolledPage<CaseFollowupRecord> scroll =  elasticsearchTemplate.startScroll(10000, searchQuery, CaseFollowupRecord.class);
        // 判断是否有内容
        while (scroll.hasContent()) {
            List<CaseFollowupRecord> content = scroll.getContent();
            StopWatch sw = new StopWatch();
            sw.start();
            // 业务逻辑省略
            try {
                caseFollowupRecordService.saveBatch(content);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            sw.stop();
            logger.info("耗时:{}",sw.getTotalTimeMillis());
            //取下一页，scrollId在es服务器上可能会发生变化，需要用最新的。发起continueScroll请求会重新刷新快照保留时间
            scroll = elasticsearchTemplate.continueScroll(scroll.getScrollId(), 10000, CaseFollowupRecord.class);
        }

        // 最后释放查询
        elasticsearchTemplate.clearScroll(scroll.getScrollId());
    }

}
