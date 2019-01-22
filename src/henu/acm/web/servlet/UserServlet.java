package henu.acm.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import henu.acm.entity.ZheXianBean;
import henu.acm.entity.User;
import henu.acm.service.UserService;
import henu.acm.utils.UUIDUtils;
/*
 **关于用户操作的方法合集
 * author:wpc
 */
@SuppressWarnings("serial")
public class UserServlet extends BaseServlet {

	private UserService service = new UserService();
	
	//个人主页折线图
	public String getZheXianData(String user_id)  {
		List<ZheXianBean> homeData = service.getHomeData(Integer.valueOf(user_id));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "[";
		
		for(ZheXianBean h : homeData) {
			if(str.equals("[")) {
				str+="['"+format.format(h.getSubmit_time())+"',"+h.getNum()+"]";
			} else {
				str+=",['"+format.format(h.getSubmit_time())+"',"+h.getNum()+"]";
			}
		}
		str+="]";
		return str;
	}
	
	//准备个人主页的数据
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		User user = service.getUserInfo(user_id);
		
		request.setAttribute("data", new UserServlet().getZheXianData(user_id));
		
		request.setAttribute("user", user);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}
	
	//oj账号和头像上传
	public void uploadInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String path = this.getServletContext().getRealPath("temp");// 设置临时文件上传位置
		
		// 1.创建磁盘文件项工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*1024);// 设置缓存文件的大小  单位字节
		factory.setRepository(new File(path));// 设置临时文件的位置
		
		// 2.创建文件上传的核心类
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			// **********解析request获得的文件项集合***********
			@SuppressWarnings("unchecked")
			List<FileItem> parseRequest = upload.parseRequest(request);
			if(parseRequest!=null) {
				for(FileItem fileItem : parseRequest) {
					// 判断是不是一个普通的表单项
					boolean formField = fileItem.isFormField();
					if(formField) {// 普通的表单项
						String fieldName = fileItem.getFieldName();
						String fieldValue = fileItem.getString("utf-8");// 对表单项内容编码
						
						map.put(fieldName, fieldValue);
					}else {
						String huizui = fileItem.getName();
						if(huizui==null||"".equals(huizui)) {
							continue;
						}
						huizui = huizui.substring(huizui.indexOf("."), huizui.length());
						String fileName = UUIDUtils.getUUID()+huizui;
						InputStream in = fileItem.getInputStream();// 获得文件的输入流
						// 上传到指定的文件
						String realPath = this.getServletContext().getRealPath("/upload");//上传的头像文件夹
						OutputStream out = new FileOutputStream(realPath+"/"+fileName);
						IOUtils.copy(in, out);
						
						in.close();
						out.close();
						
						fileItem.delete();// 删除临时文件
						map.put("user_photourl", "upload/"+fileName);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		try {
			HttpSession session = request.getSession();
			User currentUser = (User) session.getAttribute("session_user");
			user.setUser_id(currentUser.getUser_id());
			BeanUtils.populate(user, map);
			
			service.uploadInfo(user);
			if(user.getUser_photourl()!=null&&!"".equals(user.getUser_photourl()))
				currentUser.setUser_photourl(user.getUser_photourl());
			currentUser.setUser_hdu(user.getUser_hdu());
			currentUser.setUser_poj(user.getUser_poj());
			currentUser.setUser_cf(user.getUser_cf());
			//user只是用来封装表单数据用的    并没有其他信息
			//用户登录后  user对象放在session中名为session_user
			
			session.setAttribute("session_user", currentUser);
			request.setAttribute("data", new UserServlet().getZheXianData(currentUser.getUser_id().toString()));
			request.setAttribute("user", currentUser);
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("user_username");
		String password = request.getParameter("user_password");
		User user = service.userLogin(name,password);
		if(user==null) {
			request.setAttribute("errorInfo", "用户名或者密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else {
			HttpSession session = request.getSession();
			session.setAttribute("session_user", user);//将用户存放到session
			request.setAttribute("user", user);
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}
	
	public void userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			service.userRegister(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("checkUsername");
		boolean b = service.checkUsername(username);
		response.getWriter().write("{\"isok\":"+b+"}");
	}
	
	public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("checkEmail");
		boolean b = service.checkEmail(email);
		response.getWriter().write("{\"isok\":"+b+"}");
	}
	
}