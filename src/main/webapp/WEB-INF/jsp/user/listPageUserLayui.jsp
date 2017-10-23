<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>用户列表分页-基于layui</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/sfile/css/layui.css?${v}">
</head>

<body>
	<div style="margin: 10px auto; width: 60%;">
		<form id="pageForm">
			<!-- 搜索栏 -->
			<div style="float:left">
		        <input type="text" name="query" placeholder="请输入用户昵称"/>
	            <button class="btn btn-danger" id="search-btn">搜索</button>
	        </div>
	        
	        <table class="table">
		        <thead>  
		            <tr>
		                <th>昵称</th>
		                <th>电话号码</th>
		                <th>注册时间</th>
		            </tr>
		        </thead>
		        <!-- 分页数据 -->
                <tbody id="pageList">
		        	<%-- <tr>
			        	<td>${data.nickName }</td>
			        	<td>${data.mobile }</td>
			        	<td>${data.regTime }</td>
		        	</tr> --%>
				</tbody>
		    </table>
		    <!-- 分页信息 -->
            <div id="page" style="float: right;"></div>
		</form>
	</div>
</body>
<jsp:include page="/WEB-INF/jsp/common/foot.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/layer/layer.js?${v}" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/layui/layui.js?${v}" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/ajax/listPageUserLayui.js?${v}" ></script>
<script type="text/javascript">
	/* $(function(){
		
	}); */
</script>

</html>
