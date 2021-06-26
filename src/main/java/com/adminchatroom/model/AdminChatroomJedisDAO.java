package com.adminchatroom.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.websocket.jedis.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class AdminChatroomJedisDAO implements AdminChatroomDAOInterface {
//	private static String auth = "123456";
//	private static JedisPool pool = null;
//
//	static {
//		pool = JedisPoolUtil.getJedisPool();
//	}
//
//	@Override
//	public boolean insert(AdminChatroomVO adminChatroom) {
//		Jedis jedis = null;
//		boolean insertSuccess = false;
//
//		try {
//			jedis = pool.getResource();
//			jedis.auth(auth);
//
//			JSONObject jsonObj = new JSONObject();
//			jsonObj.put("sendBy", adminChatroom.getSendBy());
//			jsonObj.put("sendTime", adminChatroom.getSendTime());
//			jsonObj.put("sendText", adminChatroom.getSendText());
//
//			StringBuilder key = new StringBuilder().append("adminID").append(":")
//					.append(adminChatroom.getAccountID());
//			// 放
//			jedis.rpush(key.toString(), // key
//					jsonObj.toString() // value
//			);
//
//			insertSuccess = true;
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} finally {
//
//			if (jedis != null) {
//				jedis.close(); // 歸還連線
//			}
//		}
//		return insertSuccess;
//	}
//
//	@Override
//	public List<AdminChatroomVO> getAllByText(int accountID, int adminID, String sendText) {
//		Jedis jedis = null;
//		List<AdminChatroomVO> allAdminChatroom = new ArrayList<AdminChatroomVO>();
//
//		try {
//			jedis = pool.getResource();
//			jedis.auth(auth);
//
//			StringBuilder key = new StringBuilder().append(accountID).append(":").append(adminID);
//			List<String> jsonObjStrs = jedis.lrange(key.toString(), 0, -1); // 拿
//
//			if (jsonObjStrs.size() > 0) {
//				for (String jsonObjStr : jsonObjStrs) {
//					JSONObject jsonObj = new JSONObject(jsonObjStr);
//					if ((jsonObj.getString("sendText")).contains(sendText)) {
//						AdminChatroomVO adminChatroom = new AdminChatroomVO();
//						adminChatroom.setAccountID(accountID);
////						adminChatroom.setAdminID(adminID);
//						adminChatroom.setSendBy(jsonObj.getBoolean("sendBy"));
//						adminChatroom.setSendTime(Timestamp.valueOf(jsonObj.getString("sendTime")));
//						adminChatroom.setSendText(jsonObj.getString("sendText"));
//						allAdminChatroom.add(adminChatroom);
//					}
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} finally {
//
//			if (jedis != null) {
//				jedis.close(); // 歸還連線
//			}
//		}
//		return allAdminChatroom;
//	}
//
//	@Override
//	public List<AdminChatroomVO> getAllByMembers(int accountID, int adminID) {
//		Jedis jedis = null;
//		List<AdminChatroomVO> allAdminChatroom = new ArrayList<AdminChatroomVO>();
//
//		try {
//			jedis = pool.getResource();
//			jedis.auth(auth);
//
//			StringBuilder key = new StringBuilder().append(accountID).append(":").append(adminID);
//			List<String> jsonObjStrs = jedis.lrange(key.toString(), 0, -1); // 拿
//
//			if (jsonObjStrs.size() > 0) {
//				for (String jsonObjStr : jsonObjStrs) {
//					JSONObject jsonObj = new JSONObject(jsonObjStr);
//					AdminChatroomVO adminChatroom = new AdminChatroomVO();
//					adminChatroom.setAccountID(accountID);
////					adminChatroom.setAdminID(adminID);
//					adminChatroom.setSendBy(jsonObj.getBoolean("sendBy"));
//					adminChatroom.setSendTime(Timestamp.valueOf(jsonObj.getString("sendTime")));
//					adminChatroom.setSendText(jsonObj.getString("sendText"));
//					allAdminChatroom.add(adminChatroom);
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} finally {
//
//			if (jedis != null) {
//				jedis.close(); // 歸還連線
//			}
//		}
//		return allAdminChatroom;
//	}
//
//	public static void main(String args[]) {
//		AdminChatroomVO vo = new AdminChatroomVO();
//		AdminChatroomJedisDAO jdao = new AdminChatroomJedisDAO();
//		List<AdminChatroomVO> list;
//
//		// 測試 insert 成功
//		vo.setAccountID(100001);
////		vo.setAdminID(120001);
//		vo.setSendBy(false); // true 管理員 | false 會員
//		vo.setSendTime(new Timestamp(new Date().getTime()));
//		vo.setSendText("測試abcde");
//		if (jdao.insert(vo)) {
//			System.out.println("聊天紀錄新增成功");
//		}
//		System.out.println("=============");
//
////		// 測試 getAllByMembers & getAllByText 皆成功
//////		list = jdao.getAllByMembers(100001, 120001);
////		list = jdao.getAllByText(100001, 120001, "abc");
//////		list.forEach(one -> System.out.println(one.getSendText()));
////		if (list.size() > 0) {
////			for (AdminChatroomVO one : list) {
////				if (one.getSendBy())
////					System.out.println(
////							"管理員" + one.getAdminID() + ":	" + one.getSendText() + "(at " + one.getSendTime() + ")");
////				else
////					System.out.println(
////							"會員" + one.getAccountID() + ":	" + one.getSendText() + "(at " + one.getSendTime() + ")");
////			}
////		}
////
////		if (pool != null) {
//		JedisPoolUtil.shutdownJedisPool(); // 關閉連線池? servlet destroy() 用?
////		}
//	}

}

