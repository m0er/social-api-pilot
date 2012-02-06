package moer.social.facebook;

import java.util.*;

public class Facebook {
	List<Status> data;

	public List<Status> getData() {
		return data;
	}

	public void setData(List<Status> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Facebook [data=" + data + "]";
	}
	
	public static class Status {
		private String id;
		private String message;
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
	
		@Override
		public String toString() {
			return "Statuses [id=" + id + ", message=" + message + "]";
		}
	}
}
