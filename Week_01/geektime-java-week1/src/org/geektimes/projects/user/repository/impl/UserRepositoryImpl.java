package org.geektimes.projects.user.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.geektimes.projects.framework.jdbc.DBConnectionManager;
import org.geektimes.projects.user.entity.User;
import org.geektimes.projects.user.repository.IUserRepository;

public class UserRepositoryImpl implements IUserRepository {

	private final DBConnectionManager dbConnectionManager;

    public UserRepositoryImpl(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
        initTable();
    }

    private void initTable() {
    	Connection connection = null;
    	try {
    		connection = getNewConnection();
    		Statement statement = connection.createStatement();
    	    // 删除 users 表
//    	    System.out.println(statement.execute(DBConnectionManager.DROP_USERS_TABLE_DDL_SQL)); // false
    	    // 创建 users 表
    	    System.out.println(statement.execute(DBConnectionManager.CREATE_USERS_TABLE_DDL_SQL)); // false
    	    
    		System.out.println("***************系统启动初始化创建Derby用户表******************");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(connection != null) {
    			dbConnectionManager.releaseConnection();
    			dbConnectionManager.setConnection(null);
    		}
    	}
	}

	private Connection getConnection() {
        return dbConnectionManager.getConnection();
    }
	
    public boolean saveUser(User user) {
    	Connection connection = null;
    	try {
    		connection = getNewConnection();
    		PreparedStatement statement = connection.prepareStatement(DBConnectionManager.REGISTER_USER_DML_SQL);
    		statement.setObject(1, user.getName());
    		statement.setObject(2, "");
    		statement.setObject(3, user.getEmail());
    		statement.setObject(4, user.getPhone());
    		
    		int i = statement.executeUpdate();
    		if(i > 0) {
    			System.out.println("***************注册用户成功******************");
    			return true;
    		}else {
    			System.out.println("***************ERROR:注册用户失败******************");
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		if(connection != null) {
    			dbConnectionManager.releaseConnection();
    			dbConnectionManager.setConnection(null);
    		}
    	}
    	return false;
    }

    
    
    private Connection getNewConnection() throws Exception {
    	Connection connection = getConnection();
    	if(connection == null) {
    		connection = initDataBaseConnection();
    		dbConnectionManager.setConnection(connection);
    	}
    	return connection;
	}

	private Connection initDataBaseConnection() throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        String databaseURL = "jdbc:derby:/soft/derby/user-platform;create=true";
        Connection connection = DriverManager.getConnection(databaseURL);
        return connection;
	}




	
	
}
