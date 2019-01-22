package henu.acm.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import henu.acm.entity.Activites;
import henu.acm.entity.DynamicData;
import henu.acm.entity.RankBean;
import henu.acm.entity.ZheXianBean;
import henu.acm.entity.SearchBean;
import henu.acm.entity.User;
import henu.acm.utils.DataSourceUtils;
import oracle.jdbc.OracleTypes;

public class GetAllPersonDataDao {

	private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	
	public List<ZheXianBean> getZheXianData() throws SQLException {
		String sql = " SELECT TO_DATE(st,'YYYY-MM-DD') as submit_time,num " + 
				" FROM ( " + 
				"	SELECT TO_CHAR(submit_time,'YYYY-MM-DD') as st,COUNT(DISTINCT(problem_id)) AS num " + 
				"	FROM activites " + 
				"	WHERE MONTHS_BETWEEN(SYSDATE,submit_time)<=12 AND submit_status LIKE 'Accepted' " + 
				"	GROUP BY TO_CHAR(submit_time,'YYYY-MM-DD') " + 
				" ) ORDER BY submit_time ";
		List<ZheXianBean> query = qr.query(sql, new BeanListHandler<ZheXianBean>(ZheXianBean.class));
		return query;
	}
	
	//插入oj数据
	public void insertOjData(Activites a) throws SQLException {
		String sql = "insert into activites values(?,?,?,?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?)";
		qr.update(sql,a.getAid(),a.getUser_id(),a.getOJ_system(),a.getProblem_id(),a.getSubmit_status(),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a.getSubmit_time()),a.getProblem_url());
	}
	
	//查询某一条提交记录是否存在
	public boolean isExistData(String a) throws SQLException {
		String sql = "select * from activites where aid = ?";
		Activites activites = qr.query(sql, new BeanHandler<Activites>(Activites.class), a);
		return activites==null?false:true;
	}
	
	public List<RankBean> showRank() throws SQLException {
		String sql = " SELECT a.user_id,user_name,aweekday,amonth,total " + 
				" FROM ( " + 
				"	SELECT ac_user.user_id AS user_id,user_name,COUNT(DISTINCT(problem_id)) AS aweekday " + 
				"	FROM ac_user LEFT JOIN activites " + 
				"	ON ac_user.user_id=activites.user_id AND submit_status LIKE 'Accepted' AND SYSDATE-submit_time<7 " + 
				"	GROUP BY ac_user.user_id,user_name " + 
				" ) a, ( " + 
				"	SELECT ac_user.user_id AS user_id,COUNT(DISTINCT(problem_id)) AS amonth " + 
				"	FROM ac_user LEFT JOIN activites " + 
				"	ON ac_user.user_id=activites.user_id AND submit_status LIKE 'Accepted' AND SYSDATE-submit_time<30 " + 
				"	GROUP BY ac_user.user_id " + 
				" ) b, ( " + 
				"	SELECT ac_user.user_id AS user_id,COUNT(DISTINCT(problem_id)) AS total " + 
				"	FROM ac_user LEFT JOIN activites " + 
				"	ON ac_user.user_id=activites.user_id AND submit_status LIKE 'Accepted' " + 
				"	GROUP BY ac_user.user_id " + 
				" ) c " + 
				" WHERE a.user_id = b.user_id AND b.user_id = c.user_id " + 
				" ORDER BY total DESC";
		List<RankBean> list = qr.query(sql, new BeanListHandler<RankBean>(RankBean.class));
		return list;
	}

	public List<DynamicData> showDynamic(int currentPage,int count) throws SQLException {
		String sql = " SELECT * FROM ( " + 
				"	SELECT a.*,ROWNUM r " + 
				"	FROM ( " + 
				"		SELECT ac_user.user_id,user_username,user_name,user_photourl,OJ_system,problem_id,submit_status,submit_time,problem_url " + 
				"		FROM ac_user,activites " + 
				"		WHERE ac_user.user_id = activites.user_id " + 
				"		ORDER BY submit_time DESC " + 
				"	) a where ROWNUM<=? " + 
				" ) where r>=? ";
		List<DynamicData> list = qr.query(sql, new BeanListHandler<DynamicData>(DynamicData.class),count,currentPage+1);
		return list;
	}
	
	public List<DynamicData> searchDynamic(SearchBean search,int currentPage,int count) throws SQLException {
		String sql = " SELECT * FROM ( " + 
				"	SELECT b.*,ROWNUM r " + 
				"	FROM ( " + 
				"			SELECT * FROM ( " + 
				"				SELECT ac_user.user_id,user_username,user_name,user_photourl,OJ_system,problem_id,submit_status,submit_time,problem_url " + 
				"				FROM ac_user,activites " + 
				"				WHERE ac_user.user_id = activites.user_id " + 
				"			) a " + 
				"			WHERE user_username = ? OR user_name=? OR submit_status like ? OR OJ_system like ? " + 
				"			ORDER BY submit_time DESC " + 
				"	) b where ROWNUM<=? " + 
				" ) where r>=? ";
		List<DynamicData> list = qr.query(sql, new BeanListHandler<DynamicData>(DynamicData.class), search.getUser_username(),
				search.getUser_name(),"%"+search.getSubmit_status()+"%",search.getOJ_system(),count,currentPage+1);
		return list;
	}

//	public int totalDynamicNum() throws SQLException {
//		String sql = " SELECT count(*) " + 
//				" FROM ac_user,activites  " + 
//				" WHERE ac_user.user_id = activites.user_id ";
//		BigDecimal query = (BigDecimal) qr.query(sql, new ScalarHandler());
//		return query.intValue();
//	}
	
	//使用存储过程
	public int totalDynamicNum() throws SQLException {
		String sql = "{call getTotalNum(?)}";
		Connection conn = null;
		CallableStatement prepareCall = null;
		int num = 0;
		try {
			conn = DataSourceUtils.getConnection();
			prepareCall = conn.prepareCall(sql);
			//对out参数进行声明
			prepareCall.registerOutParameter(1, OracleTypes.NUMBER);
			prepareCall.execute();//执行
			num = prepareCall.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public int totalSearchDynamic(SearchBean search) throws SQLException {
		String sql = " SELECT COUNT(*) FROM( " + 
				" SELECT ac_user.user_id,user_username,user_name,user_photourl,OJ_system,problem_id,submit_status,submit_time " + 
				" FROM ac_user,activites " + 
				" WHERE ac_user.user_id = activites.user_id " + 
				" ) a " + 
				" WHERE user_username = ? OR user_name=? OR submit_status LIKE ? OR OJ_system LIKE ? ";
		BigDecimal qqq = (BigDecimal) qr.query(sql, new ScalarHandler(), search.getUser_username(),
				search.getUser_name(),"%"+search.getSubmit_status()+"%",search.getOJ_system());
		return qqq.intValue();
	}

	public List<User> getBingTuData() throws SQLException {
		String sql = " SELECT ac_user.user_id AS user_id,user_name,COUNT(DISTINCT(problem_id)) AS num " + 
				" FROM ac_user LEFT JOIN activites " + 
				" ON ac_user.user_id=activites.user_id AND submit_status LIKE 'Accepted' " + 
				" GROUP BY ac_user.user_id,user_name ";
		return qr.query(sql, new BeanListHandler<User>(User.class));
	}

}
