package com.fxl.frame.util.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * java NIO工具类
 * @author fangxilin
 * @date 2018年2月7日
 */
public class NIOUtils {

	public static void main(String[] args) {
		String filePath = "F:\\temp\\搜狗标准词库.txt";
		long s = System.currentTimeMillis();
		readFile1(filePath);
		//System.out.println(readFile1(filePath));
		long e = System.currentTimeMillis();
		System.out.println("readFile耗时：" + (e - s));
		
		/*long s1 = System.currentTimeMillis();
		readFile1(filePath);
		long e1 = System.currentTimeMillis();
		System.out.println("readFile1耗时：" + (e1 - s1));*/
		
		
		/*String oldFilePath = "F:\\temp\\搜狗标准词库.txt";
		String newFilePath = "F:\\temp\\搜狗标准词库1.txt";
		long s = System.currentTimeMillis();
		copyFile(oldFilePath, newFilePath);
		long e = System.currentTimeMillis();
		System.out.println("copyFile耗时：" + (e - s));*/

	}
	
	/**
	 * 使用ByteBuffer读取文件
	 */
	public static String readFile(String filePath) {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		String result = "";
		try {
			//rw，支持读写，不存在文件会自动创建文件
			aFile = new RandomAccessFile(filePath, "rw");
			fc = aFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate((int) aFile.length());
			fc.read(buf);
			buf.flip();//read完后需要重置
			byte[] dst = new byte[(int) aFile.length()];
			buf.get(dst);
			result = new String(dst, "UTF-8");
			buf.clear();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fc != null) {
					fc.close();
				}
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	
	/**
	 * 使用MappedByteBuffer读取文件
	 * MappedByteBuffer：
	 * 	是NIO引入的文件内存映射方案，读写性能极高，
	 * 	MappedByteBuffer 将文件直接映射到虚拟内存
	 * 
	 */
	public static String readFile1(String filePath) {
		RandomAccessFile aFile = null;
		FileChannel fc = null;
		String result = "";
		try {
			//rw，支持读写，不存在文件会自动创建文件
			aFile = new RandomAccessFile(filePath, "rw");
			fc = aFile.getChannel();
			//使用MappedByteBuffer
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, aFile.length());
			fc.read(mbb);
			mbb.flip();//read完后需要重置
			byte[] dst = new byte[(int) aFile.length()];
			mbb.get(dst);
			result = new String(dst, "UTF-8");
			mbb.clear();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fc != null) {
					fc.close();
				}
				if (aFile != null) {
					aFile.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	/**
	 * 复制文件
	 */
	public static void copyFile(String oldFilePath, String newFilePath) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		
		try {
			fis = new FileInputStream(oldFilePath);
			fos = new FileOutputStream(newFilePath);
			//获得传输通道channel
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			//toChannel.transferFrom(fromChannel, position, count);
			//获得容器buffer，分配1024 Byte（字节）（1k）capacity的ByteBuffer
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int eof = 0;
			while ((eof = inChannel.read(buffer)) != -1) {
				//将 Buffer 模式切换，重设一下buffer的position=0，limit=position
				buffer.flip();
				//开始写
				outChannel.write(buffer);
				//写完要重置buffer，重设position=0,limit=capacity
                buffer.clear();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outChannel.close();
				inChannel.close();
				fos.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
