package com.eventchatroom.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String eventID) {
		String key = new StringBuilder("eventID").append(":").append(eventID).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String eventID, String message) {
		String receiverKey = new StringBuilder("eventID").append(":").append(eventID).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(receiverKey, message);
		jedis.close();
	}

}
