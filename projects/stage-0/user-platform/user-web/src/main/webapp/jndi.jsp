<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DatabaseMetaData" %>
<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>

	Tomcat连接池测试,获取数据源 <br>
	<%
	Context inictx = null;
	try {
		inictx = new InitialContext();
		if (inictx==null) {
			throw new Exception("Can't create context!");
		}
		Context ctx = (Context) inictx.lookup("java:comp/env");

		DataSource ds = (DataSource) ctx.lookup("jdbc/UserPlatformDB");

		Connection connection = ds.getConnection();
		DatabaseMetaData metaData = connection.getMetaData();
		connection.close();
		out.println(metaData.getURL());
		out.println("连接成功！");
	} catch (Exception e) {
		e.printStackTrace();
	}


%>
</body>