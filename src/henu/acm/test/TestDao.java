package henu.acm.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import henu.acm.utils.DataSourceUtils;
import oracle.jdbc.OracleTypes;

public class TestDao {

	@Test
	public void demo1() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from tab";
		List<Map<String, Object>> query = null;
		query = qr.query(sql, new MapListHandler());
		if(query==null) {
			System.out.println("null");
		}else {
			for (Map<String, Object> map : query) {
				System.out.println(map.get("TNAME"));
			}
		}
	}
	
	@Test
	public void demo2() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update ac_user set user_name=? where user_id = ?";
		qr.update(sql,"你好呀",10);
		DataSourceUtils.commitAndRelease();
	}
	
	@Test
	public void totalDynamicNum() throws SQLException {
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
		System.out.println(num);
	}
	
}
