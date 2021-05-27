package wq.study.demo.constant;

public interface Constant {

    /**
     * 定时器执行时间
     */
    long ONE_MIN = 60000;

    /**
     * 通过user.dir可以获取应用程序根目录
     */
    String USER_DIR = "user.dir";

    /**
     * 缓存过期时间（1小时）
     **/
      int CACHE_EXP_H = 60 * 60;

    /**
     * 缓存过期时间（24小时）
     **/
      int CACHE_EXP_D = 60 * 60 * 24;


    /**
     * 缓存过期时间（列表 30分钟）
     **/
      int CACHE_EXP_LIST = 60 * 30;

    /**
     * 缓存过期时间（详细 4小时）
     **/
      int CACHE_EXP_DETAIL = 60 * 240;

      int CACHE_EXP_WEEK = 60 * 60 * 24 * 7;

      String security = "<policy-file-request/>";

}
