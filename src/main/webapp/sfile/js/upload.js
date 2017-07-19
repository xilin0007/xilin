var upload_img_url = "";//上传图片路径
var imgUploadId = "";
$(document).ready(function() {
	/**
	 * 绑定图片上传空间方法
	 */
	$.fn.initOrangeUploader = function() {
		var time = new Date().getTime();
		$(this).each(function(){
			var thiz = $(this);
			var id = time++;
			imgUploadId += id+",";
			var uploaderHtml = "";
			var url = "";
			
			if($(this).val()!=null && $(this).val()!=""){
				url = $(this).val();
			}
			
			uploaderHtml += '<div class="imgPicker" style="width:215px;"><img id="imgPicker_'+id+'" style="cursor: pointer;" src="'+url+'" '
			+ 'class="img-thumbnail" alt="" onerror="onImgError(this)"/><div id="progressBarWrap_'+id+'" style="display:none;height:20px;">'
			+ '<div class="progress progress-striped active" id="progress"><div id="progressBar_'+id+'" '
			+ 'class="progress-bar progress-bar-success" role="progressbar" style="width: 0%">'
			+ '</div></div></div></div>';
			
			if (!$(this).next().hasClass("imgPicker")) {
				$(this).after(uploaderHtml);
				
				if (thiz.attr("disabled") == "disabled") {
					return false;
				}
				new plupload.Uploader({
					runtimes : 'html5,flash,silverlight,html4',
					browse_button : 'imgPicker_'+id+'', // you can pass in id...
					url : baseUrl + '/file/upload',
					chunks : false,
					multi_selection : false,
					unique_names : true,
					filters : {
						max_file_size : '3mb',
						mime_types : [ {
							title : "Image files",
							extensions : "jpg,gif,png,bmp,jpeg"
						}]
					},
					flash_swf_url : baseUrl +'/assets/lib/plupload/Moxie.swf',
					silverlight_xap_url : baseUrl + '/assets/lib/plupload//Moxie.xap',
					preinit : {
						Init : function(up, info) {
						},

						UploadFile : function(up, file) {
						}
					},
					init : {
						PostInit : function() {
						},

						UploadProgress : function(up, file) {
							$("#progressBar_" + id).css("width", file.percent - 10 + "%");
						},
						FilesAdded : function(up, files) {
							plupload.each(files, function(file) {
								previewImage(file, function(imgsrc) {
									$("#imgPicker_" + id).attr("src", imgsrc);
								});
							});
							$("#progressBarWrap_" + id).show();
							up.start();
						},
						FileUploaded : function(up, file, info) {
							var ret = JSON.parse(info.response);
							if (1 == ret.msg) {
								upload_img_url = ret.data;
								thiz.val("/"+upload_img_url);
								$("#progressBarWrap_" + id).fadeOut("slow");
								//CQAlert("图片上传成功");
							} else {
								/*layer.msg("图片上传失败", {
			  		      			icon: 1,
			  		      			shade: [0.5,'#000'],
			  		      			btn: ['确定']
			  		    		});*/
								alert("图片上传失败");
								$("#progressBar_" + id).removeClass("progress-bar-success");
								$("#progressBar_" + id).addClass("progress-bar-danger");
							}
						},
						BeforeUpload : function(up, files) {
							$("#progressBar_" + id).removeClass("progress-bar-danger");
							$("#progressBar_" + id).addClass("progress-bar-success");
						},
						
						UploadComplete : function(up, files) {
							up.refresh();
							$("#progressBar_" + id).css("width","100%");
							//thiz.validateField();
						},

						Error : function(up, args) {
							alert("图片上传错误");
							$("#progressBar_" + id).removeClass("progress-bar-success");
							$("#progressBar_" + id).addClass("progress-bar-danger");
						}
					}
				}).init();
			}
		});
	};
});

//plupload中为我们提供了mOxie对象
//有关mOxie的介绍和说明请看：https://github.com/moxiecode/moxie/wiki/API
//如果你不想了解那么多的话，那就照抄本示例的代码来得到预览的图片吧
function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数 
	if (!file || !/image\//.test(file.type))
		return; //确保文件是图片
	if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
		var fr = new mOxie.FileReader();
		fr.onload = function() {
			callback(fr.result);
			fr.destroy();
			fr = null;
		};
		fr.readAsDataURL(file.getSource());
	} else {
		var preloader = new mOxie.Image();
		preloader.onload = function() {
			preloader.downsize(300, 300);//先压缩一下要预览的图片,宽300，高300
			var imgsrc = preloader.type == 'image/jpeg' ? preloader
					.getAsDataURL('image/jpeg', 80) : preloader
					.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
			callback && callback(imgsrc); //callback传入的参数为预览图片的url
			preloader.destroy();
			preloader = null;
		};
		preloader.load(file.getSource());
	}
}

function onImgError(obj) {
	$(obj).attr("src", baseUrl + "/sfile/image/empty.png");
}