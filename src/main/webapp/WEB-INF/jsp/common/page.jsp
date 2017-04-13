<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function jumpPage(pageNum) {
		$("#pageNum").val(pageNum);
		//提交外层的分页表单
		$("#pageForm").submit();
	}
</script>
<div class="row-fluid">
	<input type="hidden" name="pageNum" id="pageNum" value="1"/>
	<input type="hidden" id="currentNo" value="${pageInfo.pageNum}"/>
	<div class="clearFix"></div>
	<c:set var="offset" value="2"/>
	<c:set var="begin" value="${pageInfo.pageNum - offset}"/>
	<c:set var="end" value="${pageInfo.pageNum + offset}"/>
	<c:if test="${pageInfo.pageNum - offset <= 0}">
		<c:set var="end" value="${end + offset - pageInfo.pageNum + 1}"/>
	</c:if>
	<c:if test="${end >= pageInfo.pages}">
		<c:set var="beginOffset" value="${end - pageInfo.pages}"/>
		<c:set var="begin" value="${begin - beginOffset}"/>
		<c:set var="end" value="${pageInfo.pages}"/>
 	</c:if>
 	<c:if test="${begin < 1}">
 		<c:set var="begin" value="1"/>
 	</c:if>
	<div class="col-lg-12" style="padding-left:0;padding-right:0">
		<div class="dataTables_info" style="float:left" id="sample_1_info">${pageInfo.total} 条记录  ${pageInfo.pageNum }/${pageInfo.pages } 页</div>
		<div class="dataTables_paginate paging_bootstrap pagination">
			<ul>
				<li class="prev"><a href="javascript:jumpPage(1);"><span class="hidden-480">首页</span></a></li>
				<li class="prev <c:if test="${pageInfo.pageNum <= 1 }">disabled</c:if>"><a <c:if test="${pageInfo.pageNum > 1 }">href="javascript:jumpPage(${pageInfo.pageNum - 1});"</c:if>><span class="hidden-480">上一页</span></a></li>
				<c:forEach begin="${begin}" end="${end}" var="pno">
					<c:if test="${pageInfo.pageNum == pno}"><li class="active"><a href="#">${pno}</a></li></c:if>
					<c:if test="${pageInfo.pageNum != pno}"><li><a href="javascript:jumpPage(${pno})">${pno}</a></li></c:if>
				</c:forEach>
				<li class="next <c:if test="${pageInfo.pageNum >= pageInfo.pages }">disabled</c:if>"><a <c:if test="${pageInfo.pageNum < pageInfo.pages }">href="javascript:jumpPage(${pageInfo.pageNum + 1});"</c:if>><span class="hidden-480">下一页</span></a></li>
				<li class="next"><a href="javascript:jumpPage(${pageInfo.pages });"><span class="hidden-480">尾页</span></a></li>
			</ul>
		</div>
	</div>
</div>