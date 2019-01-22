package henu.acm.spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzz.spider.request.HttpRequest;
import com.zzz.spider.request.JsoupRequest;
import com.zzz.spider.selecter.Selectable;

import henu.acm.dao.GetAllPersonDataDao;

class HDOJSpider implements BaseSpider {

	private static final String BASE_URL = "http://acm.hdu.edu.cn";
	private static final String URL = "http://acm.hdu.edu.cn/status.php?user=";
	private static final String OJ_NAME = "HDU";
	
//	public static void main(String[] args) {
//		JSONObject json = new JSONObject();
//		json.put("user", "oddly0529");
//		json.put("last", "27115099");//Run Id
//		List<CommitSolution> list = new HDOJSpider().run(json);
//		for(CommitSolution com : list) {
//			System.out.println(com.getSubmitTime());
//		}
//		System.out.println(JSON.toJSONString(new HDOJSpider().run(json)));
//	}
	
	@Override
	public List<CommitSolution> run(JSONObject param) {
		final String user = param.getString("user");
		final String last = StringUtils.trimToEmpty(param.getString("last"));
		final String user_id = param.getString("user_id");
		
		if (StringUtils.isBlank(user)) {
			throw new RuntimeException("hdoj spider cannot run, because of param['user'] is blank");
		}

		final String finalUrl = URL + user + "&first=";
		//最终结果
		final List<CommitSolution> re = new ArrayList<>();
		GetAllPersonDataDao dataDao = new GetAllPersonDataDao();
		try {
			HttpRequest request = new JsoupRequest();
			String first = StringUtils.EMPTY;

			while (true) {
				request.setUrl(finalUrl.concat(first));

				//遍历结果，页面的结果比较鬼畜，标签都不成对。。。
				//意思是，选中路径为*/body/table/tr且align属性为center的标签
				List<Selectable> trs = request.doGet().xpathList("//body/table/tr[@align='center']");
				//保存当前页面的结果
				List<CommitSolution> r = new ArrayList<>(trs.size());

				for (Selectable tr : trs) {
					List<Selectable> tds = tr.xpathList("//td");
					
					first = tds.get(0).text();
					if(last.equals(first)||dataDao.isExistData(user_id+OJ_NAME+first)) {
						re.addAll(r);
						return re;
					}
					CommitSolution solution = new CommitSolution();
					
					solution.setId(Long.valueOf(first));
					solution.setFrom(OJ_NAME);
					solution.setSubmitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tds.get(1).text()));
					solution.setStatus(tds.get(2).text());
					solution.setProblem(tds.get(3).text());
					solution.setUser(tds.get(8).text());
					solution.setUrl(BASE_URL.concat(tds.get(3).xpath("//td/a/@href").get()));
					r.add(solution);
				}
				
				re.addAll(r);
				//一页固定15条
				if(r.size() < 15) {
					break;
				}
				//请求下一页，这个需要先了解网站逻辑
				//写的有点丑，讲究看
				first = String.valueOf(Long.valueOf(first) - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return re;
	}

}
