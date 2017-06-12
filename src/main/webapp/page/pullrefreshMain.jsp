<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<title>用户列表分页-基于mui的H5分页</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	
    <jsp:include page="common/head.jsp"></jsp:include>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/sfile/css/mui.min.css">
</head>

<body>
	<header class="mui-bar mui-bar-nav">
		<a id="back" class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 id="title" class="mui-title">下拉刷新和上拉加载更多</h1>
	</header>
	<div class="mui-content"></div>
</body>
<jsp:include page="common/foot.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/sfile/js/mui.min.js"></script>
<script type="text/javascript">
	//启用双击监听
	mui.init({
		gestureConfig:{
			doubletap:true
		},
		subpages:[{
			url:'pullrefreshSub.jsp',
			id:'pullrefreshSub.jsp',
			styles:{
				top: '45px',
				bottom: '0px',
			}
		}]
	});
	
	mui.plusReady(function(){
		var contentWebview = null;
		document.querySelector('header').addEventListener('doubletap',function () {
			if(contentWebview==null){
				contentWebview = plus.webview.currentWebview().children()[0];
			}
			contentWebview.evalJS("mui('#pullrefresh').pullRefresh().scrollTo(0,0,100)");
		});
	});
	
</script>
</html>
