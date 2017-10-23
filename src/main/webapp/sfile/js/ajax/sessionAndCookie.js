/**
 * jquery.session.js和jquery.cookie.js的用法
 */
//session
$.session.set('key', 'value');//添加数据
$.session.remove('key');//删除数据
$.session.get('key');//获取数据
$.session.clear();//清除数据

//cookie
$.cookie('the_cookie'); //读取Cookie值
$.cookie('the_cookie', 'the_value'); //设置cookie的值
$.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain: 'jquery.com', secure: true});//新建一个cookie 包括有效期 路径域名等
$.cookie('the_cookie', 'the_value'); //新建cookie
$.cookie('the_cookie', null); //删除一个cookie

/**
 *
	相关参数的解释
	expires: 365
	定义cookie的有效时间，值可以是一个（从创建cookie时算起，以天为单位）或一个Date。
	如果省略，那么创建的cookie是会话cookie，将在用户退出浏览器时被删除。
	 
	path: '/'
	默认情况：只有设置cookie的网页才能读取该cookie。
	定义cookie的有效路径。默认情况下，该参数的值为创建cookie的网页所在路径（标准浏览器的行为）。
	如果你想在整个网站中访问这个cookie需要这样设置有效路径：path: '/'。
	如果你想删除一个定义了有效路径的cookie，你需要在调用函数时包含这个路径:$.cookie('the_cookie', null, { path: '/' });。
	
	domain: 'example.com'
	默认值：创建cookie的网页所拥有的域名。
	 
	secure: true
	默认值：false。如果为true，cookie的传输需要使用安全协议（HTTPS）。
	 
	raw: true
	默认值：false。 默认情况下，读取和写入cookie的时候自动进行编码和解码（使用encodeURIComponent编码，decodeURIComponent解码）。
	要关闭这个功能设置raw: true即可。 
 */
