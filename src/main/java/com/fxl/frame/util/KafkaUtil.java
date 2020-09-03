package com.fxl.frame.util;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 
 * @Description TODO
 * @author fangxilin
 * @date 2020-07-07
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2020
 */
public class KafkaUtil {

    public static void main(String[] args) throws Exception {
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                consumer();
            }
        }).start();*/
        producerMessage();
        //consumer();
    }


    /**
     * 消费者
     */
    public static void consumer() {
        Properties properties = new Properties();
        //ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG
        properties.setProperty("bootstrap.servers","10.1.2.64:9092,10.1.2.74:9092,10.1.2.75:9092");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset", "earliest");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id","fxl.test");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Collections.singletonList("ACCOUNT_USER_BEHAVIOR"));

        try {
            while (true) {
                //poll轮询消息，每次轮询时，消费者都将尝试使用上次消耗的偏移量作为起始偏移量，然后依次获取，如果缓冲区中没有数据，则等待轮询所花费的时间（以毫秒为单位）
                //includeMetadataInTimeout,拉取消息的超时时间是否包含更新元数据的时间，默认为true，即包含。
                ConsumerRecords<String, String> poll = kafkaConsumer.poll(1000);
                System.out.println("---------开始");
                for (ConsumerRecord<String, String> context : poll) {
                    System.out.println("消息内容：" + context.toString());
                    System.out.println("消息所在分区:" + context.partition() + "-消息的偏移量:" + context.offset()
                            + "key:" + context.key() + "value:" + context.value());
                }
                System.out.println("poll是否为空:" + poll.isEmpty() + ", pool的count:" + poll.count());
                if (!poll.isEmpty()) {
                    //正常情况异步提交
                    kafkaConsumer.commitAsync();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //当程序中断时同步提交
                kafkaConsumer.commitSync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭当前消费者
                kafkaConsumer.close();
            }

        }

    }

    public static void producer() throws Exception {
        Properties properties = new Properties();
        //指定kafka服务器地址 如果是集群可以指定多个  但是就算只指定一个他也会去集群环境下寻找其他的节点地 址
        properties.setProperty("bootstrap.servers","10.1.2.64:9092,10.1.2.74:9092,10.1.2.75:9092");
        //key序列化器
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        //value序列化器
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        String value = "{\"data\":{\"accessToken\":\"b70e6bf9783d7914b48bb10fd76c2751TJdSnCKH20200714141030\"},\"action\":\"logout\",\"userId\":220019370}\n";
        ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<String, String>("ACCOUNT_USER_BEHAVIOR","blackListUser",value);
        Future<RecordMetadata> send = kafkaProducer.send(stringStringProducerRecord);
        RecordMetadata recordMetadata = send.get();
        System.out.println(recordMetadata.toString());
    }

    public static void producerMessage() throws Exception {
        Properties properties = new Properties();
        //指定kafka服务器地址 如果是集群可以指定多个  但是就算只指定一个他也会去集群环境下寻找其他的节点地 址
        properties.setProperty("bootstrap.servers", "10.1.2.64:9092,10.1.2.74:9092,10.1.2.75:9092");
        //key序列化器
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        //value序列化器
        properties.setProperty("value.serializer", JsonSerializer.class.getName());
        //防止发送消息报错 Magic v1 does not support record headers
        properties.setProperty(JsonSerializer.ADD_TYPE_INFO_HEADERS, "false");
        KafkaProducer<String, Message> kafkaProducer = new KafkaProducer<String, Message>(properties);
        Message value = new Message("11111", "2020", "ORDER_UPDATE_STATUS", "10.1.1.1", null);
        ProducerRecord<String, Message> stringStringProducerRecord = new ProducerRecord<String, Message>("ORDER_UPDATE_STATUS", "order", value);
        Future<RecordMetadata> send = kafkaProducer.send(stringStringProducerRecord);

        /*properties.setProperty("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        Message value = new Message("11111", "2020", "ORDER_UPDATE_STATUS", "10.1.1.1", null);
        ProducerRecord<String, String> stringStringProducerRecord = new ProducerRecord<String, String>("ORDER_UPDATE_STATUS", "order", JSON.toJSONString(value));
        Future<RecordMetadata> send = kafkaProducer.send(stringStringProducerRecord);*/

        RecordMetadata recordMetadata = send.get();
        System.out.println(recordMetadata.toString());
    }
}
