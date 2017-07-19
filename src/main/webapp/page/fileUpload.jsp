<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<jsp:include page="common/head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/sfile/css/page.css?${v}">
	
</head>

<body>
	<div>
		图片文件
		<input type="hidden" id="imgUrl" class="orangeUploder" />
	</div>
</body>
<jsp:include page="common/foot.jsp"></jsp:include>
<script src="${pageContext.request.contextPath}/sfile/js/zui.min.js"></script>
<script src="${pageContext.request.contextPath}/sfile/js/plupload/plupload.full.min.js"></script>
<script src="${pageContext.request.contextPath}/sfile/js/upload.js"></script>
<script src="${pageContext.request.contextPath}/sfile/js/ajax/fileUpload.js"></script>
<script type="text/javascript">
	/* $(function(){
		
	}); */
</script>   

</html>
