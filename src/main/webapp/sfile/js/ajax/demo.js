
$(function() {
	//字段补全
	$("#tags").autocomplete({
    	autoFocus: true,
      	minLength: 1,        //触发自动填充需要的最小字符长度
      	source: function(request, response) {
      		var postData = {nickName: request.term, size: 15};
      		$.post(baseUrl + "/userPage/findByNickName", postData, function(ret) {
      			if(ret.msg == 1) {
      				//label：下拉框显示的列表，value：选中后输入框所赋的
      				var searchList = $.map(ret.data, function(item, index){
      					return {label: item.nickName, value: item.id, id: item.id};
      				});
      				response(searchList);
      			}else{
      				alert("搜索异常");
      			}
      		});
      	},
      	select: function(event, ui) {
      		alert("选中了："+ui.item.label+", id："+ui.item.id);
      	}
    });
	
	//拼音模糊搜索补全
	$("#pySearch").autocomplete({
    	autoFocus: true,
      	minLength: 1,        //触发自动填充需要的最小字符长度
      	source: function(request, response) {
      		var query = request.term.trim();
      		if (query == "") {
				return;
			}
      		var postData = {query: query, size: 20};
      		$.post(baseUrl + "/userPage/searchByPinyin", postData, function(ret) {
      			if (ret.msg != 1) {
      				alert("搜索异常");
      				return;
				}
  				var searchList = $.map(ret.data, function(item, index){
  					return {label: item.chinese, value: item.id, id: item.id};
  				});
  				response(searchList);
      		});
      	},
      	select: function(event, ui) {
      		alert("选中了："+ui.item.label+", id："+ui.item.id);
      	}
    });
    
    //测试传List参数
    $("#testIdList").on("click", function() {
    	var postDate = new Array();
    	postDate.push(3);
    	postDate.push(4);
		$.post(baseUrl + "/userPage/testIdList", {idList : postDate}, function(ret) {
			alert(ret.msgbox);				
		}, "json");
	});
	
	//测试下载网络文件
	$("#download").click(function() {
		var urlString = $("#imgFile").attr("src");
		window.location.href = baseUrl + "/userPage/downloadNet?urlString=" + urlString;
	});
	
	//日历启动
	layui.use('laydate', function() {
		var laydate = layui.laydate;
	});
	$("#birthday").click(function() {
		layui.laydate({
			elem : this,
			festival : true,
			max : laydate.now()
		});
	});
});