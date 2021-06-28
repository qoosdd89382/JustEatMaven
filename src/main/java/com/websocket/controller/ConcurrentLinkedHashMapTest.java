package com.websocket.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;
import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

public class ConcurrentLinkedHashMapTest {

	public static void main(String args[]) {

		Map<String, String> sessionsMap = 
				new ConcurrentLinkedHashMap.Builder<String, String>()
			    	.maximumWeightedCapacity(1000)
			    	.build();

		sessionsMap.put("1", "test");
		sessionsMap.put("2", "test");
		sessionsMap.put("3", "test");
		sessionsMap.put("4", "test");
		sessionsMap.put("4", "test2");
		
		Set<String> userIDs = new LinkedHashSet<String>();
		for(Map.Entry<String, String> entry : sessionsMap.entrySet()) {
			System.out.println(entry);
			userIDs.add(entry.getKey());
		}
		
		System.out.println(userIDs);
		

	}

}
