package henu.acm.spider;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface BaseSpider {
	
	List<CommitSolution> run(JSONObject param);
	
}
