
var pageNum = 1;//上拉加载对应的页数
var list = [];
var isEndPullup = false;//是否结束上拉加载（没有数据）
$(function() {
	$.ajaxSetup({ //设置ajax为同步提交
        async: false
    });
	
	mui.init({
		swipeBack : true, //启用右滑关闭功能
		pullRefresh : {
			container : '#pullrefresh',
			down : {
				height : 50,//可选，默认50，触发下拉刷新拖动距离
				auto : true,//可选，默认false,自动下拉刷新一次
				contnetdown : "下拉可以刷新",//可选，在下拉可刷新状态时，下拉刷新控件上显示的标题内容
				contentover : "释放立即刷新",//可选，在释放可刷新状态时，下拉刷新控件上显示的标题内容
				contentrefresh : "正在刷新哦...",//可选，正在刷新状态时，下拉刷新控件上显示的标题内容
				callback : pulldownRefresh
			},
			up : {
				height : 50,
				auto : false,
				contentrefresh : '正在加载...',
				contentnomore : '没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
				callback : pullupRefresh
			}
		}
	});
	
	
	/**
	 * 下拉刷新具体业务实现
	 */
	function pulldownRefresh() {
		getPageData(0);//获取分页list数据
		initData(0);//标签赋值
		mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
	}
	
	/**
	 * 上拉加载具体业务实现
	 */
	function pullupRefresh() {
		setTimeout(function() {
			mui('#pullrefresh').pullRefresh().endPullupToRefresh(isEndPullup); //参数为true代表没有更多数据了。
			if (!isEndPullup) {
				pageNum++;
				getPageData(1);//获取分页list数据
				initData(1);//标签赋值
			}
		}, 800);
	}
	
	
	/**
	 * 下拉刷新具体业务实现
	 */
	/*function pulldownRefresh() {
		setTimeout(function() {
			var table = document.body.querySelector('.mui-table-view');
			var cells = document.body.querySelectorAll('.mui-table-view-cell');
			for (var i = cells.length, len = i + 3; i < len; i++) {
				var li = document.createElement('li');
				li.className = 'mui-table-view-cell';
				li.innerHTML = '<a class="mui-navigate-right">Item ' + (i + 1) + '</a>';
				//下拉刷新，新纪录插到最前面；
				table.insertBefore(li, table.firstChild);
			}
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
		}, 1500);
	}*/
	
	/**
	 * 上拉加载具体业务实现
	 */
	/*var count = 0;
	function pullupRefresh() {
		setTimeout(function() {
			mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
			var table = document.body.querySelector('.mui-table-view');
			var cells = document.body.querySelectorAll('.mui-table-view-cell');
			for (var i = cells.length, len = i + 5; i < len; i++) {
				var li = document.createElement('li');
				li.className = 'mui-table-view-cell';
				li.innerHTML = '<a class="mui-navigate-right">Item ' + (i + 1) + '</a>';
				table.appendChild(li);
			}
		}, 1500);
	}*/
});

/**
 * 获取分页list数据
 * @param type 0：下拉 1：上拉
 */

function getPageData(type) {
	var currPage = (type == 0) ? 1 : pageNum;
	var data = {pageNum : currPage, pageSize : CONST.tabPageSize, query : "天使医生11"};
	$.post(baseUrl + "/userPage/pageUserAjaxList", data, function(ret) {
		if (ret.msg != 1) {
			list = [];
			alert(ret.msgbox);
			return;
		}
		list = ret.data;
		if (list.length == 0 && type == 1) {
			//上拉加载的数据为空，此时应该是没有更多数据了
			isEndPullup = true;
		}
	}, "json");
}
/** 
 * 标签赋值list数据
 * @param type 0：下拉 1：上拉
 */
function initData(type) {
	var html = "";
	for (var i = 0; i < list.length; i++) {
		var obj = list[i];
		var isExist = false;//列表中是否已经有该条记录
		$(".itemli").each(function(i) {
			var userId = $(this).attr("userId");
			if (obj.id == userId) {
				isExist = true;
				return;
			}
		});
		if (isExist) {
			continue;
		}
		html += 
			'<li class="mui-table-view-cell itemli" userId=' + obj.id + '>' + 
				'<a class="mui-navigate-right">' + obj.nickName + "---" + obj.regTime + '</a>' + 
			'</li>';
	}
	if (type == 0) {
		$(".mui-table-view").prepend(html);
	} else {
		$(".mui-table-view").append(html);
	}
}