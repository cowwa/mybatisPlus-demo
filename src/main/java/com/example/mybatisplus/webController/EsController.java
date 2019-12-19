package com.example.mybatisplus.webController;

import com.example.mybatisplus.entity.BaseCase;
import com.example.mybatisplus.entity.Personal;
import com.example.mybatisplus.service.ICaseFollowupRecordService;
import com.example.mybatisplus.service.IPersonalPerAddrService;
import com.example.mybatisplus.service.IPersonalPerCallService;
import com.example.mybatisplus.service.IPersonalService;
import com.example.mybatisplus.service.impl.CaseFollowupRecordServiceFetch;
import com.example.mybatisplus.service.impl.PersonalDataFetchService;
import com.example.mybatisplus.service.impl.PersonalPerAddrServiceFetch;
import com.example.mybatisplus.service.impl.PersonalPerCallServiceFetch;
import com.google.common.base.Stopwatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import net.bytebuddy.asm.Advice;
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
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: cowwa
 * @Date: 2019/12/5 17:44
 * @Description:
 */
@RestController
@RequestMapping("/es")
@Api(value = "操作es数据库", description = "操作es数据库")
public class EsController {

    @Autowired
    ICaseFollowupRecordService caseFollowupRecordService;

    @Autowired
    PersonalDataFetchService personalDataFetchService;

    @Autowired
    CaseFollowupRecordServiceFetch caseFollowupRecordServiceFetch;

    @Autowired
    PersonalPerAddrServiceFetch personalPerAddrServiceFetch;

    @Autowired
    PersonalPerCallServiceFetch personalPerCallServiceFetch;

    @Autowired
    IPersonalService personalService;


    private Logger logger= LoggerFactory.getLogger(EsController.class);

    @GetMapping("/fetchDataFromEs")
    @ApiModelProperty(notes = "获取es数据",value = "获取es数据")
    public void fetchDataFromEs(){
        //personalDataFetchService.fecthData();

        //personalPerCallServiceFetch.fecthData();

        //personalPerAddrServiceFetch.fecthData();

        caseFollowupRecordServiceFetch.fecthData();
    }

}
