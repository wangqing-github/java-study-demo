package wq.study.demo.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import wq.study.demo.config.AnnotationTest;
import wq.study.demo.entity.UserInfo;

import java.lang.reflect.Method;

@Aspect
@Component
public class TestAopAnnoAspect {
    /**
     * execution:表达式主体
     * 第一部分  代表方法返回值类型 *表示任何类型
     * 第二部分  com.example.demo.controller 表示要监控的包名
     * 第三部分 .. 代表当前包名以及子包下的所有方法和类
     * 第四部分 * 代表类名，*代表所有的类
     * 第五部分 *(..) *代表类中的所有方法(..)代表方法里的任何参数
     */

//    @Pointcut("execution(* com.example.demo.controller..*(..))")
//    public void PointCut(){
//    }
    @Before("execution(* com.example.demo.controller..*(..))" +
            "&& @annotation(com.example.demo.config.AnnotationTest)")
    public void Before() {
        System.out.println("请求之前");
    }

    @Around("execution(* com.example.demo.controller..*(..))" +
            "&& @annotation(com.example.demo.config.AnnotationTest)")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] allArgs = joinPoint.getArgs();
        if (allArgs.length == 0) return joinPoint.proceed();
        Integer uid = null;
        for (Object params : allArgs) {
            if (params instanceof UserInfo) {
                UserInfo userInfo = (UserInfo) params;
                uid = userInfo.getId();
                break;
            }
        }
        if (uid == null) {
            CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
            String[] parameterNames = codeSignature.getParameterNames();
            int index = -1;
            for (int i = 0; i < parameterNames.length; i++) {
                String parameterName = parameterNames[i];
                if (StringUtils.equalsIgnoreCase(parameterName, "uid")) {
                    index = i;
                    break;
                }
            }
            if (index != -1) uid = (Integer) allArgs[index];
            if (uid == null) return joinPoint.proceed();

        }
        AnnotationTest annotationTest = method.getAnnotation(AnnotationTest.class);
        String type = annotationTest.type();
        System.out.println("type 为：" + type);
        return joinPoint.proceed();
    }

    @After("execution(* com.example.demo.controller..*(..))" +
            "&& @annotation(com.example.demo.config.AnnotationTest)")
    public void After() {
        System.out.println("在请求之后");
    }

    @AfterReturning("execution(* com.example.demo.controller..*(..))" +
            "&& @annotation(com.example.demo.config.AnnotationTest)")
    public void AfterReturning() {
        System.out.println("在返回之后");
    }

}
