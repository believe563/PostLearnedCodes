package com.example.minaclientdemo;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MinaClient {
    public static void main(String[] args) {
        NioSocketConnector connector = new NioSocketConnector();
        connector.setHandler(new MyClientHandler());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyTextLineCodecFactory()));
        ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 10001));
        future.awaitUninterruptibly();//mina框架是非阻塞的，建立connect之后代码不会阻塞，会一直往下运行，
        //调用await...就会一直等待客户端把连接建立好了 才会一直往下运行
        IoSession session = future.getSession();//拿到session对象，有了这个对象就可以进行非常多的操作,比如发送数据
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String inputContent=null;
        try {
            while (!(inputContent = inputReader.readLine()).equals("bye")) {
                session.write(inputContent+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
