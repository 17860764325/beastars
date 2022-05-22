package com.lhrlyn.cn.lhrlynadmin.user.util.listBeanUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListBeanUtil {
    /**
     * 传进来一个列表，方法判断list里面的多个对象中的某个字段的值是否重复
     *
     * @param list, a
     * @author lhr
     * @date 2021/9/2 5:23 下午
     * @return: boolean
     */
    public static <T> boolean v(List<T> list, String name) {
        // 声明一个set（set里面不允许有重复的值）
        java.util.Set set = new HashSet();
        try {
            // 将list进行循环把每一个list的相应的值存入到set里面（如果有重复的就回覆盖掉），最后比较传入的list和set的长度就能判断有没有重复的
            for (T o : list) {
                // 获取class
                Class object = o.getClass();
                // 获取该对象的所有属性值
                Field[] fields = object.getDeclaredFields();
                // 将属性值进行循环，得到和传入的属性值相同的那个属性的值
                for (Field field : fields) {
                    if (field.getName().equals(name)) {
                        field.setAccessible(true);
                        // 存储到set列表里面
                        System.out.println(field.get(o));
                        set.add(field.get(o));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据两个列表的长度来判断有没有重复的值
        if (set.size() < list.size()) {
            return true;
        }
        return false;
    }


    /**
     *  一个列表，根据列表中每个对象的某个字段，进行循环分组(这个字段一样的那些对象会分到一组)
     *  list:列表
     *  name：字段名
     * @author lhr
     * @date  2021/9/3 11:14 上午
     * @param list, name
     * @return: java.util.List<java.util.List<T>>
     */
    public static <T> List<List<T>> xhfz(List<T> list, String name)  {
        java.util.Set set = new HashSet();
        List<List<T>> listAll = new ArrayList<>();
        try {
            for (T t : list) {
                Class<?> aClass = t.getClass();
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (declaredField.getName().equals(name)) {
                        declaredField.setAccessible(true);
                        set.add(declaredField.get(t));
                    }
                }
            }
            for (int i = 0; i < set.size(); i++) {
                List<T> list1 = new ArrayList<>();
                for (T t : list) {
                    Class<?> aClass = t.getClass();
                    Field[] declaredFields = aClass.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        if (declaredField.getName().equals(name)) {
                            declaredField.setAccessible(true);
                            if(declaredField.get(t).equals(set.toArray()[i])){
                                list1.add(t);
                            }
                        }
                    }
                }
                listAll.add(list1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return listAll;
    }
}
