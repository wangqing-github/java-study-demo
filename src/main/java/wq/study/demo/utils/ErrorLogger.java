package wq.study.demo.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;

public class ErrorLogger {

    private static final Logger log = LoggerFactory.getLogger("ErrorLogger");
    private static final String ERROR_TEMPLETE = "标题：{}\n参数：{}\n内容：{} \n\n";

    public ErrorLogger() {
    }

    public static void info(String msg, Object... args) {
        log.info(msg, args);
    }

    public static void debug(String msg, Object... args) {
        log.debug(msg, args);
    }

    public static void warn(String msg, Object... args) {
        log.warn(msg, args);
    }

    public static void error(String msg, Object... args) {
        error("", "", msg, args);
    }

    public static void error(String title, String param, String msg, Object... args) {
        String errorMsg = format(msg, args);
        String error = format(ERROR_TEMPLETE, title, param, errorMsg);
        log.error(error);
    }

    private static String format(String str, Object... args) {
        for (Object arg : args) {
            str = str.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(arg)));
        }

        return str;
    }

    public static void error(Exception e, String msg, Object... args) {
        error(e, "", "", msg, args);
    }

    public static void error(Exception e, String title, String param, String msg, Object... args) {
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        if (args.length > 0) {
            error(title, param, msg + "\n方法调用栈：{}", ArrayUtils.add(args, out.toString()));
        } else {
            error(title, param, msg + "\n方法调用栈：{}", out.toString());
        }

    }
}
