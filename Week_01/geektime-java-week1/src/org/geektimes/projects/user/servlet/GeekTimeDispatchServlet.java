package org.geektimes.projects.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.geektimes.projects.framework.mvc.annotation.RequestMapping;
import org.geektimes.projects.user.controller.UserController;

/**
 * 核心控制入口Servlet
 * @author Wuchubuzai
 * @date 2021-03-01
 */
public class GeekTimeDispatchServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//暂时写死的数组,未来改成动态获取
	protected Class<?>[] CONTROLLER_CLASS = new Class<?>[] {UserController.class};
	
	public Map<String,Method> handlerMap = new HashMap<String,Method>();
	
	public Map<String,Object> objMap = new HashMap<String,Object>();

	public GeekTimeDispatchServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		   //task1
	       String servletContextPath = request.getContextPath();
		   //task1/user/register      
	       String requestURI = request.getRequestURI();
		   //     /user/register      
	       String requestPath = requestURI.replace(servletContextPath, "");
	       
	       Method method = handlerMap.get(requestPath);
		   if(method != null) {
			   //取出实例
			   String controllPath = requestPath.substring(0,requestPath.lastIndexOf("/"));
			   Object instance = objMap.get(controllPath);
			   if(instance != null) {
				   //参数暂时写死,后续改成动态
				   method.invoke(instance, request,response);
			   }else {
				   response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				   response.getWriter().println("<h1>404</h1>");
	               return;
			   }
		   }else {
               response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			   response.getWriter().println("<h1>404</h1>");
               return;
		   }
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
	@Override
	public void init() throws ServletException {
		try {
			for(Class<?> controllerClass : CONTROLLER_CLASS) {
				RequestMapping requestMappingClass = controllerClass.getAnnotation(RequestMapping.class);
				if(requestMappingClass == null) {
					continue;
				}
				String requestPath = requestMappingClass.value();
				Method[] methods = controllerClass.getDeclaredMethods();
				for(Method method : methods) {
					RequestMapping methodClass = method.getAnnotation(RequestMapping.class);
					if(methodClass == null) {
						continue;
					}
					String methodPath = methodClass.value();
					handlerMap.put(requestPath + methodPath, method);
				}
				Object classInstance = controllerClass.newInstance();
				objMap.put(requestPath, classInstance);
			}
    		System.out.println("***************加载配置入口： GeekTimeDispatchServlet init finish..******************");
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.init();
	}
	
	
	
	
}
