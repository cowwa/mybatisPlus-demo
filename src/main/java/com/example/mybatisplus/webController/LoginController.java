package com.example.mybatisplus.webController;

import com.example.mybatisplus.common.BaseController;
import com.example.mybatisplus.common.exception.BusinessException;
import com.example.mybatisplus.common.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: cowwa
 * @Date: 2019/12/18 11:07
 * @Description:
 */
@Slf4j
@Api(value = "系统登录",description = "系统登录")
@RestController("/loginController")
public class LoginController extends BaseController {



    @ApiOperation(value = "系统登录",notes = "系统登录")
    @PostMapping("/login")
    public ResponseData login(HttpServletRequest request) throws BusinessException{
    return resSuccess(request.getSession().getId());
    }
}
