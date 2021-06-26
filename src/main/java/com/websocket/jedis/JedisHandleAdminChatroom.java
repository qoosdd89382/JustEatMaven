package com.websocket.jedis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleAdminChatroom {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String sender, String receiver) {
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		List<String> historyData = jedis.lrange(key, 0, -1);	// 需要有順序性的資料結構(set不適合)，並且要允許重複文字(zset不適合)，順序為先進先出
		jedis.close();
		
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}

}
