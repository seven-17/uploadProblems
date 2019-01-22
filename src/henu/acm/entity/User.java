package henu.acm.entity;

//数据库实体中ac_user表
public class User {
	
	private Integer user_id;
	private String user_username;
	private String user_name;
	private String user_password;
	private String user_email;
	private String user_photourl;
	private String user_hdu;
	private String user_poj;
	private String user_cf;
	private Integer user_role;//暂时未用到
	
	private Integer num;//封装个人总ac数
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
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
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_photourl() {
		return user_photourl;
	}
	public void setUser_photourl(String user_photourl) {
		this.user_photourl = user_photourl;
	}
	public String getUser_hdu() {
		return user_hdu;
	}
	public void setUser_hdu(String user_hdu) {
		this.user_hdu = user_hdu;
	}
	public String getUser_poj() {
		return user_poj;
	}
	public void setUser_poj(String user_poj) {
		this.user_poj = user_poj;
	}
	public String getUser_cf() {
		return user_cf;
	}
	public void setUser_cf(String user_cf) {
		this.user_cf = user_cf;
	}
	public Integer getUser_role() {
		return user_role;
	}
	public void setUser_role(Integer user_role) {
		this.user_role = user_role;
	}
}
