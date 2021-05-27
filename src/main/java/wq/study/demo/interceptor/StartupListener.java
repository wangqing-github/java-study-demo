package wq.study.demo.interceptor;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import wq.study.demo.constant.Constant;
import wq.study.demo.utils.ErrorLogger;
import wq.study.demo.utils.context.SpringContext;
import wq.study.demo.utils.redis.RedisCacheUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        logger.warn("context destroyed......");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            // 设置根目录到全局变量中
            String path = arg0.getServletContext().getRealPath("/");
            if (path.endsWith(File.separator)) {
                path = path.substring(0, path.lastIndexOf(File.separator));
            }
            System.setProperty(Constant.USER_DIR, path);
            ServletContext servletContext = arg0.getServletContext();
            WebApplicationContext wac = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(servletContext);
            SpringContext.inst().setApplicationContext(wac);
            logger.info("LOG_PATH = " + SpringContext.inst().getProperty("spring.logback.path"));
            logger.info("-----------------server init-----------------");
            printDBSettings();
        } catch (Exception e) {
            ErrorLogger.error(e, "api server startup error:");
            e.printStackTrace();
            throw new RuntimeException("context initializing error!");
        }
    }

    private void printDBSettings() {
        logger.info("----------------------------start db config print-----------------------------");
        logger.info("redis cache:" + RedisCacheUtil.inst().getRedisTemplate().toString());
        DruidDataSource serverDs = (DruidDataSource) SpringContext.inst().getBean("serverDataSource");
        logger.info("serverJdbc:" + serverDs.getUrl() + " name:" + serverDs.getUsername() + " pwd:" + serverDs.getPassword());
        DruidDataSource sysDs = (DruidDataSource) SpringContext.inst().getBean("sysServerDataSource");
        logger.info("sysJdbc:" + sysDs.getUrl() + " name:" + sysDs.getUsername() + " pwd:" + sysDs.getPassword());
        logger.info("----------------------------end db config print-----------------------------\n\n\n\n");
    }

}
