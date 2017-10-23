<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>My JSP 'demo.jsp' starting page</title>
	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/sfile/css/jquery-ui.css?${v}">
	
</head>

<body>
	
	<p>    
		<label>自动提示:</label>    
		<input type="text" id="tags" />
	</p>
	</br>
	
	<p>    
		<label>拼音模糊搜索补全:</label>    
		<input type="text" id="pySearch" />
	</p>
	</br>
	
	<div><a id="testIdList">测试传List参数</a></div>
	</br>
	
	<div>
		<img id="imgFile" src="http://img.jumper-health.com/img/food/303D48E853ACF569FDBD8B3418C58A44.jpg" width="150px" height="150px">
		<a id="download">测试下载网络文件</a>
	</div>
	</br>
	
	<p>生成二维码</p>
	<img src="${pageContext.request.contextPath}/userPage/generateQRCode" width="150px" height="150px">
	</br>
	
	<p>日期插件</p>
	<input id="birthday" readonly="readonly">
	
</body>
<jsp:include page="/WEB-INF/jsp/common/foot.jsp"></jsp:include>
<!-- 自动提示用到 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/jquery-ui.js?${v}" ></script>
<!-- layui日期插件 -->
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/laydate/laydate.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/layui/layui.js?${v}" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sfile/js/ajax/demo.js?${v}" ></script>
<script type="text/javascript">    
	$(document).ready(function() {
	    var availableTags = [
	      "ActionScript",
	      "AppleScript",
	      "Asp",
	      "BASIC",
	      "C",
	      "C++",
	      "Clojure",
	      "COBOL",
	      "ColdFusion",
	      "Erlang",
	      "Fortran",
	      "Groovy",
	      "Haskell",
	      "Java",
	      "JavaScript",
	      "Lisp",
	      "Perl",
	      "PHP",
	      "Python",
	      "Ruby",
	      "Scala",
	      "Scheme"
	    ];
	    var sou = [
			{ label: "Chinese", value: 11, sayHi: "你好" },
			{ label: "English", value: 12, sayHi: "Hello" },
			{ label: "Spanish", value: 3, sayHi: "Hola" },
			{ label: "Russian", value: 4, sayHi: "Привет" },
			{ label: "French", value: 5, sayHi: "Bonjour" },
			{ label: "Japanese", value: 6, sayHi: "こんにちは" }
		];
		
		//////////////////////////////////////////////////////////////
	});	
</script>   

</html>
