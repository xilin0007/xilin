package com.fxl.frame.util.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * 文件操作工具类
 * @Description TODO
 * @author fangxilin
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class FileUtils {
	/**
	 * 创建不存在的目录
	 * @param dir
	 * @return
	 */
	public static boolean mkNotExistsDirs(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}

	/**
	 * 创建父目录
	 */
	public static boolean mkNotExistsParentDirs(String path) {
		File file = new File(path);
		File pfile = file.getParentFile();
		if (!pfile.exists()) {
			return pfile.mkdirs();
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExt(String filename) {
		if (filename != null) {
			int index = filename.lastIndexOf(".");
			if (index > 0) {
				return filename.substring(index);
			}
		}
		return null;
	}

	/**
	 * 获取文件名称
	 * 
	 */
	public static String getFileName(String s) {
		int index = s.lastIndexOf("/");
		String name = null;
		if (index > -1) {
			name = s.substring(index + 1);
		} else {
			index = s.lastIndexOf(File.separator);
			if (index > 0) {
				name = s.substring(index + 1);
			}
		}
		return name;
	}

	public static boolean copy(URL url, String path) {
		boolean result = false;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			if (conn.getResponseCode() != 404) {
				is = conn.getInputStream();
				mkNotExistsParentDirs(path);
				IOUtils.copy(is, new FileOutputStream(path, false));
				result = true;
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	/**
	 * 按照文件最后修改日期倒序获取文件夹中所有文件
	 * @param path
	 * @return
	 */
	
	public static File [] getFiles(String path){
		File file = new File(path);   
        // get the folder list   
        File[] array = file.listFiles();   
        Arrays.sort(array, new Comparator<File>() {
        	   @Override
        		   public int compare(File file, File newFile) {
                       if (file.lastModified() < newFile.lastModified()) {
                           return 1;
                       } else if (file.lastModified() == newFile.lastModified()) {
                           return 0;
                       } else {
                           return -1;
                       }
        	   }
        	});
		return array;
	}
	/**
	 * 
	 * 保留文件夹中的30个文件
	 */
	public static void deleteFiles(String path){
		File[] array = FileUtils.getFiles(path);
		if(array.length>30){
			for(int i=30;i<array.length;i++){
				array[i].delete();
			}
		}
		
	}
	/**
	 * 将文件从一个文件夹复制到另外一个文件夹
	 * @param oldpath
	 * @param newpath
	 */
	public static void fileCp(String oldpathall, String newpath) throws IOException {
		String newpathall = newpath + File.separator + new File(oldpathall).getName();
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(oldpathall).getChannel();
			outputChannel = new FileOutputStream(newpathall).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
	
	/**
	 * 将多个文件合并成一个文件
	 * 第一个参数是合并后生成文件的路径
	 * 第二个参数是你需要合并的文本文件列表
	 */
	public static final int BUFSIZE = 1024 * 5;
	public static void mergeFiles(String outFile, String[] files) {
	    FileChannel outChannel = null;
	    System.out.println("Merge " + Arrays.toString(files) + " into " + outFile);
	    try {
	        outChannel = new FileOutputStream(outFile).getChannel();
	        for(String f : files){
	        	FileWriter writer = new FileWriter(f, true);
	        	writer.write("\r\n");
	        	writer.close();
	            Charset charset=Charset.forName("utf-8");
	            CharsetDecoder chdecoder=charset.newDecoder();
	            CharsetEncoder chencoder=charset.newEncoder();
	            FileChannel fc = new FileInputStream(f).getChannel(); 
	            ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
	            CharBuffer charBuffer=chdecoder.decode(bb);
	            ByteBuffer nbuBuffer=chencoder.encode(charBuffer);
	            while(fc.read(nbuBuffer) != -1){
	                bb.flip(); 
	                nbuBuffer.flip();
	                outChannel.write(nbuBuffer);
	                bb.clear();
	                nbuBuffer.clear();
	            }
	            fc.close();
	            new File(f).delete();
	        }
	        System.out.println("Merged!! ");
	    } catch (IOException ioe) {
	        ioe.printStackTrace();
	    } finally {
	        try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}
	    }
	}
	    
	    
    public static List<String> readTxtFile(String filePath){
        try {
            String encoding="UTF-8";
            File file=new File(filePath);
            List<String> list = new ArrayList<String>();
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
                return list;
	        }else{
	            System.out.println("找不到指定的文件");
	        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
		return null;
    }
	
	public static String readStringFromStream(InputStream in) throws IOException {
	    /*   StringBuilder sb = new StringBuilder();
	       for (int i = in.read(); i != -1; i = in.read()) {
	           sb.append((char) i);
	       }
	       in.close();
	       return sb.toString();*/
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		in.close();
		return new String(data,"UTF-8");
	}
}
