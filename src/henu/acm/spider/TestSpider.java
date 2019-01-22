package henu.acm.spider;

import org.junit.Test;

import com.zzz.spider.request.HttpRequest;
import com.zzz.spider.request.JsoupRequest;

public class TestSpider {
	
	@Test
	public void spider() throws Exception {
		HttpRequest request = new JsoupRequest();
		request.setUrl("https://vjudge.net/status#un=henuwhr&OJId=All&probNum=&"
				+ "res=0&language=&onlyFollowee=false");
		String string = request.doGet().toString();
		System.out.println(string);
	}
	
}
