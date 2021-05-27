package wq.study.demo.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import wq.study.demo.utils.context.SpringContext;

@Configuration
public class SpringContextConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.inst().setApplicationContext(applicationContext);
    }

}
