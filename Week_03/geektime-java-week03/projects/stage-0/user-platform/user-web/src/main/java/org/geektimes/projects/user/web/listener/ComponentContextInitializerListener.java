package org.geektimes.projects.user.web.listener;

import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * {@link ComponentContext} 初始化器
 * ContextLoaderListener
 */
public class ComponentContextInitializerListener implements ServletContextListener {

    private ServletContext servletContext;


    /**
     * 注册MBean
     */
    public void registerMBean() {
        try {
            // 获取平台 MBean Server
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            // 定义 ObjectName
            ObjectName objectName = new ObjectName("org.geektimes.projects.user.management:type=Application");
            // 创建 ApplicationMBean 实例
            Application application = new Application("user-web");
            mBeanServer.registerMBean(application, objectName);
            logger.log(Level.INFO, "注册ApplicationMBean成功");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException("注册ApplicationMBean失败");
        }
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.servletContext = sce.getServletContext();
        ComponentContext context = new ComponentContext();
        context.init(servletContext);
        registerMBean();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        ComponentContext context = ComponentContext.getInstance();
//        context.destroy();
    }


    /**
     * 数据库初始化
     */
    private void schemaInitialize() {
        String[] sqlArray = loadSql();
        DBConnectionManager connectionManager = ComponentContext.getInstance().getComponent("bean/DBConnectionManager");

        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : sqlArray) {
                try {
                    statement.execute(sql);
                } catch (SQLException exception) {
                    ignoredIfDropSql(sql, exception);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "执行数据库初始化失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 如果是DROP语句执行失败，则忽略
     * @param sql
     * @param exception
     */
    private void ignoredIfDropSql(String sql, SQLException exception) {
        if (sql.contains("DROP")) {
            logger.warning("执行sql脚本失败, sql: " + sql + ", error: " + exception.getMessage());
        } else {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 加载数据库脚本
     * @return
     */
    private String[] loadSql() {
        String sqls = IOUtils.getResourceAsString(this.getClass(), "/META-INF/db/DDL/users_table_ddl.sql", "执行数据库初始化失败");
        logger.info("加载数据库脚本: " + sqls);
        return sqls.split(";");
    }
}
