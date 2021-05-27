package wq.study.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wq.study.demo.entity.UserInfo;
import wq.study.demo.service.UserInfoService;
import wq.study.demo.user.UserInfoMapper;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;
//    @Override
//    public List<UserInfo> selectList() {
//        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
//        return userInfoMapper.selectList(wrapper);
//    }
}
