package henu.acm.spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.zzz.spider.request.HttpRequest;
import com.zzz.spider.request.JsoupRequest;
import com.zzz.spider.response.HttpResponse;
import com.zzz.spider.selecter.Selectable;

import henu.acm.dao.GetAllPersonDataDao;

class CodeForcesSpider implements BaseSpider {

	public static String BASE_URL = "http://codeforces.com";
	private static final String URL = BASE_URL+"/submissions/";
	private static final String OJ_NAME = "Code Forces";
	private static final int HalfOfMinute = 1000*30;
	
	/*public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("user", "seven-7");
		//json.put("last", "712210");// Run Id
		List<CommitSolution> list = new CodeForcesSpider().run(json);
		for(CommitSolution com : list) {
			System.out.println(com);
		}
	//	System.out.println(JSON.toJSONString(new CodeForcesSpider().run(json)));
	}*/

	@Override
	public List<CommitSolution> run(JSONObject param) {
		final String user = param.getString("user");
		final String user_id = param.getString("user_id");
		final String last = StringUtils.trimToEmpty(param.getString("last"));

		if (StringUtils.isBlank(user)) {
			throw new RuntimeException("code forces spider cannot run, because of param['user'] is blank");
		}

		final String finalUrl = URL + user + "/page/";
		final HttpRequest request = new JsoupRequest();
		request.setTimeout(HalfOfMinute);//设置超时时间
		
		int page = 1;
		Integer lastPage = null;
		final List<CommitSolution> re = new ArrayList<>();
		GetAllPersonDataDao dataDao = new GetAllPersonDataDao();
		try {
			while (true) {
				request.setUrl(finalUrl + page++);
				HttpResponse response = request.doGet();
				if (lastPage == null) {
					List<Selectable> list = response.xpathList("//div[@class='pagination']/ul/li");
					if (list == null || list.isEmpty()) {
						lastPage = 1;
					} else {
						lastPage = Integer.parseInt(list.get(list.size()-2).text());
					}
				}

				List<Selectable> trs = response
						.xpathList("//table[@class='status-frame-datatable']//tr[@data-submission-id]");
				if (trs == null || trs.isEmpty()) {
					return re;
				}

				List<CommitSolution> r = new ArrayList<>(trs.size());
				for (Selectable tr : trs) {
					List<Selectable> tds = tr.xpathList("//td");

					String id = tds.get(0).text();
					if (last.equals(id)||dataDao.isExistData(user_id+OJ_NAME+id)) {
						re.addAll(r);
						return re;
					}
					CommitSolution solution = new CommitSolution();
					solution.setId(Long.valueOf(id));
					solution.setFrom(OJ_NAME);
					solution.setSubmitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tds.get(1).text()));
					solution.setUser(tds.get(2).text());
					solution.setProblem(tds.get(3).text());
					solution.setUrl(BASE_URL.concat(tds.get(3).xpath("//td/a/@href").get()));
					solution.setStatus(tds.get(5).text());

					r.add(solution);
				}

				re.addAll(r);
				if (page > lastPage) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return re;
	}

}
