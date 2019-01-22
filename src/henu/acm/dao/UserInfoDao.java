package henu.acm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import henu.acm.entity.ZheXianBean;
import henu.acm.entity.User;
import henu.acm.utils.DataSourceUtils;

public class UserInfoDao {
	
	private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	
	//主页的折线图数据
	public List<ZheXianBean> getHomeData(Integer user) throws SQLException {
		String sql = " SELECT TO_DATE(st,'YYYY-MM-DD') as submit_time,num " + 
				" FROM ( " + 
				"	SELECT TO_CHAR(submit_time,'YYYY-MM-DD') as st, COUNT(DISTINCT(problem_id)) as num " + 
				"	FROM activites " + 
				"	WHERE MONTHS_BETWEEN(SYSDATE, SUBMIT_TIME)<=3 AND user_id=? AND submit_status LIKE 'Accepted' " + 
				"	GROUP BY TO_CHAR(submit_time,'YYYY-MM-DD') " + 
				" ) ORDER BY submit_time ";
		List<ZheXianBean> list = qr.query(sql, new BeanListHandler<ZheXianBean>(ZheXianBean.class), user);
		return list;
	}
	
	public boolean checkEmail(String email) throws SQLException {
		String sql = "select * from ac_user where user_email = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), email);
		if(user == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkUsername(String username) throws SQLException {
		String sql = "select * from ac_user where user_username = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), username);
		if(user == null) {
			return true;
		}else {
			return false;
		}
	}

	public void userRegister(User user) throws SQLException {
		String sql = "insert into ac_user(user_username,user_email,user_name,user_password) values(?,?,?,?)";
		qr.update(sql,user.getUser_username(),user.getUser_email(),user.getUser_name(),user.getUser_password());
	}

	public User userLogin(String name, String password) throws SQLException {
		String sql = "select * from ac_user where user_username = ? and user_password = ?";
		User user = qr.query(sql, new BeanHandler<User>(User.class), name, password);
		return user;
	}

	public void uploadInfo(User user) throws SQLException {
		if(user.getUser_photourl()==null||"".equals(user.getUser_photourl())) {
			String sql = "update ac_user set user_hdu=?,user_poj=?,user_cf=? where user_id = ?";
			qr.update(sql,user.getUser_hdu(),user.getUser_poj(),user.getUser_cf(),user.getUser_id());
		} else {
			String sql = "update ac_user set user_photourl=?,user_hdu=?,user_poj=?,user_cf=? where user_id = ?";
			qr.update(sql,user.getUser_photourl(),user.getUser_hdu(),user.getUser_poj(),user.getUser_cf(),user.getUser_id());
		}
	}

	public User getUserInfo(String user_id) throws SQLException {
		String sql = "select * from ac_user where user_id=?";
		User user = qr.query(sql, new BeanHandler<User>(User.class),user_id);
		return user;
	}
	
	public List<User> getAllUser() throws SQLException {
		String sql = "select * from ac_user";
		List<User> list = qr.query(sql, new BeanListHandler<User>(User.class));
		return list;
	}
	
}
