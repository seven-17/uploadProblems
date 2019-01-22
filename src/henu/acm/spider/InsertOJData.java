package henu.acm.spider;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import henu.acm.dao.GetAllPersonDataDao;
import henu.acm.entity.Activites;
import henu.acm.entity.User;

public class InsertOJData {
	
	public void insertHduData(User user) {
		if(user.getUser_hdu()==null||"".equals(user.getUser_hdu())) return ;
		
		Integer user_id = user.getUser_id();
		
		JSONObject json = new JSONObject();
		json.put("user", user.getUser_hdu());
		json.put("user_id", user_id);
		
		List<CommitSolution> list = SpiderFactory.getSpiderByHDOJ().run(json);
		if(list!=null&&list.size()!=0)
			insertData(list, user_id);
	}
	
	public void insertCFData(User user) {
		if(user.getUser_cf()==null||"".equals(user.getUser_cf())) return ;
		
		Integer user_id = user.getUser_id();
		
		JSONObject json = new JSONObject();
		json.put("user", user.getUser_cf());
		json.put("user_id", user_id);
		
		List<CommitSolution> list = SpiderFactory.getSpdierByCodeForces().run(json);
		if(list!=null&&list.size()!=0)
			insertData(list, user_id);
		else {
			CodeForcesSpider.BASE_URL = "http://codeforc.es";
			list = SpiderFactory.getSpdierByCodeForces().run(json);
			if(list!=null&&list.size()!=0)
				insertData(list, user_id);
			CodeForcesSpider.BASE_URL = "http://codeforces.com";
		}
	}
	
	public void insertData(List<CommitSolution> list, Integer user_id) {
		GetAllPersonDataDao dataDao = new GetAllPersonDataDao();
		for(CommitSolution com : list) {
			Activites activites = new Activites();
			activites.setAid(user_id+com.getFrom()+com.getId());
			activites.setOJ_system(com.getFrom());
			activites.setProblem_id(com.getProblem());
			activites.setProblem_url(com.getUrl());
			activites.setSubmit_status(com.getStatus());
			activites.setSubmit_time(com.getSubmitTime());
			activites.setUser_id(user_id);
			try {
				dataDao.insertOjData(activites);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
