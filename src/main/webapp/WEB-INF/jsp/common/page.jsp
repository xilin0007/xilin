<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function jumpPage(pageNo) {
		$("#pageNo").val(pageNo);
		$("#form").submit();
	}
</script>
<div class="row-fluid">
	<input type="hidden" name="pageNo" id="pageNo" value="1"/>
	<input type="hidden" id="currentNo" value="${page.pageNo}"/>
	<div class="clearFix"></div>
	<c:set var="offset" value="2"/>
	<c:set var="begin" value="${page.pageNo-offset}"/>
	<c:set var="end" value="${page.pageNo+offset}"/>
	<c:if test="${page.pageNo-offset<=0}">
		<c:set var="end" value="${end+offset-page.pageNo+1}"/>
	</c:if>
	<c:if test="${end>=page.totalPages}">
		<c:set var="beginOffset" value="${end-page.totalPages}"/>
		<c:set var="begin" value="${begin-beginOffset}"/>
		<c:set var="end" value="${page.totalPages}"/>
 	</c:if>
 <c:if test="${begin<1}"><c:set var="begin" value="1"/></c:if>
	<div class="col-lg-12" style="padding-left:0;padding-right:0">
		<div class="dataTables_info" style="float:left" id="sample_1_info">${page.totalCount} 条记录  ${page.pageNo }/${page.totalPages } 页</div>
		<div class="dataTables_paginate paging_bootstrap pagination">
			<ul>
				<li class="prev"><a href="javascript:jumpPage(1);"><span class="hidden-480">首页</span></a></li>
				<li class="prev <c:if test="${page.pageNo <= 1 }">disabled</c:if>"><a <c:if test="${page.pageNo > 1 }">href="javascript:jumpPage(${page.pageNo-1});"</c:if>><span class="hidden-480">上一页</span></a></li>
				<c:forEach begin="${begin}" end="${end}" var="pno">
					<c:if test="${page.pageNo==pno}"><li class="active"><a href="#">${pno}</a></li></c:if>
					<c:if test="${page.pageNo!=pno}"><li><a href="javascript:jumpPage(${pno})">${pno}</a></li></c:if>
				</c:forEach>
				<li class="next <c:if test="${page.pageNo >= page.totalPages }">disabled</c:if>"><a <c:if test="${page.pageNo < page.totalPages }">href="javascript:jumpPage(${page.pageNo+1});"</c:if>><span class="hidden-480">下一页</span></a></li>
				<li class="next"><a href="javascript:jumpPage(${page.totalPages });"><span class="hidden-480">尾页</span></a></li>
			</ul>
		</div>
	</div>
</div>