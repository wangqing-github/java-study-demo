package wq.study.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wq.study.demo.config.AnnotationTest;
import wq.study.demo.entity.UserInfo;
import wq.study.demo.service.UserInfoService;
import wq.study.demo.utils.redis.RedisCacheUtil;
import wq.study.demo.vo.ResultMsg;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class UserInfoController{
    @Resource
    UserInfoService userInfoService;

    @RequestMapping(value = "/testOne")
    @AnnotationTest
    public ResultMsg test(){
        Double aDouble = RedisCacheUtil.inst().zincrementScore("TEST", 1, "test");
        System.out.println(aDouble);
        List<UserInfo> list = userInfoService.list();
        return ResultMsg.success(list);
    }
    @RequestMapping("/file/upload")
    public void fileUpload(){

    }
}
