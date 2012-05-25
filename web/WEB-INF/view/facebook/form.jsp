<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Facebook Form</title>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="<c:url value="/js/charCount.js"/>"></script>
	<style type="text/css">
		form {width: 500px;}
		textarea {width: 490px; height: 60px; border: 2px solid #CCC; padding: 3px; color: #555; resize: none;}
		.counter {
			position: absolute;
			right: 0;
			top: 0;
			font-weight: bold;
			color: #ccc;
		}
		.warning {color: #600;}
		.exceeded {color: #e00;}
		form section {position: relative; margin: 1em 0;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<form action="<c:url value="/facebook/form"/>" method="post">
		<section>
			<label for="statusUpdate">What's happening?</label>
			<textarea id="statusUpdate" name="message"></textarea>
			<label for="link">Link:</label>
			<input type="text" id="link" name="link"/>
			<input type="submit" value="Update"/>
		</section>
	</form>
</body>
</html>