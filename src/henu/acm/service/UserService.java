package henu.acm.service;

import java.sql.SQLException;
import java.util.List;

import henu.acm.dao.UserInfoDao;
import henu.acm.entity.ZheXianBean;
import henu.acm.entity.User;

public class UserService {

	private UserInfoDao dao = new UserInfoDao();
	
	public List<ZheXianBean> getHomeData(Integer user){
		List<ZheXianBean> list = null;
		try {
			list = dao.getHomeData(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean checkEmail(String email) {
		try {
			return dao.checkEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkUsername(String username) {
		try {
			return dao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void userRegister(User user) {
		try {
			dao.userRegister(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User userLogin(String name, String password) {
		User user = null;
		try {
			user = dao.userLogin(name,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public void uploadInfo(User user) {
		try {
			dao.uploadInfo(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public User getUserInfo(String user_id) {
		User user = null;
		try {
			user = dao.getUserInfo(user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
