<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	main.jsp  
</h1>
<P>  The time on the server is ${serverTime}. </P>
	<form action="summoner" method="get">
		<input type="text" name="name"><input type="submit" value="검색">
	</form>
</body>
</html>
