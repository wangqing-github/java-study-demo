package wq.study.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wq.study.demo.utils.JsonUtils;

@ApiModel(value = "响应结果")
public class ResultMsg {

    private static final Logger logger = LoggerFactory.getLogger(ResultMsg.class);

    @ApiModelProperty(value = "响应码",example = "1")
    private String code = "-1";    //代码
    @ApiModelProperty(value = "响应消息",example = "成功")
    private String msg;     //信息
    @ApiModelProperty(value = "通用响应体",example = "{}")
    private Object data;    //返回数据

    private ResultMsg() {
    }

    private ResultMsg(Boolean success) {
        code = success ? "1" : code;
    }

    private ResultMsg(Boolean success, Object data) {
        this.data = data;
        code = success ? "1" : code;
    }

    private ResultMsg(Boolean success, String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.code = success && code.equals("-1") ? "1" : code;
    }

    private ResultMsg(Boolean success, String code, Object data) {
        this.code = code;
        this.data = data;
        this.code = success && code.equals("-1") ? "1" : code;
    }

    private ResultMsg(Boolean success, String code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.code = success && code.equals("-1") ? "1" : code;
    }

    public String toJson() {
        return JsonUtils.toJson(this);
    }

    ;

    public static ResultMsg success() {

        return new ResultMsg(true);
    }

    public static ResultMsg success(String code, Object data) {
        return new ResultMsg(true, code, data);
    }

    public static ResultMsg success(Object data) {
        return new ResultMsg(true, data);
    }


    public static ResultMsg success(String msg) {
        return new ResultMsg(true, null, null, msg);
    }

    public static ResultMsg fail() {
        return new ResultMsg(false);
    }

    public static ResultMsg fail(String url,String code, String msg) {
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        StringBuilder sb = new StringBuilder();
        if(stackElements != null) {
            for (int i = 0; i < 100; i++) {
                if(i<stackElements.length){
                    sb.append(stackElements[i].getClassName()).append(".");
                    sb.append(stackElements[i].getMethodName()).append(":");
                    sb.append(stackElements[i].getLineNumber());
                    sb.append("\n");
                }
            }
        }
        logger.info("返回参数 url="+url+" code="+code+",msg="+msg+",e->"+sb.toString());
        return new ResultMsg(false, code, msg);
    }

    public static ResultMsg fail(String code, String msg) {
        return new ResultMsg(false, code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
