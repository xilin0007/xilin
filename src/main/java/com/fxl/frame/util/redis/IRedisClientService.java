package com.fxl.frame.util.redis;

/**
 * @文件名： RedisClientServiceImpl.java
 */
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类名称：IRedisClientService 类描述：Redis缓存基础数据读写封装接口 
 * 
 * @version 1.0
 */
public interface IRedisClientService {
	/**
	 * 删除：删除缓存中指定的key<br>
	 * delKey
	 * 
	 * @param @param key :缓存键Id<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void delKey(String key);

	/**
	 * 订阅：订阅缓存中指定的key<br>
	 * publishKey
	 * 
	 * @param @param key :缓存键Id<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void publishKey(String key, String value);

	/**
	 * 查询：通配符查询，模糊查询缓存中的key getkeysLike
	 * 
	 * @param @param keyLike :缓存key，如“parking*”：key以“parking”开头的<br>
	 * @param @return
	 * @return Set<String>
	 * @Exception 异常对象
	 */
	Set<String> getkeysLike(String keyLike);

	// ***********************字符串操作************************************************************

	/**
	 * 新增或覆盖：添加字符串类型的值到缓存，并设置超时时间<br>
	 * setString
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param value :值<br>
	 * @param @param expireSeconds :超时时间（单位秒）<br>
	 * @return void
	 */
	void setString(String key, String value, int expireSeconds);

	/**
	 * 新增或覆盖：添加字符串类型的值到缓存，常驻缓存<br>
	 * setString
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param value :值<br>
	 * @return void
	 */
	void setString(String key, String value);

	void lpush(String key, String value);
	String hget(String key, String value);
	void hset(String key,String field, String value);
	public Map scanAllMap(String match);
	boolean exists(String key);
	List<String> lrange(String keys);

	/**
	 * 查询：根据Key查询String类型的数据<br>
	 * getString
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @return String :String类型数据
	 * @Exception 异常对象 :数据类型异常
	 */
	String getString(String key);

	// ***********************List操作************************************************************

	/**
	 * 新增或覆盖：添加List类型的值到缓存，并设置超时时间<br>
	 * setList
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param list :值<br>
	 * @param @param expireSeconds :超时时间（单位秒）<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setList(String key, List<String> list, int expireSeconds);

	/**
	 * 新增或覆盖：添加List类型的值到缓存，常驻缓存<br>
	 * setList
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param list :值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setList(String key, List<String> list);

	/**
	 * 添加Member：向List中增加元素<br>
	 * 这里用一句话描述这个方法的作用 addListMember
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param memberValue :List Member的值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void addListMember(String key, String memberValue);

	/**
	 * 查询：根据Key查询List类型的数据<br>
	 * getList
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @param @throws JedisDataException
	 * @return List<String> :List<String>类型数据
	 * @Exception 异常对象
	 */
	List<String> getList(String key);

	// ***********************Set操作************************************************************
	/**
	 * 新增或覆盖：添加Set类型的值到缓存，并设置超时时间<br>
	 * setSet
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param set :值<br>
	 * @param @param expireSeconds :超时时间（单位秒）<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setSet(String key, Set<String> set, int expireSeconds);

	/**
	 * 新增或覆盖：添加Set类型的值到缓存，常驻缓存<br>
	 * setSet
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param set :值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setSet(String key, Set<String> set);

	/**
	 * 添加Member：向Set中增加元素<br>
	 * addSetMember
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param memberValue :Set Member的值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void addSetMember(String key, String memberValue);

	/**
	 * 移除Member：移除Set中的指定内容的项<br>
	 * removeSetMember
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param memberValue :Set Member的值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void removeSetMember(String key, String memberValue);

	/**
	 * 判断存在：判断某个Set中是否有指定值的项 isContainMemberOfSet :缓存键Id<br>
	 * 
	 * @param @param key :Set Member的值<br>
	 * @param @param memberValue
	 * @param @return
	 * @return boolean
	 * @Exception 异常对象
	 */
	boolean isContainMemberOfSet(String key, String memberValue);

	/**
	 * 查询：根据Key查询Set类型的数据<br>
	 * getSet
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @param @throws JedisDataException
	 * @return Set<String> :Set<String>类型数据
	 * @Exception 异常对象
	 */
	Set<String> getSet(String key);

	// ***********************Map操作************************************************************

