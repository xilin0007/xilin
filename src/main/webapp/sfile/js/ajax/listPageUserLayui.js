/** 基于layui的分页 */

//分页数据
var totalPage = 0;//总页数
var curr = 1;//当前页
var list = [];//数据

//页面所需后台的数据
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	var data = {pageNum : curr, pageSize : CONST.tabPageSize};
	//初始化分页
	initPage(data);
	
	//模糊查询
	$("#search-btn").click(function() {
		var query = $("input[name='query']").val().trim();
		data.pageNum = 1;
		data.query = query;
		//分页查询
		initPage(data);
	});
});


//初始化分页
function initPage(data) {
	//初始分页信息
	function init(data) {
		$.post(baseUrl + "/userPage/pageUserAjax", data, function(ret) {
			if (ret.msg != 1) {
				layer.msg(ret.msgbox, {time : 1000});
				return;
			}
			totalPage = ret.data.pages;
			curr = ret.data.pageNum;
			list = ret.data.list;
		}, "json");
	}
	init(data);
	//展示分页列表
	layui.use(["laypage"], function() {
		layui.laypage({
			cont : "page",
			pages : totalPage,
			curr : data.pageNum,
			skip : true,
			jump : function(pobj) {
				//得到了当前页，用于向服务端请求对应数据
				curr = pobj.curr;
				data.pageNum = curr;
				//跳转到某页，并初始化分页数据
				init(data);
				var html = "";
				for (var int = 0; int < list.length; int++) {
					var obj = list[int];
					html += 
						'<tr>' +
							'<td>' + obj.nickName + '</td>' +
							'<td>' + obj.mobile + '</td>' +
							'<td>' + obj.regTime + '</td>' +
						'</tr>';
				}
				$("#pageList").html(html);
			}
		});
	});
}
