package henu.acm.entity;

//查询用的实体
public class SearchBean {
	
	private String user_username;//账号
	private String user_name;//姓名
	private String submit_status;//ac、wa
	private String OJ_system;//oj名
	
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSubmit_status() {
		return submit_status;
	}
	public void setSubmit_status(String submit_status) {
		this.submit_status = submit_status;
	}
	public String getOJ_system() {
		return OJ_system;
	}
	public void setOJ_system(String oJ_system) {
		OJ_system = oJ_system;
	}
	
}
