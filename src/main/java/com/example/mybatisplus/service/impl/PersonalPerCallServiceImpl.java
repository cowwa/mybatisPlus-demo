package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.entity.PersonalPerCall;
import com.example.mybatisplus.mapper.PersonalPerCallMapper;
import com.example.mybatisplus.service.IPersonalPerCallService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cowwa
 * @since 2019-12-09
 */
@Service
public class PersonalPerCallServiceImpl extends ServiceImpl<PersonalPerCallMapper, PersonalPerCall> implements IPersonalPerCallService {
    private Logger logger= LoggerFactory.getLogger(PersonalPerCallServiceImpl.class);

    @Async("asyncServiceExecutor")
    public void dataDumpRecord(List<PersonalPerCall> list) {
        logger.info("call保开始.........");
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        saveBatch(list);
        stopWatch.stop();
        logger.info("call保存耗时:{}",stopWatch.getTotalTimeMillis());
    }
}
