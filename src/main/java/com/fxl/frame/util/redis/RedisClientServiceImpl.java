package com.fxl.frame.util.redis;

/*
 * @文件名： RedisClientServiceImpl.java
 * @创建人: qxf
 * @创建时间: 2014-7-28
 * @包名： com.fangle.dacits.common.redis.service.impl
 * @版本： 1.0
 * 描述:
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 类名称：RedisClientServiceImpl 类描述：Redis基础数据读写封装实现类 
 * 
 * @version 1.0
 */
@Service("redisClientService")
public class RedisClientServiceImpl implements IRedisClientService
{
	private final Logger log = Logger.getLogger(getClass());
    @Resource(name="redisClient")
    private RedisClient redisClient;

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#delKey(java.lang.String)
	 */
	@Override
    public void delKey(String key) {
		try{
			redisClient.del(key);
		}catch (Exception e){
			log.error("删除redis中指定key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getkeysLike(java.lang.String)
	 */
	@Override
    public Set<String> getkeysLike(String keyLike) {
		try{
			return redisClient.keys(keyLike);
		}catch (Exception e){
			log.error("获取key列表：" + keyLike + "失败", e);
		}
		return null;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setString(java.lang.String,
	 *      java.lang.String, int)
	 */
	@Override
    public void setString(String key, String value, int expireSeconds) {
		try{
			redisClient.set(key, value, expireSeconds);
		}catch (Exception e){
			log.error("设置key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setString(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public void setString(String key, String value) {
		try{
			redisClient.set(key, value);
		}catch (Exception e){
			log.error("设置key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getString(java.lang.String)
	 */
	@Override
    public String getString(String key) {
		try {
			return redisClient.get(key);
		} catch (Exception e) {
			log.error("获取key：" + key + "失败", e);
		}
		return null;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setList(java.lang.String,
	 *      java.util.List, int)
	 */
	@Override
    public void setList(String key, List<String> list, int expireSeconds) {
		try {
			redisClient.setList(key, list, expireSeconds);
		} catch (Exception e) {
			log.error("设置key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setList(java.lang.String,
	 *      java.util.List)
	 */
	@Override
    public void setList(String key, List<String> list) {
		try {
			redisClient.setList(key, list, -1);
		} catch (Exception e) {
			log.error("设置key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#addListMember(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public void addListMember(String key, String memberValue) {
		try {
			redisClient.addListMember(key, memberValue);
		} catch (Exception e) {
			log.error("向key：" + key + "中添加元素失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getList(java.lang.String)
	 */
	@Override
    public List<String> getList(String key) {
		try {
			return redisClient.getList(key);
		} catch (Exception e) {
			log.error("获取key：" + key + "失败", e);
		}
		return null;
	}

	/*******************************************************************/
	@Override
    public void setListStack(String key, List<String> list,int fixSize) {
	    try {
            redisClient.setListFromHeadWithFixSize(key, list,fixSize);
        } catch (Exception e) {
			log.error("设置key：" + key + "失败", e);
        }
	}

	@Override
    public void addStrToStack(String key, String data,int fixSize) {
	    try {
            redisClient.addListMemberFromHeadWithFixSize(key, data,fixSize);
        } catch (Exception e) {
			log.error("向key：" + key + "中添加元素失败", e);
        }
	}


	/*******************************************************************/

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setSet(java.lang.String,
	 *      java.util.Set, int)
	 */
	@Override
    public void setSet(String key, Set<String> set, int expireSeconds) {
		try {
			redisClient.setSet(key, set, expireSeconds);
		} catch (Exception e) {
			log.error("设置key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setSet(java.lang.String,
	 *      java.util.Set)
	 */
	@Override
    public void setSet(String key, Set<String> set) {
		try {
			redisClient.setSet(key, set, -1);
		} catch (Exception e) {
			log.error("设置key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#addSetMember(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public void addSetMember(String key, String memberValue) {
		try {
			redisClient.addSetMember(key, memberValue);
		} catch (Exception e) {
			log.error("添加元素key：" + key + "失败", e);
		}

	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#removeSetMember(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public void removeSetMember(String key, String memberValue) {
		try {
			redisClient.removeSetMember(key, memberValue);
		} catch (Exception e) {
			log.error("删除元素key：" + key + "失败", e);
		}

	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#isContainMemberOfSet(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public boolean isContainMemberOfSet(String key, String memberValue) {
		try {
			return redisClient.isContainMemberOfSet(key, memberValue);
		} catch (Exception e) {
			log.error("判断元素key：" + key + "失败", e);
		}
		return false;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getSet(java.lang.String)
	 */
	@Override
    public Set<String> getSet(String key) {
		try {
			return redisClient.getSet(key);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setMap(java.lang.String,
	 *      java.util.Map, int)
	 */
	@Override
    public void setMap(String key, Map<String, String> map, int expireSeconds) {

		try {
			redisClient.setMap(key, map, expireSeconds);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setMap(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
    public void setMap(String key, Map<String, String> map) {

		try {
			redisClient.setMap(key, map, -1);
		} catch (Exception e) {
			log.error("设置元素key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#addMapMember(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
    public void addMapMember(String key, String mapKey, String mapValue) {
		try {
			redisClient.addMapMember(key, mapKey, mapValue);
		} catch (Exception e) {
			log.error("添加元素key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getMapValue(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public String getMapValue(String key, String mapKey) {

		try {
			return redisClient.getMapValue(key, mapKey);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;

	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#deleteMapMember(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
    public void deleteMapMember(String key, String mapKey) {

		try {
			redisClient.deleteMapMember(key, mapKey);
		} catch (Exception e) {
			log.error("删除元素key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getMap(java.lang.String)
	 */
	@Override
    public Map<String, String> getMap(String key) {

		try {
			return redisClient.getMap(key);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getMapAllKeys(java.lang.String)
	 */
	@Override
    public Set<String> getMapAllKeys(String key) {

		try {
			return redisClient.getMapAllKeys(key);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getMapAllValues(java.lang.String)
	 */
	@Override
    public List<String> getMapAllValues(String key) {

		try {
			return redisClient.getMapAllValues(key);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;
	}

    /**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#getSerializableObject(java.lang.String)
	 */
	@Override
    public Serializable getSerializableObject(String key) {
		try {
			return redisClient.getSerializableObject(key);
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setSerializableObject(java.lang.String,
	 *      java.io.Serializable, int)
	 */
	@Override
    public void setSerializableObject(String key, Serializable bean,
			int expireSeconds) {
		try {
			redisClient.setSerializableObject(key, bean, expireSeconds);
		} catch (Exception e) {
			log.error("设置元素key：" + key + "失败", e);
		}
	}

	/**
	 * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setSerializableObject(java.lang.String,
	 *      java.io.Serializable)
	 */
	@Override
    public void setSerializableObject(String key, Serializable bean) {
		try {
			redisClient.setSerializableObject(key, bean, -1);
		} catch (Exception e) {
			log.error("设置元素key：" + key + "失败", e);
		}
	}

	@Override
    public List<String> getSetScan(String key, String matchMergeStr) {
		try {
			return redisClient.getSetScan(key, matchMergeStr).getResult();
		} catch (Exception e) {
			log.error("获取元素key：" + key + "失败", e);
		}
		return null;
	}
	@Override
    public List<String> getKeyScan(String keyPattern, String matchMergeStr) {
		try {
			return redisClient.getKeyScan(keyPattern, matchMergeStr).getResult();
		} catch (Exception e) {
			log.error("获取元素key：" + keyPattern + "失败", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.fangle.dacits.common.redis.service.IRedisClientService#addOrUpdateSetElementByScan(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
    public void addOrUpdateSetElementByScan(String cacheKeyParkingRealTimeSet,
			String delMatch, String element) {
		try {
			 redisClient.addOrUpdateSetElementByScan(cacheKeyParkingRealTimeSet, delMatch,element);
		} catch (Exception e) {
			log.error("修改或删除元素key：" + cacheKeyParkingRealTimeSet + "失败", e);
		}
	}

    /**
     * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#setExpire(java.lang.String, int)
     */
    @Override
    public void setExpire(String token, int expireSeconds)
    {

        try {
             redisClient.setExpire(token, expireSeconds);
        } catch (Exception e) {
			log.error("设置超时时间：" + token + "失败", e);
        }

    }

    /**
     * @see cn.com.jumper.anglesound.mobile.utils.redis.rMonitor.service.fangle.dacits.common.redis.service.IRedisClientService#delKeys(java.util.Set)
     */
    @Override
    public void delKeys(Set<String> keys)
    {
        try {
            if(CollectionUtils.isNotEmpty(keys))
				redisClient.delKeys(keys);
       } catch (Exception e) {
			log.error("删除keys列表失败", e);
       }
    }

    @Override
    public long dbsize()
    {
        return redisClient.dbSize();

    }

    @Override
    public void flush()
    {
        redisClient.flushDB();

    }

  /*  public List<String> scanAllStrValue(String match)
    {
        List<String> result = null;
        try
        {
            List<String> keyList = redisClient.getKeyScan(match, match).getResult();
            if (CollectionUtils.isNotEmpty(keyList))
            {
                result = new ArrayList<String>(keyList.size());
                for (String key : keyList)
					result.add(redisClient.get(key));
            }
        } catch (Exception e)
        {
			log.error("获取元素key：" + match + "失败", e);
        }
        return result;
    }*/
    @Override
	public List<String> scanAllStrValue(String match)
    {
        List<String> result = null;
        try
        {
            List<String> keyList = redisClient.getKeyScan(match, match).getResult();
            if (CollectionUtils.isNotEmpty(keyList))
            {
                result = new ArrayList<String>(keyList.size());
                for (String key : keyList)
					result.add(redisClient.get(key));
            }
        } catch (Exception e)
        {
			log.error("获取元素key：" + match + "失败", e);
        }
        return result;
    }
    
    @Override
	public Map scanAllMap(String match)
    {
        Map map=new HashMap();
        try
        {
            List<String> keyList = redisClient.getKeyScan(match, match).getResult();
            if (CollectionUtils.isNotEmpty(keyList))
            {
                for (String key : keyList)
                	map.put(key, redisClient.get(key));
            }
        } catch (Exception e)
        {
			log.error("获取元素key：" + match + "失败", e);
        }
        return map;
    }


	@Override
	public void publishKey(String key, String value) {
		redisClient.publish(key, value);

	}

	@Override
	public void lpush(String key, String value) {
		try {
            if(StringUtils.isNotEmpty(key))
				redisClient.lpush(key, value);
       } catch (Exception e) {
			log.error("lpush keys失败", e);
       }
		
	}
	/**
	 * 
	 * @param keys
	 * @return 返回所有的值 0 -1
	 */
	 @Override
	public List<String> lrange(String keys){
		 List<String> result = null;
	        try
	        {
	            List<String> keyList = redisClient.lrange(keys);
	            if (CollectionUtils.isNotEmpty(keyList))
	            {
	                result = keyList;
	            }
	        } catch (Exception e)
	        {
				log.error("获取元素key：" + keys + "失败", e);
	        }
	        return result;
	 }

	@Override
	public String hget(String key, String value) {
		try {
			return redisClient.hget(key,value);
		} catch (Exception e) {
			log.error("获取key：" + key + "失败", e);
		}
		return null;
	}

	@Override
	public void hset(String key, String field, String value) {
		try {
				redisClient.hset(key, field, value);
       } catch (Exception e) {
			log.error("hset keys失败", e);
       }
	}

	@Override
	public boolean exists(String key) {
		return redisClient.equals(key);
	}

}
