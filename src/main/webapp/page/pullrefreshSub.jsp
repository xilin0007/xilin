<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<title>Hello MUI</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	
    <jsp:include page="common/head.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sfile/css/mui.min.css">
</head>

<body>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view mui-table-view-chevron">
				
			</ul>
		</div>
	</div>
</body>
<jsp:include page="common/foot.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/sfile/js/mui.min.js"></script>
<script src="${pageContext.request.contextPath}/sfile/js/ajax/pullrefreshSub.js"></script>
</html>
