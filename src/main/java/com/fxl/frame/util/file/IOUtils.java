package com.fxl.frame.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * io操作
 * @author fangxilin
 * @date 2018年2月7日
 */
public class IOUtils {
	
	public static void main(String[] args) {
		String filePath = "F:\\temp\\搜狗标准词库.txt";
		//writeFile(filePath, "ccccccccc出差擦擦擦擦擦擦擦擦擦擦擦擦");
		/*long s = System.currentTimeMillis();
		readFile(filePath);
		long e = System.currentTimeMillis();
		System.out.println("readFile耗时：" + (e - s));*/
		
		/*long s1 = System.currentTimeMillis();
		readFile1(filePath);
		long e1 = System.currentTimeMillis();
		System.out.println("readFile1耗时：" + (e1 - s1));*/
		
		String oldFilePath = "F:\\temp\\搜狗标准词库.txt";
		String newFilePath = "F:\\temp\\搜狗标准词库1.txt";
		/*long s = System.currentTimeMillis();
		copyFile(oldFilePath, newFilePath);
		long e = System.currentTimeMillis();
		System.out.println("copyFile耗时：" + (e - s));*/
		
		long s1 = System.currentTimeMillis();
		copyFile1(oldFilePath, newFilePath);
		long e1 = System.currentTimeMillis();
		System.out.println("copyFile1耗时：" + (e1 - s1));
	}
	
