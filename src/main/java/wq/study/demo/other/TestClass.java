package wq.study.demo.other;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import wq.study.demo.entity.UserInfo;
import wq.study.demo.service.UserInfoService;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass {
    @Autowired
    UserInfoService userInfoService;

    public static void main(String[] args) {
        try {
            Class<?> clzz = Class.forName("wq.study.demo.entity.UserInfo");
            UserInfo userInfo = new UserInfo();
            System.out.println(clzz.isInstance(userInfo));
            System.out.println(clzz.getSimpleName());
            System.out.println(clzz.getName());
            Object obj = clzz.newInstance();
            userInfo = (UserInfo) obj;
            System.out.println(userInfo.getId());
            Field[] fields = getAllFields(clzz);
////            for (Field field : fields) {
////                System.out.println(field.getName());
////                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clzz);
////                Method m = pd.getWriteMethod();
////                Method m1 = pd.getReadMethod();
////                System.out.println(m.getName());
////                System.out.println(m1.getName());
////            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Field[] getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
