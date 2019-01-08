package com.example.administrator.socketcombat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/5/1 0001.
 */
public class SocketClient {

    private BufferedWriter bufferedWriter;

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.start2();

    }

    /*实现允许用户从命令行输入消息的功能*/
    /*本方法只实现了从屏幕读取数据*/
    public void start() {
        BufferedReader inputReader;
        inputReader = new BufferedReader(new InputStreamReader(System.in));//得到从控制台输入的inputReader
        String inputContent;
        try {
            while (!((inputContent = inputReader.readLine()).equals("bye"))) {
                System.out.println(inputContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start2() {
        BufferedWriter bw = null;
        BufferedReader br = null;
        BufferedReader br1=null;
        Socket socket = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            socket = new Socket("127.0.0.1", 10001);
            br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            startServerReplyListener(br1);
            String str = null;
            int count=0;
            while (!((str = br.readLine()).equals("bye"))) {
                if (count % 2 == 0) {
                    bw.write(str + "\n");//要在后面加\n符,因为服务器端以readline接受数据时是用\n来换行的，否则只有等全部输入完毕服务器才相当于收到了一行
                } else {
                    bw.write(str);
                }
                bw.flush();
                count++;
//                String response=br1.readLine();
//                System.out.println(response);
                //到这个时候还不能让服务器在任何时候发送消息给客户端，必须由客户端先发送消息给服务器端，
                //服务器接收到数据之后才能给客户端回应,所以这里添加了startServerReplylistener方法
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                br.close();
                br1.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*开启接收服务器消息的监听器*/
    /*它必须一直运行着，随时接收服务器的消息
    * */
    public void startServerReplyListener(final BufferedReader br) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                        String response;
                        try {
                            while ((response=br.readLine())!=null)
                                System.out.println(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
            }
        }).start();
    }
//很少用原生socket用法，因为它的网络操作都是阻塞式的，
// 我们需要开启线程专门去维护网络方面的操作，让它不影响主线程的运行
    //java1.4以上有NIO网络操作框架new IO，非阻塞式高伸缩性
//但它的用法比较复杂
    //Mina 和nety，是用NIO封装的好用的网络操作框架
}
