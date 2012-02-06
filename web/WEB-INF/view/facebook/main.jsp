<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Facebook API Test with Scribe</title>
</head>
<body>
	<c:forEach items="${statusList}" var="status">
		<section>
			<header>${status.id}</header>
			<article>${status.message}</article>
		</section>
	</c:forEach>
</body>
</html>