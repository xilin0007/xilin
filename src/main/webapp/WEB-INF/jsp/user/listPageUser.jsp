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
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/sfile/css/jquery-ui.css">
	
</head>

<body>
	<form action="${pageContext.request.contextPath}/userPage/listPageUser" method="POST" id="pageForm">
		<!-- 搜索栏 -->
		<div class="col-lg-3" style="float:left">
            <div class="input-group">
                <input type="text" class="form-control" name="nickName" placeholder="请输入用户昵称" value="${nickName }" />
                <span class="input-group-btn">
                    <button class="btn btn-danger" type="submit">搜索</button>
                </span>
            </div>
        </div>
        
        <table class="bordered">
	        <thead>  
	            <tr>
	                <th>昵称</th>
	                <th>电话号码</th>
	                <th>注册时间</th>
	            </tr>
	        </thead>
	        <c:forEach items="${pageInfo.list }" var="data" varStatus="vs">
	        	<tr>${data.nickName }</tr>
	        	<tr>${data.mobile }</tr>
	        	<tr>${data.regTime }</tr>
	        </c:forEach>
	        <c:if test="${pageInfo == null || pageInfo.list == null || pageInfo.total <= 0 }">
				<tr>
					<td colspan="3" style="color:red">暂无记录</td>
				</tr>
			</c:if>
	    </table>
	    <jsp:include page="../common/page.jsp"></jsp:include>
	</form>
</body>
<jsp:include page="/WEB-INF/jsp/common/foot.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/jquery-1.10.2.min.js" ></script>
<script type="text/javascript">    
	/* $(function(){
		
	}); */
</script>   

</html>
