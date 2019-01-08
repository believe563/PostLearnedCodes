package com.feng.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SocketServer {

	BufferedWriter bw = null;
	BufferedReader br = null;

	public static void main(String[] args) {
		SocketServer socketServer = new SocketServer();
		socketServer.startServer();
		// main方法是静态的，调用静态方法比较麻烦，写成这样比较方便

	}

	public void startServer() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10001);
			System.out.println("server started");
			while (true) {
				Socket socket = serverSocket.accept();// 进入阻塞状态，等待客户端有一个用户接入
				managerConnection(socket);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void managerConnection(final Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("client" + socket.hashCode() + " connected");
				// 读取收到来自客户端的消息
				// 从socket读
				try {
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					// new Timer().schedule(new TimerTask() {
					//
					// @Override
					// public void run() {
					// try {
					// System.out.println("heart beat once...");
					// bw.write("heart beat once..\n");//bw是全局变量
					// bw.flush();
					// } catch (IOException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					// }
					// }, 3000,3000);
					String receiveMsg;
					while ((receiveMsg = br.readLine()) != null) {
						System.out.println(receiveMsg);
						bw.write("server"+socket.hashCode()+" reply:" + receiveMsg + "\n");
						bw.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
