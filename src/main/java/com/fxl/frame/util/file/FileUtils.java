package com.fxl.frame.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

/**
 * 文件操作工具类
 * @Description TODO
 * @author fangxilin
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class FileUtils {
	
	private static final Logger log = Logger.getLogger(FileUtils.class);
	
	/**
	 * 创建不存在的目录
	 * @param dir
	 * @return
	 */
	public static boolean mkdirs(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}

	/**
	 * 创建父目录
	 */
	public static boolean mkParentDirs(String path) {
		File file = new File(path);
		File pfile = file.getParentFile();
		if (!pfile.exists()) {
			return pfile.mkdirs();
		}
		return false;
	}
	
	
	/**
	 * 给指定文件写入内容（覆盖原有内容）
	 * @param fileName 包含路径的文件名 如:E:\phsftp\src\123.txt
	 * @param content 文件内容
	 */
	public static void writeFile(String fileName, String content) {
		try {
			String fileNameTemp = fileName;
			File filePath = new File(fileNameTemp);
			if (!filePath.exists()) {
				filePath.createNewFile();
			}
			FileWriter fw = new FileWriter(filePath);
			PrintWriter pw = new PrintWriter(fw);
			String strContent = content;
			pw.println(strContent);
			pw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			log.error("新建文件操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
     * 删除文件
     * @param fileName 包含路径的文件名
     */
	public static void delFile(String fileName) {
		try {
			String filePath = fileName;
			java.io.File delFile = new java.io.File(filePath);
			delFile.delete();
		} catch (Exception e) {
			log.error("删除文件操作出错: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
    /**
     * 删除文件夹
     * @param folderPath  文件夹路径
     */
	public static void delFolder(String folderPath) {
		try {
			// 删除文件夹里面所有内容
			delAllFile(folderPath);
			String filePath = folderPath;
			java.io.File myFilePath = new java.io.File(filePath);
			// 删除空文件夹
			myFilePath.delete();
		} catch (Exception e) {
			log.error("删除文件夹操作出错" + e.getMessage());
			e.printStackTrace();
		}
	}
    
    /**
     * 删除文件夹里面的所有文件
     * @param path 文件夹路径
     */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] childFiles = file.list();
		File temp = null;
		for (int i = 0; i < childFiles.length; i++) {
			// File.separator与系统有关的默认名称分隔符
			// 在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
			if (path.endsWith(File.separator)) {
				temp = new File(path + childFiles[i]);
			} else {
				temp = new File(path + File.separator + childFiles[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 获取文件扩展名
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
	
	/**
     * 复制单个文件
     * @param srcFile 包含路径的源文件 如：E:/phsftp/src/abc.txt
     * @param dirDest 目标文件目录；若文件目录不存在则自动创建  如：E:/phsftp/dest
     * @throws IOException
     */
	public static void copyFile(String srcFile, String dirDest) {
		try {
			FileInputStream in = new FileInputStream(srcFile);
			mkdirs(dirDest);
			FileOutputStream out = new FileOutputStream(dirDest + "/" + new File(srcFile).getName());
			int len;
			byte buffer[] = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			log.error("复制文件操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
    /**
     * 复制单个文件
     * @param srcFile 包含路径的源文件 如：E:/phsftp/src/abc.txt
     * @param dirDest 目标文件目录；若文件目录不存在则自动创建  如：E:/phsftp/dest
     * @param newFileName 新文件名
     * @throws IOException
     */
	public static void copyFile(String srcFile, String dirDest, String newFileName) {
		try {
			FileInputStream in = new FileInputStream(srcFile);
			mkdirs(dirDest);
			FileOutputStream out = new FileOutputStream(dirDest + "/" + newFileName);
			int len;
			byte buffer[] = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			log.error("复制文件操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
     * 复制文件夹
     * @param oldPath 源文件夹路径 如：E:/phsftp/src
     * @param newPath 目标文件夹路径 如：E:/phsftp/dest
     */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			// 如果文件夹不存在 则新建文件夹
			mkdirs(newPath);
			File file = new File(oldPath);
			String[] files = file.list();
			File temp = null;
			for (int i = 0; i < files.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + files[i]);
				} else {
					temp = new File(oldPath + File.separator + files[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] buffer = new byte[1024 * 2];
					int len;
					while ((len = input.read(buffer)) != -1) {
						output.write(buffer, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
				}
			}
		} catch (Exception e) {
			log.error("复制文件夹操作出错:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 将文件从一个文件夹复制到另外一个文件夹
	 * @param oldpath
	 * @param newpath
	 */
	public static void copyFolderNio(String oldpathall, String newpath) throws IOException {
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
	 * 复制网络服务器文件到本地
	 * @createTime 2018年2月6日,下午6:27:04
	 * @createAuthor fangxilin
	 * @param url
	 * @param path
	 * @return
	 */
	public static boolean copyNet(URL url, String path) {
		boolean result = false;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			if (conn.getResponseCode() != 404) {
				is = conn.getInputStream();
				mkParentDirs(path);
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
     * 移动文件到指定目录
     * @param oldPath 包含路径的文件名 如：E:/phsftp/src/ljq.txt
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}
    
    /**
     * 移动文件夹到指定目录，不会删除文件夹
     * @param oldPath 源文件目录  如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
	public static void moveFiles(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delAllFile(oldPath);
	}
    
    /**
     * 移动文件夹到指定目录，会删除文件夹
     * @param oldPath 源文件目录  如：E:/phsftp/src
     * @param newPath 目标文件目录 如：E:/phsftp/dest
     */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}
    
	/**
     * 压缩文件
     * @param srcDir 压缩前存放的目录
     * @param destDir 压缩后存放的目录
     * @throws Exception
     */
	public static void yaSuoZip(String srcDir, String destDir) throws Exception {
		String tempFileName = null;
		byte[] buf = new byte[1024 * 2];
		int len;
		// 获取要压缩的文件
		File[] files = new File(srcDir).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					if (destDir.endsWith(File.separator)) {
						tempFileName = destDir + file.getName() + ".zip";
					} else {
						tempFileName = destDir + "/" + file.getName() + ".zip";
					}
					FileOutputStream fos = new FileOutputStream(tempFileName);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

					ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
					zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

					while ((len = bis.read(buf)) != -1) {
						zos.write(buf, 0, len);
						zos.flush();
					}
					bis.close();
					zos.close();

				}
			}
		}
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
	 * 将多个文件合并成一个文件
	 * @param outFile 合并后生成文件的路径
	 * @param files 你需要合并的文本文件列表
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
	
	/**
	 * 获取MP3文件时长
	 * @param file mp3文件路径
	 * @param durationFormat 时长格式 1：分:秒  2：时:分:秒
	 * @return
	 */
	public static String getDurationLength(File file, int durationFormat) {
		try {
			MP3File f = (MP3File)AudioFileIO.read(file);
			
			MP3AudioHeader audioHeader = (MP3AudioHeader)f.getAudioHeader();
			//mp3时长
			int seconds = audioHeader.getTrackLength();
			int temp=0;
			StringBuffer sb=new StringBuffer();
			if(durationFormat == 2) {
				//时
				temp = seconds/3600;
				sb.append((temp<10)?"0"+temp+":":""+temp+":");
			}
			//分
			temp=seconds%3600/60;
			sb.append((temp<10)?"0"+temp+":":""+temp+":");
			//秒
			temp=seconds%3600%60;
			sb.append((temp<10)?"0"+temp:""+temp);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
