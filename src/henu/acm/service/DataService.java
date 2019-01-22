package henu.acm.service;

import java.sql.SQLException;
import java.util.List;

import henu.acm.dao.GetAllPersonDataDao;
import henu.acm.entity.DynamicData;
import henu.acm.entity.RankBean;
import henu.acm.entity.ZheXianBean;
import henu.acm.entity.SearchBean;
import henu.acm.entity.User;

public class DataService {

	private GetAllPersonDataDao dao = new GetAllPersonDataDao();
	
	public List<ZheXianBean> getZheXianData(){
		List<ZheXianBean> list = null;
		try {
			list = dao.getZheXianData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<RankBean> showRank() {
		List<RankBean> list = null;
		try {
			list = dao.showRank();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DynamicData> showDynamic(int currentPage,int count) {
		List<DynamicData> list = null;
		try {
			list = dao.showDynamic(currentPage,count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<DynamicData> searchDynamic(SearchBean search,int currentPage,int count) {
		List<DynamicData> list = null;
		try {
			list = dao.searchDynamic(search,currentPage,count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int totalDynamicNum() {
		try {
			return dao.totalDynamicNum();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int totalSearchDynamic(SearchBean search) {
		try {
			return dao.totalSearchDynamic(search);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<User> getBingTuData() {
		List<User> list = null;
		try {
			list = dao.getBingTuData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
