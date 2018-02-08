package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ClientConnect {

	public static void main(String[] args) {
		client();

	}

	public static void client() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		SocketChannel socketChannel = null;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			socketChannel.connect(new InetSocketAddress("localhost", 8080));

			if (socketChannel.finishConnect()) {
				int i = 0;
				while (true) {
					TimeUnit.SECONDS.sleep(1);
					String info = "I'm " + i++ + "-th information from client";
					buffer.clear();
					//向Buffer中写数据
					buffer.put(info.getBytes());
					buffer.flip();
					//Write()方法无法保证能写多少字节到SocketChannel。所以，我们重复调用write()直到Buffer没有要写的字节为止
					while (buffer.hasRemaining()) {
						System.out.println(buffer);
						//从Buffer读取到Channel
						socketChannel.write(buffer);
					}
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
