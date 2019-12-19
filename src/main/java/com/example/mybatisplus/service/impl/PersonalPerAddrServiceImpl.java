package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.entity.Personal;
import com.example.mybatisplus.entity.PersonalPerAddr;
import com.example.mybatisplus.entity.PersonalPerCall;
import com.example.mybatisplus.mapper.PersonalPerAddrMapper;
import com.example.mybatisplus.service.IPersonalPerAddrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cowwa
 * @since 2019-12-09
 */
@Service
public class PersonalPerAddrServiceImpl extends ServiceImpl<PersonalPerAddrMapper, PersonalPerAddr> implements IPersonalPerAddrService {
    private Logger logger= LoggerFactory.getLogger(PersonalPerAddrServiceImpl.class);

    @Async("asyncServiceExecutor")
    public void dataDumpRecord(List<PersonalPerAddr> list) {
        logger.info("addr保开始.........");
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        saveBatch(list);
        stopWatch.stop();
        logger.info("Addr保存耗时:{}",stopWatch.getTotalTimeMillis());
    }
}
