ssm项目小案例

swagger2页面访问路径：
	http://192.168.1.235:8080/template/swagger-ui.html

Druid数据库监控页面：
	http://192.168.1.235:8080/template/druid/login.html
	
bug：
	1.druid sevlet 与tk-filter gzip压缩不兼容，显示不出监控页面。
	原因：gzip压缩成压缩文件后，druid sevlet将压缩文件response.getWriter().write(obj)到浏览器后并没有
		设置response header的Content-Encoding为gzip，
	可以通过修改tk-filter源码不拦截压缩/druid路径下的资源