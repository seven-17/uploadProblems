package henu.acm.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import henu.acm.entity.DynamicData;
import henu.acm.entity.PageBean;
import henu.acm.entity.RankBean;
import henu.acm.entity.SearchBean;
import henu.acm.entity.User;
import henu.acm.entity.ZheXianBean;
import henu.acm.service.DataService;
import net.sf.json.JSONArray;

//获取首页数据、排名、动态
@SuppressWarnings("serial")
public class ShowDataServlet extends BaseServlet {

	private DataService service = new DataService();
	
	//饼图数据
	public void getBingTuData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> list = service.getBingTuData();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}
	
	//首页折线图数据
	public void getIndexZheXianData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray array = new JSONArray();
		List<ZheXianBean> data = service.getZheXianData();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for(ZheXianBean z : data) {
			JSONArray item = new JSONArray();
			item.add(format.format(z.getSubmit_time()));
			item.add(z.getNum());
			array.add(item);
		}
		
		response.getWriter().write(array.toString());
	}
	
	//动态页搜索功能
	public void searchDynamic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SearchBean search = new SearchBean();
		
		String key = request.getParameter("key");
		if(key.equalsIgnoreCase("ac")||key.equalsIgnoreCase("accepted")||key.equalsIgnoreCase("accept")) {
			search.setSubmit_status("Accepted");
		}else if(key.equalsIgnoreCase("wa")||key.equalsIgnoreCase("wrong answer")||key.equalsIgnoreCase("wrong")) {
			search.setSubmit_status("Wrong");
		}else if(key.startsWith("poj")||key.equalsIgnoreCase("poj")) {
			search.setOJ_system("POJ");
		}else if(key.startsWith("hdu")||key.equalsIgnoreCase("hdu")) {
			search.setOJ_system("HDU");
		}else if(key.startsWith("cf")||key.equalsIgnoreCase("cf")||key.startsWith("codef")||key.startsWith("Codef")) {
			search.setOJ_system("Code Forces");
		}else {
			search.setUser_username(key);
			search.setUser_name(key);
		}
		
		String str = request.getParameter("currentPage");
		int currentPage = Integer.parseInt(str);
		
		PageBean<DynamicData> page = new PageBean<>();
		page.setCount(20);
		page.setCurrentPage(currentPage);
		
		List<DynamicData> list = service.searchDynamic(search,(currentPage-1)*page.getCount(),page.getCount()*currentPage);
		if(list.size()==0) {
			response.sendRedirect(request.getContextPath()+"/showData?method=showDynamic&currentPage=1");
			return ;
		}
		page.setList(list);
		
		int totalCount = service.totalSearchDynamic(search);
		page.setTotalCount(totalCount);
		page.setTotalPage((int)Math.ceil(page.getTotalCount()*1.0/page.getCount()));
		
		request.setAttribute("pageBean", page);
		request.setAttribute("lastSearch", key);
		request.getRequestDispatcher("/dynamic.jsp").forward(request, response);
	}
	
	//显示所有动态
	public void showDynamic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = request.getParameter("currentPage");
		int currentPage = Integer.parseInt(str);
		
		PageBean<DynamicData> page = new PageBean<DynamicData>();//分页对象
		page.setCount(20);
		page.setCurrentPage(currentPage);
		
		List<DynamicData> list = service.showDynamic((currentPage-1)*page.getCount(),page.getCount()*currentPage);
		page.setList(list);
		
		int totalCount = service.totalDynamicNum();
		page.setTotalCount(totalCount);
		page.setTotalPage((int)Math.ceil(page.getTotalCount()*1.0/page.getCount()));
		
		request.setAttribute("pageBean", page);
		request.getRequestDispatcher("/dynamic.jsp").forward(request, response);
	}
	
	//显示总排名
	public void showRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<RankBean> list = service.showRank();
		request.setAttribute("rank", list);
		request.getRequestDispatcher("/rank.jsp").forward(request, response);
	}

}