package com.redis;

import java.util.Set;

import com.fxl.frame.util.redis.RedisClient;

public class redisTest {
	public static void main(String[] args) {
		//连接本地的 Redis 服务
		/*Jedis jedis = new Jedis("localhost");
		jedis.auth("123456");
		System.out.println("Connection to server sucessfully");
	    //查看服务是否运行
		System.out.println("Server is running: "+jedis.ping());*/
		
		//设置 redis 字符串数据
		//jedis.set(key, value);
		
		RedisClient client = new RedisClient();
		client.initialShardedPool();
		Set<String> keys = client.keys("*");
		System.out.println(keys.toString());
		for (String key : keys) {
			System.out.println("key: " + key + ", type: " + client.type(key));
		}
		System.out.println();
	}
}
