package org.geektimes.projects.user.web.controller;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * Copyright @ 2019 Citycloud Co. Ltd.
 * All right reserved.
 * 主页
 *
 * @author edd1225
 * @Description: java类作用描述
 * @create 2021/3/1 12:35
 **/
public class test {
    public static void main(String[] args) throws Throwable {
        Context inictx = new InitialContext();
        if (inictx==null) {
            throw new Exception("Can't create context!");
        }
        Context ctx = (Context) inictx.lookup("java:comp/env");

        DataSource ds = (DataSource) ctx.lookup("jdbc/UserPlatformDB");

        Connection connection = ds.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData.getURL());
    }
}
