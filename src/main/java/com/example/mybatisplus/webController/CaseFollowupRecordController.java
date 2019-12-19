package com.example.mybatisplus.webController;


import com.example.mybatisplus.service.impl.DataDumpServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.mybatisplus.common.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cowwa
 * @since 2019-12-06
 */
@RestController
@RequestMapping("/case-followup-record")
public class CaseFollowupRecordController extends BaseController {

    @Autowired
    DataDumpServiceImpl dataDumpService;


    @GetMapping("/dataDumpRecord")
    @ApiOperation(value = "转储催记信息",notes = "转储催记信息")
    public void dataDumpRecord(String index){
        dataDumpService.dataDumpRecord(index);
    }
}
