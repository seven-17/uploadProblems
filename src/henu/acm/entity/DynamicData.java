package henu.acm.entity;

import java.util.Date;

//动态数据集
public class DynamicData {
	
	private Integer user_id;
	private String user_username;
	private String user_name;
	private String user_photourl;
	private String OJ_system;
	private String problem_id;
	private String submit_status;
	private Date submit_time;
	private String problem_url;
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
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
	public String getUser_photourl() {
		return user_photourl;
	}
	public void setUser_photourl(String user_photourl) {
		this.user_photourl = user_photourl;
	}
	public String getOJ_system() {
		return OJ_system;
	}
	public void setOJ_system(String oJ_system) {
		OJ_system = oJ_system;
	}
	public String getProblem_id() {
		return problem_id;
	}
	public void setProblem_id(String problem_id) {
		this.problem_id = problem_id;
	}
	public String getSubmit_status() {
		return submit_status;
	}
	public void setSubmit_status(String submit_status) {
		this.submit_status = submit_status;
	}
	public Date getSubmit_time() {
		return new myDate(submit_time);
	}
	public void setSubmit_time(Date submit_time) {
		this.submit_time = submit_time;
	}
	public String getProblem_url() {
		return problem_url;
	}
	public void setProblem_url(String problem_url) {
		this.problem_url = problem_url;
	}
	
}

@SuppressWarnings("serial")
class myDate extends Date {
	private Date date;
	
	public myDate() {
		super();
	}
	
	public myDate(Date date) {
		super();
		this.date = date;
	}

	public String toString() {
		String str = date.toString();
		return str.substring(0, str.length()-2);
	}
}
