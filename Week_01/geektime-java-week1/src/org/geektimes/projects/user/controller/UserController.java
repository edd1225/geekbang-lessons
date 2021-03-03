package org.geektimes.projects.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.geektimes.projects.framework.mvc.annotation.RequestMapping;
import org.geektimes.projects.user.entity.User;
import org.geektimes.projects.user.service.UserService;

@RequestMapping("/user")
public class UserController {
	
	
	private UserService userService = new UserService();
	
	public void writeError(HttpServletResponse response,String content) throws IOException {
		response.getWriter().println(content);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping("/register")
	public void register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		System.out.println(userName + "....." + email);
		if(userName == null || userName.trim().length() == 0) {
			writeError(response,"未指定用户名称");
			return;
		}
		if(email == null || email.trim().length() == 0) {
			writeError(response,"未指定邮箱");
			return;
		}
		if(phone == null || phone.trim().length() == 0) {
			writeError(response,"未指定电话信息");
			return;
		}
		//开始注册
		User user = new User();
		user.setEmail(email);
		user.setName(userName);
		user.setPhone(phone);
		
		userService.saveUser(user);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	
	

}
