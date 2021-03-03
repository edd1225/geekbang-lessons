package org.geektimes.projects.user.web.controller;

import org.geektimes.web.mvc.controller.PageController;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * Copyright @ 2019 Citycloud Co. Ltd.
 * All right reserved.
 * 主页
 *
 * @author edd1225
 * @Description: java类作用描述
 * @create 2021/3/1 12:10
 **/

@Path("/common")
public class JNDIController  implements PageController {


    @GET
    @POST
    @Path("/jndi") // /hello/world -> HelloWorldController
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        return   "jndi.jsp";
    }
}