	/**
	 * 新增或覆盖：添加Map类型的值到缓存，并设置超时时间<br>
	 * setMap
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param map :值<br>
	 * @param @param expireSeconds :超时时间（单位秒）<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setMap(String key, Map<String, String> map, int expireSeconds);

	/**
	 * 新增或覆盖：添加Map类型的值到缓存，常驻缓存<br>
	 * setMap
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param map :值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setMap(String key, Map<String, String> map);

	/**
	 * 添加Member：向Map中增加 键-值<br>
	 * addMapMember
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param mapKey :MapKey键<br>
	 * @param @param mapValue :MapValue值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void addMapMember(String key, String mapKey, String mapValue);

	/**
	 * 查询：根据Key和mapKey查询Map的值<br>
	 * getMapValue
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param mapKey :MapKey键<br>
	 * @param @return
	 * @return String
	 * @Exception 异常对象
	 */
	String getMapValue(String key, String mapKey);

	/**
	 * 移除Member：移除Map中的指定mapKey的项<br>
	 * deleteMapMember
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param mapKey :MapKey键<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void deleteMapMember(String key, String mapKey);

	/**
	 * 查询：根据Key查询Map类型的数据<br>
	 * 这里用一句话描述这个方法的作用 getMap
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @return Map<String,String>
	 * @Exception 异常对象
	 */
	Map<String, String> getMap(String key);

	/**
	 * 查询：根据Key查询Map的 键集合<br>
	 * getMapAllKeys
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @return Set<String>
	 * @Exception 异常对象
	 */
	Set<String> getMapAllKeys(String key);

	/**
	 * 查询：根据Key查询Map的 值集合<br>
	 * getMapAllValues
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @return List<String>
	 * @Exception 异常对象
	 */
	List<String> getMapAllValues(String key);

	// ***********************java.io.Serializable对象操作************************************************************

	/**
	 * 新增或覆盖：添加系列化类型的bean到缓存，并设置超时时间<br>
	 * setSerializableObject
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param bean :值<br>
	 * @param @param expireSeconds :超时时间（单位秒）<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setSerializableObject(String key, Serializable bean, int expireSeconds);

	/**
	 * 新增或覆盖：添加系列化类型的bean到缓存，常驻缓存<br>
	 * setSerializableObject
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @param bean :值<br>
	 * @return void
	 * @Exception 异常对象
	 */
	void setSerializableObject(String key, Serializable bean);

	/**
	 * 根据Key获得序列化的对象 getSerializableObject
	 * 
	 * @param @param key :缓存键Id<br>
	 * @param @return
	 * @return Serializable :序列化对象
	 * @Exception 异常对象
	 */
	Serializable getSerializableObject(String key);

	/**
	 * 添加针对set的scan方法，用来针对值过滤
	 * 
	 * @param string
	 * @param matchMergeStr
	 * @return
	 */
	List<String> getSetScan(String string, String matchMergeStr);

	/**
	 * 添加针对key的scan方法，用来针对值过滤
	 * 
	 * @param keyPattern
	 * @param matchMergeStr
	 * @return
	 */
	List<String> getKeyScan(String keyPattern, String matchMergeStr);

	/**
	 * 修改或删除值核delMatcha相匹配的项，并插入新的element
	 * 
	 * @param cacheKeyParkingRealTimeSet
	 * @param delMatch
	 * @param string
	 */
	void addOrUpdateSetElementByScan(String cacheKeyParkingRealTimeSet,
			String delMatch, String element);

	/**
	 * setExpire(这里用一句话描述这个方法的作用) 设置某个key的超时时间
	 * 
	 * @param @param token
	 * @param @param expireSeconds
	 * @return void
	 * @Exception 异常对象
	 */
	void setExpire(String token, int expireSeconds);

	/**
	 * delKeys(这里用一句话描述这个方法的作用) 根据key列表批量删除
	 * 
	 * @param @param aa
	 * @return void
	 * @Exception 异常对象
	 */
	void delKeys(Set<String> aa);

	/**
	 * 将list数据全部压入栈 setListStack
	 * 
	 * @param @param key
	 * @param @param list
	 * @param @param fixSize
	 * @return void
	 * @Exception 异常对象
	 */
	public void setListStack(String key, List<String> list, int fixSize);

	/**
	 * 将数据压入栈：List数据的ltrim操作，模拟栈的压入操作 addStrToStack
	 * 
	 * @param @param key
	 * @param @param data
	 * @param @param fixSize
	 * @return void
	 * @Exception 异常对象
	 */
	public void addStrToStack(String key, String data, int fixSize);

	long dbsize();

	void flush();

	List<String> scanAllStrValue(String match);

}
