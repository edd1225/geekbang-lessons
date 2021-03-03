package org.geektimes.projects.user.web.controller;

import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Copyright @ 2019 Citycloud Co. Ltd.
 * All right reserved.
 * 主页
 *
 * @author edd1225
 * @Description: java类作用描述
 * @create 2021/3/1 12:00
 **/
@Path("/user")
public class RegisterController  implements PageController {
    @GET
    @POST
    @Path("/register") // /hello/world -> HelloWorldController
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "login-form.jsp";
    }

}
