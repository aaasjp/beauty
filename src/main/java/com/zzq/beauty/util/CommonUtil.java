package com.zzq.beauty.util;

import com.mysql.jdbc.StringUtils;
import com.zzq.beauty.model.Person;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommonUtil {

    /**
     * 随机获取UUID字符串(无中划线)
     *
     * @return UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    /**
     * 随机获取字符串
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
        }
        return stringBuffer.toString();
    }

    /**
     * 根据指定长度 分隔字符串
     *
     * @param str    需要处理的字符串
     * @param length 分隔长度
     * @return 字符串集合
     */
    public static List<String> splitString(String str, int length) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < str.length(); i += length) {
            int endIndex = i + length;
            if (endIndex <= str.length()) {
                list.add(str.substring(i, i + length));
            } else {
                list.add(str.substring(i, str.length() - 1));
            }
        }
        return list;
    }

    /**
     * 将字符串List转化为字符串，以分隔符间隔.
     *
     * @param list      需要处理的List.
     * @param separator 分隔符.
     * @return 转化后的字符串
     */
    public static String toString(List<String> list, String separator) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : list) {
            stringBuffer.append(separator + str);
        }
        stringBuffer.deleteCharAt(0);
        return stringBuffer.toString();
    }

    public static String combineArray(Integer[] array, String separaotr) {
        String result = "";
        if (array == null || array.length == 0)
            return result;
        for (int i = 0; i < array.length; i++) {
            result += String.valueOf(array[i]) + separaotr;
        }
        return result.substring(0, result.length() - 1);
    }

    /**
     * 比较同一类型class,orig 中非NULL值的属性和dest不同的值到resultBean里面
     *
     * @param dest   要比较的BEAN
     * @param orig   原来BEAN
     * @param result 结果BEAN
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void differBeanNotNullPropertyToOtherBean(Object dest, Object orig, Object result) throws IllegalAccessException, InvocationTargetException {
        // Validate existence of the specified beans
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        if (result == null) {
            throw new IllegalArgumentException("No result bean specified");
        }

        if (!dest.getClass().toString().equals(orig.getClass().toString())) {
            throw new IllegalArgumentException("No same bean class");
        }
        if (orig instanceof Map) {
            throw new IllegalArgumentException("No support map");
        }

		/* if (orig is a standard JavaBean) */
        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
                try {
                    Object value1 = PropertyUtils.getSimpleProperty(orig, name);
                    Object value2 = PropertyUtils.getSimpleProperty(dest, name);
                    if (value2 != null && !value2.equals(value1)) {
                        if (PropertyUtils.isReadable(result, name) && PropertyUtils.isWriteable(result, name)) {
                            BeanUtils.copyProperty(result, name, value2);
                        }
                    }
                } catch (NoSuchMethodException e) {
                }
            }
        }

    }

    /**
     * 比较一个对象，如是这个对象的某一个属性不为空，把他copy到另一个有这个属性的bean中
     *
     * @param target 要copy到的bean
     * @param source 原来BEAN
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void copyProperties(Object target, Object source) {
        // Validate existence of the specified beans
        if (target == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (source == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        if (source instanceof Map) {
            throw new IllegalArgumentException("No support map");
        }

		/* if (orig is a standard JavaBean) */
        PropertyDescriptor sourceDescriptors[] = PropertyUtils.getPropertyDescriptors(source);
        for (int i = 0; i < sourceDescriptors.length; i++) {
            String name = sourceDescriptors[i].getName();
            if ("class".equals(name)) {
                continue; // No point in trying to set an object's class
            }
            if (PropertyUtils.isReadable(source, name) && PropertyUtils.isWriteable(target, name)) {
                try {
                    Object sourceValue = PropertyUtils.getSimpleProperty(source, name);
                    if (sourceValue != null) {
                        if (PropertyUtils.isReadable(target, name) && PropertyUtils.isWriteable(target, name)) {
                            BeanUtils.copyProperty(target, name, sourceValue);
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 比较两个类  如果target类中属性值为null就把source的该属性值复制过去
     *
     * @param target
     * @param source
     */
    public static void copyPropertiesToNull(Object target, Object source) {

        if (!target.getClass().getName().equals(source.getClass().getName())) {
            throw new IllegalArgumentException("参数必须是同一类型！");
        }
        if (target == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (source == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }

        if (source instanceof Map) {
            throw new IllegalArgumentException("No support map");
        }


        PropertyDescriptor sourceDescriptors[] = PropertyUtils.getPropertyDescriptors(source);
        PropertyDescriptor targetDescriptors[] = PropertyUtils.getPropertyDescriptors(target);
        for (int i = 0; i < sourceDescriptors.length; i++) {
            String name = sourceDescriptors[i].getName();
            String targetTame = targetDescriptors[i].getName();
            if ("class".equals(name) || "class".equals(targetTame)) {
                continue; // No point in trying to set an object's class
            }
            if (PropertyUtils.isReadable(source, name) && PropertyUtils.isWriteable(target, name)
                    && PropertyUtils.isReadable(target, name) && PropertyUtils.isWriteable(source, name)
                    ) {
                try {
                    Object sourceValue = PropertyUtils.getSimpleProperty(source, name);
                    Object targetValue = PropertyUtils.getSimpleProperty(target, targetTame);
                    if (sourceValue != null && targetValue == null) {
                        if (PropertyUtils.isReadable(target, name) && PropertyUtils.isWriteable(target, name)) {
                            BeanUtils.copyProperty(target, name, sourceValue);
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 将字符串List根据某种分隔符解析，删除或者
     * 添加指定子串，再还原成以分隔符间隔的完整字符串.
     *
     * @param src       需要处理的List.
     * @param sub       指定的子串
     * @param separator 分隔符.
     * @param add       添加还是删除
     * @return 转化后的字符串
     */
    public static String combinString(String src, String sub, String separator, boolean add) {
        if (add) {
            return src + separator + sub;
        }

        String[] spArray = src.split(separator);
        ArrayList<String> helpList = new ArrayList<String>();
        for (int i = 0; i < spArray.length; i++) {
            if (spArray[i] == sub) continue;
            helpList.add(spArray[i]);
        }
        return toString(helpList, separator);
    }

    public static Integer getUserIdByRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HashMap map = (HashMap) request.getSession().getAttribute("user");
        if (map != null) {
            String userIdStr = map.getOrDefault("id", "").toString();
            if (StringUtils.isNullOrEmpty(userIdStr)) {
                return null;
            }
            return Integer.parseInt(userIdStr);
        }
        return null;
    }

}