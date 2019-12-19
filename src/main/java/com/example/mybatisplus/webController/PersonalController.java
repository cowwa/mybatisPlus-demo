package com.example.mybatisplus.webController;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mybatisplus.common.exception.BusinessException;
import com.example.mybatisplus.common.response.ResponseCode;
import com.example.mybatisplus.common.response.ResponseData;
import com.example.mybatisplus.common.response.ResponseDataUtil;
import com.example.mybatisplus.component.RedisUtil;
import com.example.mybatisplus.entity.Personal;
import com.example.mybatisplus.service.IPersonalService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.mybatisplus.common.BaseController;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cowwa
 * @since 2019-12-09
 */
@Slf4j
@RestController
@RequestMapping("/test/personal")
public class PersonalController extends BaseController {

    @Autowired
    IPersonalService personalService;

    @Autowired
    RedisUtil  redisUtil;



    @GetMapping("/getPerosnalPage")
    @ApiOperation(value ="分页获取客户信息",notes = "分页获取客户信息")
   public ResponseData<IPage<Personal>> getPerosnalPage(Personal personal) throws BusinessException{
      getHttpSession().setAttribute("test","aaaaa");
      log.error("careting:{}",getHttpSession().getCreationTime());

     IPage<Personal> iPage=  personalService.page(personal.builderPage(),personal.builderQueryWrapper());
      return resSuccess(iPage);
   }


}
