<%@ page import="java.util.List" %>
<%@ page import="org.kpi.lab2.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="/lab2">Refresh</a>
    <h1>Users list:</h1>
    <ul>
        <%
            List<User> users = (List<User>) request.getAttribute("users");
            for (User user : users) {
        %>
            <li><%=user.getName()%></li>
        <%
            }
        %>
    </ul>

    <form method="post" action="">
        <label for="user_name">Enter new user name:</label>
        <br>
        <input id="user_name" type="text" name="user_name">
    </form>
</body>
</html>
