package henu.acm.entity;

import java.util.Date;

//数据库实体类  activites表
public class Activites {
	
	private String aid;
	private Integer user_id;
	private String OJ_system;
	private String problem_id;
	private String submit_status;
	private Date submit_time;
	private String problem_url;
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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
		return submit_time;
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
