package com.fxl.frame.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.fxl.frame.common.ReturnMsg;

/**
 * 文件上传
 */
public class UploadUtils {

	public static String ERROR = "-1";

	private static final Logger log = Logger.getLogger(UploadUtils.class);

	/**
	 * 上传文件
	 * @param path
	 * @param files
	 * @return
	 */
	public static ReturnMsg sendMultyPartRequest(String path, HashMap<String, String> params, HashMap<String, File> files) {
		ReturnMsg ret = new ReturnMsg(0, "文件上传失败");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(path);
		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		if (params.size() > 0) {
			Set<String> paramKeys = params.keySet();
			Iterator<String> paramIterator = paramKeys.iterator();
			while (paramIterator.hasNext()) {
				String key = paramIterator.next();
				try {
					StringBody stringBody = new StringBody(params.get(key), Charset.forName(HTTP.UTF_8));
					multipartEntity.addPart(key, stringBody);
				} catch (UnsupportedEncodingException e) {
					return ret;
				}
			}
		}
		if (files.size() > 0) {
			Set<String> fileKeys = files.keySet();
			Iterator<String> fileIterator = fileKeys.iterator();
			while (fileIterator.hasNext()) {
				String key = fileIterator.next();
				FileBody fileBody = new FileBody(files.get(key));
				multipartEntity.addPart(key, fileBody);
			}
		}
		httpPost.setEntity(multipartEntity);

		try {
			HttpResponse response = httpClient.execute(httpPost);
			int statueCode = response.getStatusLine().getStatusCode();
			if (statueCode == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(), "utf-8");
				Map<String, Object> map = JSON.parseObject(result, Map.class);
				Map<String, Object> mapData = (Map<String, Object>) map.get("data");
				ret = new ReturnMsg(1, "文件上传成功", mapData.get("fileUrl"));
			}
		} catch (Exception e) {
			log.error(UploadUtils.class, e);
			// e.printStackTrace();
		} finally {
			try {
				multipartEntity.consumeContent();
				multipartEntity = null;
				httpPost.abort();
			} catch (UnsupportedOperationException e) {
				log.error(UploadUtils.class, e);
				// e.printStackTrace();
			} catch (IOException e) {
				log.error(UploadUtils.class, e);
				// e.printStackTrace();
			}
		}
		return ret;
	}


	public static void main(String[] args) {
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("fileData", new File("/Users/utoow/Desktop/1.jpg"));
		System.out.println(sendMultyPartRequest("http://127.0.0.1:8080/netFileTool/uploadFile.do", new HashMap<String, String>(), files));
		// System.out.println(sendMultyPartRequest("http://api.findkedo.com/fileserver/upload?path=2181283/123456.jpg",
		// new HashMap<String, String>(), files));

	}
}
