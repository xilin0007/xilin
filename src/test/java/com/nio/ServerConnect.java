package com.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerConnect {
	private static final int BUF_SIZE = 1024;
	private static final int PORT = 8080;
	private static final int TIMEOUT = 3000;

	public static void main(String[] args) {
		selector();
		//server();
	}

	public static void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
		//在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null
		SocketChannel sc = ssChannel.accept();
		if (sc != null) {
			sc.configureBlocking(false);
			sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
			System.out.println("Got connection from " + sc);
		}
	}

	public static void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		//从Channel写到Buffer
		long bytesRead = sc.read(buf);
		while (bytesRead > 0) {
			buf.flip();
			while (buf.hasRemaining()) {
				//get()方法从Buffer中读取数据
				System.out.print((char) buf.get());
			}
			System.out.println();
			buf.clear();
			bytesRead = sc.read(buf);
		}
		if (bytesRead == -1) {
			sc.close();
		}
	}

	public static void handleWrite(SelectionKey key) throws IOException {
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip();
		SocketChannel sc = (SocketChannel) key.channel();
		while (buf.hasRemaining()) {
			sc.write(buf);
		}
		buf.compact();
	}

	public static void selector() {
		Selector selector = null;
		ServerSocketChannel ssc = null;
		try {
			//创建一个selector，select是NIO中的核心对象，它用来监听各种感兴趣的IO事件
			selector = Selector.open();
			ssc = ServerSocketChannel.open();
			//监听一个端口（8080）
			ssc.socket().bind(new InetSocketAddress(PORT));
			//与Selector一起使用时，Channel必须处于异步非阻塞模式下
			ssc.configureBlocking(false);
			/**
			 * 注册（register）到想要监控的信道上，
			 * 第一个参数永远都是selector，
			 * 第二个参数是我们要监听的事件，OP_ACCEPT是新建立连接的事件，也是适用于ServerSocketChannel的唯一事件类型
			 */
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				//select()方法会阻塞等待，直到有一个或更多的信道准备好了I/O操作或等待超时,返回可进行I/O操作的信道数量
				if (selector.select(TIMEOUT) == 0) {//0个可进行I/O操作的信道数量
					System.out.println("68行==");
					continue;
				}
				//通过迭代 SelectionKeys 并依次处理每个 SelectionKey 来处理事件
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					//if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					if (key.isAcceptable()) {//ServerSocketChannel准备好接收新连接
						handleAccept(key);
					}
					//if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					if (key.isReadable()) {//有数据可读的通道，已经准备就绪的操作
						handleRead(key);
					}
					if (key.isWritable() && key.isValid()) {//等待写数据的通道
						handleWrite(key);
					}
					if (key.isConnectable()) {//Channel成功连接到另一个服务器
						System.out.println("isConnectable = true");
					}
					/**
					 * Selector对象并不会从自己的selected key集合中自动移除SelectionKey实例。
					 * 我们需要在处理完一个Channel的时候自己去移除。
					 * 当下一次Channel就绪的时候，Selector会再次把它添加到selected key集合中
					 */
					iter.remove();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (selector != null) {
					selector.close();
				}
				if (ssc != null) {
					ssc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * io实现服务端
	 */
	public static void server() {
		ServerSocket serverSocket = null;
		InputStream in = null;
		try {
			serverSocket = new ServerSocket(8080);
			int recvMsgSize = 0;
			byte[] recvBuf = new byte[1024];
			while (true) {
				Socket clntSocket = serverSocket.accept();
				SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				in = clntSocket.getInputStream();
				while ((recvMsgSize = in.read(recvBuf)) != -1) {
					byte[] temp = new byte[recvMsgSize];
					System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
					System.out.println(new String(temp));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
