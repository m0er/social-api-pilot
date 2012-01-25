<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Twitter @Anywhere test page</title>
	<script src="http://platform.twitter.com/anywhere.js?id=IXw8Box7vGU37vCslSZRbw&v=1" 
			type="text/javascript"></script>
	<script type="text/javascript">
		twttr.anywhere(function(T) {
			T("article").linkifyUsers();
		});
	</script>
</head>
<body>
	<article>
		@Outsideris 대장님 안녕하세요 :-)
	</article>
</body>
</html>