	/**
	 * 读取指定文件的内容
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		InputStream fis = null;
		String result = "";
		
		try {
			//1.根据path路径实例化一个输入流的对象
			fis = new FileInputStream(filePath);
			//2.返回这个输入流中可以被读的剩下的bytes字节的估计值
			int size = fis.available();
			//3.根据输入流中的字节数创建指定大小的byte数组
			byte[] array = new byte[size];
			//4.把数据读取到数组中
			fis.read(array);
			//5.根据获取的byte数组创建一个字符串，然后输出
			result = new String(array, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 缓冲流读取指定文件的内容
	 * @param filePath
	 * @return
	 */
	public static String readFile1(String filePath) {
		InputStream fis = null;
		BufferedInputStream bis = null;
		String result = "";
		
		try {
			//根据path路径实例化一个输入流的对象
			fis = new FileInputStream(filePath);
			//返回这个输入流中可以被读的剩下的bytes字节的估计值
			int size = fis.available();
			//根据输入流中的字节数创建指定大小的byte数组
			byte[] array = new byte[size];
			bis = new BufferedInputStream(fis);
			//把数据读取到数组中
			//bis.read(array);
			
			byte[] b = new byte[1024];
			int len = 0;
			StringBuilder sb = new StringBuilder();
			while ((len = bis.read(b)) != -1) {
				String a = new String(b);//new对象时间花销大
				sb.append(a);//append时间花销大
			}
			//根据获取的byte数组创建一个字符串，然后输出
			result = sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 给指定文件写入内容（覆盖原有内容）
	 * @param filePath
	 * @param content
	 */
	public static void writeFile(String filePath, String content) {
		OutputStream fos = null;
		try {
			//1.根据文件路径创建输出流
			//fos = new FileOutputStream(filePath, true);//追加到文本末尾
			fos = new FileOutputStream(filePath);
			//2.把String转换为byte数组
			byte[] array = content.getBytes();
			//3.把byte数组输出
			fos.write(array);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 使用缓冲流给指定文件写入内容（覆盖原有内容）
	 * @param filePath
	 * @param content
	 */
	public static void writeFile1(String filePath, String content) {
		OutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			//根据文件路径创建输出流
			fos = new FileOutputStream(filePath);
			bos = new BufferedOutputStream(fos);
			//把String转换为byte数组
			byte[] array = content.getBytes();
			//将array写入到内置缓存数组
			bos.write(array);
			//写入文件
			bos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 使用字节流复制文件
	 * @param oldFilePath 需要复制的文件路径
	 * @param newFilePath 复制文件存放的路径
	 */
	public static void copyFile(String oldFilePath, String newFilePath) {
		InputStream fis = null;
		OutputStream fos = null;
		
		try {
			//1.根据path路径实例化一个输入流的对象
			fis = new FileInputStream(oldFilePath);
			//2.返回这个输入流中可以被读的剩下的bytes字节的估计值
			int size = fis.available();
			//3.根据输入流中的字节数创建指定大小的byte数组
			byte[] array = new byte[size];
			//4.把数据读取到数组中
			fis.read(array);
			
			//5.根据文件路径创建输出流
			fos = new FileOutputStream(newFilePath);
			//6.把byte数组输出
			fos.write(array);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 使用缓冲流复制文件（效率最高）
	 * @param oldFilePath 需要复制的文件路径
	 * @param newFilePath 复制文件存放的路径
	 */
	public static void copyFile1(String oldFilePath, String newFilePath) {
		InputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			//根据path路径实例化一个输入流的对象
			fis = new FileInputStream(oldFilePath);
			//使用默认内置缓存字节数组大小（8KB）底层字节输入流构建bis
			bis = new BufferedInputStream(fis);
			
			//根据文件路径创建输出流
			fos = new FileOutputStream(newFilePath);
			//使用默认内置缓存字节数组大小（8KB）底层字节输入流构建bis
			bos = new BufferedOutputStream(fos);
			
			//代表一次最多读取1kb的内容
			byte[] b = new byte[1024];
			//代表实际读取的字节数
			int length = 0;
			while ((length = bis.read(b)) != -1) {//将bis内置缓存字节数组内容循环赋给b数组
				bos.write(b, 0, length);//此时，写入的是bos内置缓存字节数组
			}
			/*byte[] b = new byte[bis.available()];
			bis.read(b);//一次性读取效率更低
			bos.write(b);*/
			
			//缓冲区的内容一次性写入到文件
			bos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					//可以只调用外层流的close方法关闭其装饰的内层流
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					//可以只调用外层流的close方法关闭其装饰的内层流
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 使用缓冲流中字符流复制文件
	 * @param oldFilePath 需要复制的文件路径
	 * @param newFilePath 复制文件存放的路径
	 */
	public static void copyFile2(String oldFilePath, String newFilePath) {
		Reader reader = null;
		BufferedReader bReader = null;
		Writer writer = null;
		BufferedWriter bWriter = null;
		
		try {
			reader = new FileReader(oldFilePath);
			bReader = new BufferedReader(reader);
			
			writer = new FileWriter(newFilePath);
			bWriter = new BufferedWriter(writer);
			
			String result = null;//每次读取一行内容
			while ((result = bReader.readLine()) != null) {//逐行读取，效率低
				bWriter.write(result);//把内容写入文件
				bWriter.newLine();
			}
			bWriter.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bWriter != null) {
				try {
					//可以只调用外层流的close方法关闭其装饰的内层流
					bWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bReader != null) {
				try {
					//可以只调用外层流的close方法关闭其装饰的内层流
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 使用转换流复制文件
	 * @param oldFilePath 需要复制的文件路径
	 * @param newFilePath 复制文件存放的路径
	 */
	public static void copyFile3(String oldFilePath, String newFilePath) {
		InputStream fis = null;
		InputStreamReader isr = null;
		OutputStream fos = null;
		OutputStreamWriter osw = null;
		
		try {
			//根据path路径实例化一个输入流的对象
			fis = new FileInputStream(oldFilePath);
			isr = new InputStreamReader(fis);
			
			//根据文件路径创建输出流
			fos = new FileOutputStream(newFilePath);
			osw = new OutputStreamWriter(fos);
			
			char[] cbuf = new char[1024 * 1024];
			int len = 0;
			while ((len = isr.read(cbuf)) != -1) {
				osw.write(cbuf, 0, len);
			}
			
			//缓冲区的内容一次性写入到文件
			osw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					//可以只调用外层流的close方法关闭其装饰的内层流
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (isr != null) {
				try {
					//可以只调用外层流的close方法关闭其装饰的内层流
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
