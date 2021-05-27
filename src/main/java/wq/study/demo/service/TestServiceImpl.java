package wq.study.demo.service;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl  implements TestService{
//    @Override
    RedissonClient redissonClient;

    public void selectList() {
        List<String> list = new ArrayList<>();
    }
}
