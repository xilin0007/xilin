package com.fxl.frame.util.redis;

/**
 * 类名称：RedisSerializeUtil
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class RedisSerializeUtil
{
    private static final Logger log = Logger.getLogger(RedisSerializeUtil.class);

    // 序列化
    public static byte[] serialize(Object object)
    {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try
        {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (Exception e)
        {
            log.error("序列表对象失败", e);
        }
        return null;
    }

    // 反序列化
    public static Object deSeialize(byte[] bytes)
    {
        ByteArrayInputStream byteArrayOutputStream = null;
        try
        {
            byteArrayOutputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream);
            return objectInputStream.readObject();
        } catch (Exception e)
        {
            log.error("反序列表对象失败", e);
        }
        return null;
    }

}