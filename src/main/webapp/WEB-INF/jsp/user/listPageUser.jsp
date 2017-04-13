<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>用户列表分页</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/sfile/css/page.css?${v}">
	
</head>

<body>
	<div style="margin: 10px auto; width: 60%;">
		<form action="${pageContext.request.contextPath}/userPage/listPageUser" method="POST" id="pageForm">
			<!-- 搜索栏 -->
			<div style="float:left">
		        <input type="text" name="nickName" placeholder="请输入用户昵称" value="${nickName }" />
	            <button class="btn btn-danger" type="submit">搜索</button>
	        </div>
	        
	        <table class="table">
		        <thead>  
		            <tr>
		                <th>昵称</th>
		                <th>电话号码</th>
		                <th>注册时间</th>
		            </tr>
		        </thead>
		        <tbody>
			        <c:forEach items="${pageInfo.list }" var="data" varStatus="vs">
			        	<tr>
				        	<td>${data.nickName }</td>
				        	<td>${data.mobile }</td>
				        	<td>${data.regTime }</td>
			        	</tr>
			        </c:forEach>
			        <c:if test="${pageInfo == null || pageInfo.list == null || pageInfo.total <= 0 }">
						<tr>
							<td colspan="3" style="color:red">暂无记录</td>
						</tr>
					</c:if>
				</tbody>
		    </table>
		    <jsp:include page="../common/page.jsp"></jsp:include>
		</form>
	</div>
</body>
<jsp:include page="/WEB-INF/jsp/common/foot.jsp"></jsp:include>
<script type="text/javascript">    
	/* $(function(){
		
	}); */
</script>   

</html>
