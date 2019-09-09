package com.qlckh.chunlvv.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Andy
 * @date 2018/8/18 18:27
 * Desc:
 */
public class TestSocket {


    public static void get() {

        ThreadPool.getInstance().execute("00", new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("10.10.10.1", 8080);
//        // 2、获取输出流，向服务器端发送信息
//        OutputStream os = socket.getOutputStream();// 获取字节输出流
//        // 将输出流包装为打印流
//        PrintWriter pw = new PrintWriter(os);
//        pw.write("用户名:admin 密码:123");
//        pw.flush();
//        socket.shutdownOutput();//关闭输出流

                    // 3、获取输入流，并读取服务器端的响应信息
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String info = null;
                    while ((info = br.readLine()) != null) {
                        System.out.println("我是客户端，服务器跟我说：" + info);
                    }

                    // 4、关闭资源
                    br.close();
                    is.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        pw.close();
//        os.close();


    }
}
