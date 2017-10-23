package com.fxl.frame.util.redis;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author Administrator
 *
 */
@Component("redisClient")
public class RedisClient {
	protected final Logger log = Logger.getLogger(getClass());
	private int currentReadIndex = 0;

	private boolean useRedisModel=true;
	private String masterName="mymaster";
	private String redis1Ip="localhost";
	private String password="123456";
	private Integer redis1Port=6379;
	private Integer maxTotal=2000;
	private Integer maxIdle=500;
	private boolean blockWhenExhausted=true;
	private Boolean testOnBorrow=false;
	private Boolean testOnReturn=false;
	/**
	 * /** 读取数据用到的连接池
	 */
	private ShardedJedisPool shardJedisPool;

    /**
	 * 写数据用到的连接池
	 */
    //private JedisSentinelPool shardJedisPool;

	public RedisClient() {
	}

	                                                                                                                                                    /**
	 * 初始化sentinel池和shardPool池
	 */
	@PostConstruct
	public void initialShardedPool() {
		if(!useRedisModel)
			return;
		Properties local = new Properties();
		try {
			local.load(RedisClient.class.getResourceAsStream("/conf/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("load local.properties error:"+e.getMessage());
		}
		String redisIP = StringUtils.trimToEmpty(local.getProperty("JEDIS_IP"));
		if(StringUtils.isNotBlank(redisIP)){
			redis1Ip = redisIP;
		}
		String redisPort = StringUtils.trimToEmpty(local.getProperty("JEDIS_PORT"));
		if(StringUtils.isNotBlank(redisPort)){
			redis1Port = Integer.valueOf(redisPort);
		}
		
		String pssWd=StringUtils.trimToEmpty(local.getProperty("JEDIS_PASSWD"));
		if(StringUtils.isNotBlank(pssWd)){
			password = pssWd;
		}
		// 设置jedis连接池的属性
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		// 当连接池没有可以资源时，进行阻塞操作
		config.setBlockWhenExhausted(blockWhenExhausted);
		// 初始化可用的redis服务属性
		List<JedisShardInfo> listShard = new ArrayList<JedisShardInfo>();
        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, masterName);
        info1.setPassword(password);
        //JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port);
        //JedisShardInfo info3 = new JedisShardInfo(redis3Ip, redis3Port);
		listShard.add(info1);

        //listShard.add(info2);
        //listShard.add(info3);
        shardJedisPool = new ShardedJedisPool(config, listShard);
		// 初始化sentinel的属性
        /*
		 * Set<String> sentinels = new HashSet<String>(); sentinels.add(new
		 * HostAndPort(redisSentinel1Ip, redisSentinel1Port).toString());
		 * sentinels.add(new HostAndPort(redisSentinel2Ip,
		 * redisSentinel2Port).toString()); sentinels.add(new
		 * HostAndPort(redisSentinel3Ip, redisSentinel3Port).toString()); //
		 * 如果存在密码 GenericObjectPoolConfig sentinelConfig = new
		 * GenericObjectPoolConfig(); sentinelConfig.setMaxTotal(maxTotal);
		 * sentinelConfig.setMaxIdle(maxIdle);
		 * sentinelConfig.setTestOnBorrow(testOnBorrow);
		 * sentinelConfig.setTestOnReturn(testOnReturn); // 当连接池没有可以资源时，进行阻塞操作
		 * config.setBlockWhenExhausted(blockWhenExhausted); if
		 * (StringUtils.isEmpty(password)) { shardJedisPool = new
		 * JedisSentinelPool(masterName, sentinels, sentinelConfig,5000); } else
		 * { // 设置sentinel密码 shardJedisPool = new JedisSentinelPool(masterName,
		 * sentinels, sentinelConfig,5000, password); // 设置redis服务密码 if
		 * (!StringUtils.isEmpty(password)) { info1.setPassword(password);
		 * info2.setPassword(password); info3.setPassword(password); } }
		 */
	}

	                                                                                                                                                    /**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisValue(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

		return value;
	}

	                                                                                                                                                    /**
	 * 通过key删除
	 * 
	 * @param key
	 */
    public void del(String key)
    {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			instance.del(key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            instance.set(key, value);
            instance.expire(key, liveTime);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
			throw e;
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

	}


    /**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            instance.set(key, value);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
			throw e;
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

	/**
	 * 发布 key value
	 *
	 * @param key
	 * @param value
	 */
	public void publish(String key, String value) {
		ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			Collection<Jedis> jedislist = instance.getAllShards();
			Iterator<Jedis> itr = jedislist.iterator();
			while (itr.hasNext()) {
				Jedis obj = itr.next();
				obj.publish(key, value);
				break;
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
			throw e;
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}




    /**
	 * 通过正则匹配keys
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            return instance.getShard("").keys(pattern);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return null;
	}

    /**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) throws Exception {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			return instance.exists(key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
			throw e;
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	public String flushDB() {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            return instance.getShard("").flushDB();
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return null;
	}

    /**
	 * 查看redis里有多少数据
	 */
	public long dbSize() {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            return instance.getShard("").dbSize();
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return 0;
	}

    /**
	 * 向redis中添加list对象
	 * 
	 * @param key
	 * @param list
	 * @param expireSeconds
	 */
	public void setList(String key, List<String> list, int expireSeconds) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            instance.del(key);
            instance.rpush(key, list.toArray(new String[list.size()]));
			if (expireSeconds != -1)
				instance.expire(key, expireSeconds);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * 向list中添加元素
	 * 
	 * @param key
	 * @param memberValue
	 */
	public void addListMember(String key, String memberValue) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			instance.rpush(key, memberValue);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

    }

	                                                                                                                                                    /**
	 * 获取list对象
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getList(String key) {
		List<String> value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			instance.persist(key);
			value = getFromJedisList(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

		return value;
	}

	/******************** list 操作 在list的头部添加 item*********begin *********************************************/
	                                                                                                                                                    /**
	 * 向redis中添加list对象()
	 * 
	 * @param key
	 * @param list
	 * @param fixSize
	 *            -1代表不限制大小
	 */
    public void setListFromHeadWithFixSize(String key, List<String> list, int fixSize)
    {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
            instance.del(key);
            instance.lpush(key, list.toArray(new String[list.size()]));
            if (fixSize != -1)
				instance.ltrim(key, 0, fixSize - 1);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }
    }

    /**
	 * 向list中添加元素
	 * 
	 * @param key
	 * @param memberValue
	 */
    public void addListMemberFromHeadWithFixSize(String key, String memberValue,int fixSize) {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
            instance.lpush(key, memberValue);
            instance.ltrim(key, 0, fixSize-1);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }

    }

