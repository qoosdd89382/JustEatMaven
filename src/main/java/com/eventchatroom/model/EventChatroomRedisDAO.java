package com.eventchatroom.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class EventChatroomRedisDAO implements EventChatroomDAOInterface {

	private static String auth = "123456";
	private static JedisPool pool = null;

	static {
		pool = JedisConnectionPool.getJedisPool();
	}

	@Override
	public void insert(EventChatroomVO eventChatroomVO) {
		Jedis jedis = null;

		try {
			jedis = pool.getResource();
			jedis.auth(auth);
			JSONObject chatObj = new JSONObject();
			chatObj.put("sendby", eventChatroomVO.getAccountID());
			chatObj.put("sendTime", eventChatroomVO.getMessageTime());
			chatObj.put("sendText", eventChatroomVO.getChatText());

			StringBuilder key = new StringBuilder().append(eventChatroomVO.getEventID()).append(":")
					.append(eventChatroomVO.getAccountID());

			jedis.lpush(key.toString(), chatObj.toString());
			System.out.println("新增成功");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public List<EventChatroomVO> getAllByText(int eventID, int accountID, String chatText) {
		Jedis jedis = null;
		List<EventChatroomVO> list = new ArrayList<EventChatroomVO>();

		try {
			jedis = pool.getResource();
			jedis.auth(auth);
			StringBuilder key = new StringBuilder().append(eventID).append(":").append(accountID);
			List<String> messageList = jedis.lrange(key.toString(), 0, -1);

//		messageList.stream().filter(m -> messageList.size()>0).forEach(action);
			if (messageList.size() > 0) {
				for (String messageListStr : messageList) {
					JSONObject jsonObj = new JSONObject(messageListStr);
					if ((jsonObj.getString("sendText")).contains(chatText)) {
						EventChatroomVO eventChatroom = new EventChatroomVO();
						eventChatroom.setAccountID(accountID);
						eventChatroom.setEventID(eventID);
						eventChatroom.setMessageTime(Timestamp.valueOf(jsonObj.getString("sendTime")));
						eventChatroom.setChatText(jsonObj.getString("sendText"));
						list.add(eventChatroom);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return list;
	}

	@Override
	public List<EventChatroomVO> getAllByMember(int eventID, int accountID) {
		Jedis jedis = null;
		List<EventChatroomVO> list = new ArrayList<EventChatroomVO>();

		try {
			jedis = pool.getResource();
			jedis.auth(auth);
			StringBuilder key = new StringBuilder().append(eventID).append(":").append(accountID);
			List<String> messageList = jedis.lrange(key.toString(), 0, -1);

//		messageList.stream().filter(m -> messageList.size()>0).forEach(action);
			if (messageList.size() > 0) {
				for (String messageListStr : messageList) {
					JSONObject jsonObj = new JSONObject(messageListStr);
					EventChatroomVO eventChatroom = new EventChatroomVO();
					eventChatroom.setAccountID(accountID);
					eventChatroom.setEventID(eventID);
					eventChatroom.setMessageTime(Timestamp.valueOf(jsonObj.getString("sendTime")));
					eventChatroom.setChatText(jsonObj.getString("sendText"));
					list.add(eventChatroom);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return list;
	}
	
	public static void main(String[] args) {
		EventChatroomVO vo = new EventChatroomVO();
		EventChatroomRedisDAO dao = new EventChatroomRedisDAO();
		List<EventChatroomVO> list;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String localDateTimeNow = LocalDateTime.now().format(formatter);
		Timestamp timestampNow = Timestamp.valueOf(localDateTimeNow);
//		==============================新增用======================
//		vo.setAccountID(100001);
//		vo.setEventID(300001);
//		vo.setMessageTime(timestampNow);
//		vo.setChatText("活動你好!");
//		dao.insert(vo);
//		==============================查詢用=======================
//		list = dao.getAllByMember(300001, 100001);
		list = dao.getAllByText(300001, 100001, "活動");
		if(list.size()>0) {
			for(EventChatroomVO one:list) {
				System.out.println("會員"+one.getAccountID()+":"+one.getChatText()+"("+one.getMessageTime()+")");
			}
		}
		if(pool!=null) {
			JedisConnectionPool.shutdownJedisPool();
		}
	}
}



class JedisConnectionPool {
	private static JedisPool pool = null;
	private static String host = "localhost";
	private static int port = 6379;

	private JedisConnectionPool() {

	}

	public static JedisPool getJedisPool() {
		if (pool == null) {
			synchronized (JedisPool.class) {
				if (pool == null) {
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxTotal(8); // 最大連線數
					config.setMaxIdle(8); // 最大閒置連線數
					config.setMaxWaitMillis(10000); // 最大等待時間10秒
					return new JedisPool(config, host, port);
				}
			}
		}
		return pool;
	}

	public static void shutdownJedisPool() {
		if (pool != null) {
			pool.destroy(); // 關閉連線池(不是歸還連線)
		}
	}
}
