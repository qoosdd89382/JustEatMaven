package com.eventchatroom.model;

import java.util.Set;

public class EventChatroomState {
		private String type;
		private String eventID;
		// the user changing the state
		private String user;
		// total users
		private Set<String> users;

		public EventChatroomState(String type,String eventID,String user, Set<String> users) {
			super();
			this.type = type;
			this.eventID = eventID;
			this.user = user;
			this.users = users;
		}

		public String getEventID() {
			return eventID;
		}

		public void setEventID(String eventID) {
			this.eventID = eventID;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public Set<String> getUsers() {
			return users;
		}

		public void setUsers(Set<String> users) {
			this.users = users;
		}
}