    /**
	 * 设置List固定长度
	 * 
	 * @param key
	 * @param memberValue
	 */
    /*
    public void setListFixSizeLtrim(String key,int fixSize) {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
            instance.ltrim(key, 0, fixSize-1);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null) {
                shardJedisPool.returnBrokenResource(instance);
            }
        } finally {
            if (borrowOrOprSuccess) {
                shardJedisPool.returnResource(instance);
            }
        }

    }*/
	/******************** list 操作 在list的头部添加 item*************end *****************************************/

	                                                                                                                                                    /**
	 * 向redis中添加set对象
	 * 
	 * @param key
	 * @param set
	 * @param expireSeconds
	 */
    public void setSet(String key, Set<String> set, int expireSeconds)
    {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		Transaction tx = null;
		try {
            instance = shardJedisPool.getResource();
            instance.del(key);
            instance.sadd(key, set.toArray(new String[set.size()]));
            if (expireSeconds != -1)
				instance.expire(key, expireSeconds);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * 向map中添加一个元素
	 * 
	 * @param key
	 * @param memberValue
	 */
	public void addSetMember(String key, String memberValue) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			instance.sadd(key, memberValue);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

	}

    /**
	 * 从map中移除指定元素
	 * 
	 * @param key
	 * @param memberValue
	 */
	public void removeSetMember(String key, String memberValue) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			instance.srem(key, memberValue);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * 判断set中是否存在值memberValue
	 * 
	 * @param key
	 * @param memberValue
	 * @return
	 */
	public boolean isContainMemberOfSet(String key, String memberValue) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		boolean isExist = false;
		try {
            instance = shardJedisPool.getResource();
			isExist = instance.sismember(key, memberValue);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return isExist;
    }

	                                                                                                                                                    /**
	 * 获取set对象
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getSet(String key) {
		Set<String> value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisSet(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return value;
	}

	                                                                                                                                                    /**
	 * 向redis中新增map
	 * 
	 * @param key
	 * @param map
	 * @param expireSeconds
	 */
    public void setMap(String key, Map<String, String> map, int expireSeconds)
    {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            instance.hmset("user", map);
			if (expireSeconds != -1)
				instance.expire(key, expireSeconds);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * 向map中添加元素
	 * 
	 * @param key
	 * @param mapKey
	 * @param mapValue
	 */
	public void addMapMember(String key, String mapKey, String mapValue) {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			Map<String, String> sourceMap = instance.hgetAll(key);
			sourceMap.put(mapKey, mapValue);
			instance.hmset(key, sourceMap);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

    }

	                                                                                                                                                    /**
	 * 获取map的所有key列表
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getMapAllKeys(String key) {
		Set<String> value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisMapKeys(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

		return value;
	}

	                                                                                                                                                    /**
	 * 获取map的所有值列表
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getMapAllValues(String key) {
		List<String> value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisMapValues(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

		return value;
	}

	                                                                                                                                                    /**
	 * 获取map对象
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getMap(String key) {
		Map<String, String> value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisMap(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

		return value;
	}

	                                                                                                                                                    /**
	 * 删除map的指定元素
	 * 
	 * @param key
	 * @param mapKey
	 */
    public void deleteMapMember(String key, String mapKey)
    {

        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
			instance.hdel("hashs", "entryKey");
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

    }

	                                                                                                                                                    /**
	 * 根据key和map的key获取指定的map值
	 * 
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public String getMapValue(String key, String mapKey) {
		String value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisMapValue(instance, key, mapKey);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}

		return value;
	}

    /**
	 * 设置序列号对象
	 * 
	 * @param key
	 * @param bean
	 * @param expireSeconds
	 */
    public void setSerializableObject(String key, Serializable bean, int expireSeconds)
    {
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
            instance = shardJedisPool.getResource();
            instance.set(key.getBytes(), RedisSerializeUtil.serialize(bean));
			if (expireSeconds != -1)
				instance.expire(key, expireSeconds);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
			throw e;
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
	}
    }

    /**
	 * 获取序列号对象
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getSerializableObject(String key) {
		Serializable value = null;
        ShardedJedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisSeriObject(instance, key);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return value;
	}

    /**
	 * 根据key和map的key获取指定的map值
	 * 
	 * @param instance
	 * @param key
	 * @param mapKey
	 * @return
	 */
	private String getFromJedisMapValue(ShardedJedis instance, String key,
			String mapKey) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		String redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).hget(key, mapKey);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.hget(key, mapKey);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 从shardJedis中获取属性值
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private String getFromJedisValue(ShardedJedis instance, String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		String redisResult = "";
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).get(key);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.get(key);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 从shardJedis中获取指定List的所有值
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private List<String> getFromJedisList(ShardedJedis instance, String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		List<String> redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).lrange(key, 0, -1);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.lrange(key, 0, -1);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 从shardJedis中获取指定map
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private Map<String, String> getFromJedisMap(ShardedJedis instance,
			String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		Map<String, String> redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).hgetAll(key);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.hgetAll(key);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 从shardJedis中获取指定set的所有值
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private Set<String> getFromJedisSet(ShardedJedis instance, String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		Set<String> redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).smembers(key);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.smembers(key);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 从shardJedis中获取指定map的所有key
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private Set<String> getFromJedisMapKeys(ShardedJedis instance, String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		Set<String> redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).hkeys(key);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.hkeys(key);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 从shardJedis中获取指定map的所有值
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private List<String> getFromJedisMapValues(ShardedJedis instance, String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		List<String> redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			redisResult = jedisList.get(currentReadIndex).hvals(key);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					redisResult = jedis.hvals(key);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

    /**
	 * 从shardJedis中获取序列话对象
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private Serializable getFromJedisSeriObject(ShardedJedis instance, String key) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		Serializable redisResult = null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
		    byte[] objectByte=jedisList.get(currentReadIndex).get(key.getBytes());
		    if(objectByte==null)
				return null;
			redisResult = (Serializable) RedisSerializeUtil.deSeialize(objectByte);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
				    byte[] objectByte= jedis.get(key.getBytes());
				    if(objectByte==null)
						return null;
					redisResult = (Serializable) RedisSerializeUtil.deSeialize(objectByte);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return redisResult;
	}

	                                                                                                                                                    /**
	 * 添加针对set的scan方法，用来针对值过滤
	 * 
	 * @param key
	 * @param matchMergeStr
	 * @return
	 */
	public ScanResult<String> getSetScan(String key, String matchMergeStr) {
        ShardedJedis instance = null;
		ScanResult<String> value=null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisSetScanObject(instance, key,matchMergeStr);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return value;
	}
	public ScanResult<String> getKeyScan(String key, String matchMergeStr) {
        ShardedJedis instance = null;
		ScanResult<String> value=null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = shardJedisPool.getResource();
			value = getFromJedisKeyScanObject(instance, key,matchMergeStr);
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
		return value;
	}

    /**
	 * 从shardJedis中获取序列话对象
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private ScanResult<String> getFromJedisSetScanObject(ShardedJedis instance, String key,String matchMergeStr) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		ScanResult<String> resultSet =null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			ScanParams params = new ScanParams();
			Long number = jedisList.get(currentReadIndex).scard(key);
			params.count(number.intValue());
			params.match(matchMergeStr);
			resultSet = jedisList.get(currentReadIndex).sscan(key,"0", params);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					ScanParams params = new ScanParams();
					Long number = jedisList.get(currentReadIndex).scard(key);
					params.count(number.intValue());
					params.match(matchMergeStr);
					resultSet = jedis.sscan(key,"0", params);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return resultSet;
	}

    /**
	 * 从shardJedis中获取Scan结果
	 * 
	 * @param instance
	 * @param key
	 * @return
	 */
	private ScanResult<String> getFromJedisKeyScanObject(ShardedJedis instance, String keyPattern,String matchMergeStr) {
		List<Jedis> jedisList = new ArrayList<Jedis>(instance.getAllShards());
		ScanResult<String> resultSet =null;
		try {
			// 优先从上次获取成功的redis中取对象，如果连接失败，在从其他的redid里面获取
			ScanParams params = new ScanParams();
			int number = jedisList.get(currentReadIndex).keys("*").size();
			params.count(number);
			params.match(matchMergeStr);
			resultSet = jedisList.get(currentReadIndex).scan("0", params);
		} catch (JedisConnectionException e) {
			// 从其他的redis中取值
			for (Jedis jedis : jedisList)
				try {
					ScanParams params = new ScanParams();
					int number = jedisList.get(currentReadIndex).keys("*").size();
					params.count(number);
					params.match(matchMergeStr);
					resultSet = jedisList.get(currentReadIndex).scan("0", params);
					// 如果能成功获取redis对象，则为下次访问提供重新初始化该属性。
					currentReadIndex = jedisList.indexOf(jedis);
					break;
				} catch (JedisConnectionException e3) {
					log.error("redis连接异常,请联系管理员，【ip"
							+ jedis.getClient().getHost() + "】【端口："
							+ jedis.getClient().getPort() + "】", e3);
				}
		}
		return resultSet;
	}

    /**
	 * 添加或修改元素。之前先查找是否存在delMatch模式的元素，如果有则删除 如果要添加的元素为空，则只会执行删除操作，不会执行添加操作
	 * 
	 * @param key
	 * @param delMatch
	 * @param element
	 */
    public void addOrUpdateSetElementByScan(String key, String delMatch,
			String element) {
        ShardedJedis instance = null;
        Jedis jedis = null;
		boolean borrowOrOprSuccess = true;
		ScanResult<String> resultSet = null;
		Transaction tx = null;
		try {
            instance = shardJedisPool.getResource();
            jedis = instance.getShard("");
			ScanParams params = new ScanParams();
			Long number = instance.scard(key);
			params.count(number.intValue());
			params.match(delMatch);
            resultSet = jedis.sscan(key, "0", params);
			List<String> resultList = resultSet.getResult();
            tx = jedis.multi();
			if (!CollectionUtils.isEmpty(resultList))
				tx.srem(key, resultList.toArray(new String[resultList.size()]));
			if(!StringUtils.isEmpty(element))
				tx.sadd(key, element);
			tx.exec();
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
			throw e;
		} finally {
			if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
		}
	}

    /**
	 * setExpire(这里用一句话描述这个方法的作用) 设置key的超时时间
	 * 
	 * @param @param token
	 * @param @param expireSeconds
	 * @return void
	 * @Exception 异常对象
	 */
    public void setExpire(String key, int liveTime)
    {

        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
            instance.expire(key, liveTime);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
            throw e;
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }


    }

