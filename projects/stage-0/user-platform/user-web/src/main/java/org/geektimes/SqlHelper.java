package org.geektimes;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SqlHelper {

    private static Connection getConnection() throws ClassNotFoundException,
            SQLException {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            // 获取与逻辑名相关联的数据源对象
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/test");
            conn = ds.getConnection();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public static int executeNonQuery(String cmdText)
            throws ClassNotFoundException, SQLException {
        int result = -1;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(cmdText);
            result = ps.executeUpdate();
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            close(con, ps, null);
        }
        return result;
    }

    public static int executeScalar(String cmdText) throws SQLException,
            ClassNotFoundException {
        int result = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(cmdText);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch (Exception e) {
            System.out.println(e);
        } finally {
            close(con, ps, rs);
        }
        return result;
    }

    public static <T> List<T> executeList(Class<T> cls, String cmdText)
            throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<T>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(cmdText);
            rs = ps.executeQuery();
            while (rs.next()) {
                T obj = executeResultSet(cls, rs);
                list.add(obj);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            close(con, ps, rs);
        }
        return list;
    }

    public static <T> T executeEntity(Class<T> cls, String cmdText)
            throws SQLException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        T obj = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement(cmdText);
            rs = ps.executeQuery();
            while (rs.next()) {
                obj = executeResultSet(cls, rs);
                break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            close(con, ps, rs);
        }
        return obj;
    }

    private static <T> T executeResultSet(Class<T> cls, ResultSet rs)
            throws InstantiationException, IllegalAccessException, SQLException {
        T obj = cls.newInstance();
        ResultSetMetaData rsm = rs.getMetaData();
        int columnCount = rsm.getColumnCount();
//        Field[] fields = cls.getFields();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            for (int j = 1; j <= columnCount; j++) {
                String columnName = rsm.getColumnName(j);
                if (fieldName.equalsIgnoreCase(columnName)) {
                    Object value = rs.getObject(j);
                    field.setAccessible(true);
                    field.set(obj, value);
                    break;
                }
            }
        }
        return obj;
    }

    private static void close(Connection con, PreparedStatement ps, ResultSet rs)
            throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }
        if (ps != null ) {
            ps.close();
            ps = null;
        }
        if (con != null) {
            con.close();
            con = null;
        }
    }
}