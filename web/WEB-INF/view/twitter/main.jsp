<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Twitter API Test with Scribe</title>
</head>
<body>
	<c:forEach items="${result}" var="twitter">
		<section>
			<img alt="user image" src="${twitter.profileImage}">
			<p>${twitter.nickname}</p>
			<p>${twitter.text}</p>
		</section>
	</c:forEach>
</body>
</html>