    /**
	 * delKeys(这里用一句话描述这个方法的作用) 批量删除
	 * 
	 * @param @param keys
	 * @return void
	 * @Exception 异常对象
	 */
    public void delKeys(Set<String> keys)
    {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
            for (String key : keys)
				instance.del(key);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }

    }
    
    public String type(String key) {
    	 ShardedJedis instance = null;
    	 boolean borrowOrOprSuccess = true;
    	 String type = null;
    	 try {
    		 instance = shardJedisPool.getResource();
    		 type = instance.type(key);
         } catch (JedisConnectionException e) {
             borrowOrOprSuccess = false;
             if (instance != null)
 				shardJedisPool.returnBrokenResource(instance);
         } finally {
             if (borrowOrOprSuccess)
 				shardJedisPool.returnResource(instance);
         }
    	 return type;
    }
    
    public void lpush(String keys,String values)
    {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
			instance.lpush(keys, values);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }

    }
    
    
    public List<String> lrange(String keys)
    {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
			return instance.lrange(keys, 0L, -1L);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
            return null;
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }
    }
    
    public String hget(String key,String value)
    {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
			return instance.hget(key,value);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
            return null;
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }
    }
    

    public void hset(String key,String field,String value)
    {
        ShardedJedis instance = null;
        boolean borrowOrOprSuccess = true;
        try {
            instance = shardJedisPool.getResource();
            instance.hset(key, field, value);
        } catch (JedisConnectionException e) {
            borrowOrOprSuccess = false;
            if (instance != null)
				shardJedisPool.returnBrokenResource(instance);
        } finally {
            if (borrowOrOprSuccess)
				shardJedisPool.returnResource(instance);
        }
    }
    

}
