package com.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatisplus.entity.Personal;
import com.example.mybatisplus.mapper.PersonalMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cowwa
 * @since 2019-12-09
 */
@Service
public class PersonalServiceImpl extends ServiceImpl<PersonalMapper, Personal> implements IPersonalService {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    private Logger logger = LoggerFactory.getLogger(PersonalServiceImpl.class);

    @Async
    public void dataDumpRecord(List<Personal> personalList) {
        logger.info("P保开始.........");
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        saveBatch(personalList);
        stopWatch.stop();
        logger.info("P保存耗时:{}",stopWatch.getTotalTimeMillis());
    }



}
