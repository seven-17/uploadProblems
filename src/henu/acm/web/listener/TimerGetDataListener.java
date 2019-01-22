package henu.acm.web.listener;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import henu.acm.dao.UserInfoDao;
import henu.acm.entity.User;
import henu.acm.spider.InsertOJData;

public class TimerGetDataListener implements ServletContextListener {

	private final static int Fifteen_MINUTES = 1000*60*15;
	
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Class.forName("henu.acm.utils.DataSourceUtils");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		InsertOJData data = new InsertOJData();
		//周期性爬取
		final Is_insert_ok okk = new Is_insert_ok();
		okk.setOn();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(okk.getFlag()==false) return ;
				try {
					okk.setOff();
					List<User> list = new UserInfoDao().getAllUser();//得到所有的user
					for(User user : list) {
						data.insertHduData(user);
						data.insertCFData(user);
					}
					okk.setOn();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, new Date(), Fifteen_MINUTES);
		
	}
	
	public void contextDestroyed(ServletContextEvent sce) {}
	
}

class Is_insert_ok {
	private boolean flag;
	public void setOn() {
		flag = true;
	}
	public void setOff() {
		flag = false;
	}
	public boolean getFlag() {
		return flag;
	}
}