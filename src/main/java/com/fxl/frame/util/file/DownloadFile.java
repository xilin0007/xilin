package com.fxl.frame.util.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class DownloadFile {
	
	private static final String FILE_DIR = System.getProperty("java.io.tmpdir");
	
	public static String download(String urlString, String fileName) {
		System.out.println("download start.......");
		// 下载网络文件  
		String filePath = FILE_DIR + fileName;
        int byteread = 0;
        InputStream inStream = null;
        FileOutputStream outStream = null;
        try {
        	URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            outStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                //System.out.println(bytesum);
                outStream.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
        	IOUtils.closeQuietly(inStream, outStream);
        }
		System.out.println("download over.......");
		return filePath;
	} 
}
