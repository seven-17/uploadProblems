package henu.acm.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * url中转站
 * author:wpc
 */
@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		try {
			String methodName = req.getParameter("method");
			Class<? extends BaseServlet> clazz = this.getClass();
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this,req,res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}