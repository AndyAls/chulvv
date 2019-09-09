package com.qlckh.chunlvv.common;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dream on 2018/4/3.
 */

public class TcpHelper {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private DataInputStream inputStream;
    private boolean NeedStop;
    private InetSocketAddress SerAddr;
    public String ErrMsg;
    private ReadThread readThread;           //读取数据线程
    private OnReceiveEvent receiveEvent;     //此事件用于当接收到数据时向主线程通知接收到的数据
    private long LastCheckTime;
    public static interface OnReceiveEvent{
        public  void ReceiveBytes(byte[] iData);
        public  void ReceiveString(String iData);
    }
    public enum EDataType{EDT_BYTE,EDT_STRING};
    private EDataType dataType;
    public TcpHelper(String HostIp, int HostPort) {
        try {
            LastCheckTime= System.currentTimeMillis();
            readThread =new ReadThread();
            NeedStop=false;
            SerAddr =  new InetSocketAddress(HostIp,HostPort);
            NeedStop=false;
            socket = new Socket();
            readThread.start();
        }catch (Exception e){
            ErrMsg=e.getMessage();
        }
    }
    public void SendString(String iText){       //发送字符串
        dataType= EDataType.EDT_STRING;
        toSend(iText);
    }
    public void SendBytes(byte[] iBuf){     //发送字节流指令
        dataType= EDataType.EDT_BYTE;
        String iText = new String(iBuf);
        toSend(iText);
    }
    //发送数据线程
    private void toSend(final String iText){
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            bufferedWriter.write(iText);
//                            bufferedWriter.flush();
//                        } catch (Exception e) {
//                        }
//                    }
//                }
//        ).start();
        ThreadPool.getInstance().execute("hello", new Runnable() {
            @Override
            public void run() {
                try {
                    bufferedWriter.write(iText);
                    bufferedWriter.flush();
                } catch (Exception e) {
                }
            }
        });
    }
    public boolean isConnected(){
        return socket.isConnected();
    }
    //设置通知事件
    public void setReceiveEvent(OnReceiveEvent iEvent){
        receiveEvent=iEvent;
    }
    //读取线程
    private class ReadThread extends Thread {
        @Override
        public void run() {
            while (!NeedStop){
                try {
                    if (!socket.isConnected() || socket.isClosed()){
                        socket.connect(SerAddr,5000);
                        if(bufferedWriter==null) {
                            bufferedWriter =new BufferedWriter(new OutputStreamWriter( socket.getOutputStream(),"utf-8"));
                        }
                        if(inputStream==null) {
                            inputStream = new DataInputStream(socket.getInputStream());
                        }
                    }
                    else {
                        if(inputStream.available()>0){
                            byte[] recData = new byte[inputStream.available()];
                            inputStream.read(recData);
                            if(receiveEvent!=null)
                            {
                                if(dataType== EDataType.EDT_BYTE) {
                                    //receiveEvent.ReceiveBytes(recData);
                                }
                                else {
                                    String s = new String(recData,"utf-8");
                                    receiveEvent.ReceiveString(s);
                                    receiveEvent.ReceiveBytes(recData);
                                }
                            }
//                            inputStream.close();
                        }
                        if (System.currentTimeMillis()-LastCheckTime>5000)
                        {
                            try {
                                LastCheckTime= System.currentTimeMillis();
                                socket.sendUrgentData(0xFF);
                            }catch (Exception e){
                                socket.close();
                                socket = new Socket();
                                bufferedWriter=null;
                                inputStream =null;
                            }
                        }
                    }

                }catch (Exception e){

                }
            }
        }
    }
    /**
     * 结束通讯
     */
    public void setStop(){
        try {
            if (socket != null ){
                socket.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }
            if (inputStream!=null){
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查ip是否合法
     *///TODO
    public static boolean isIP(String addr){
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr)){
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        //============对之前的ip判断的bug在进行判断
        if (ipAddress==true){
            String ips[] = addr.split("\\.");
            if(ips.length==4){
                try{
                    for(String ip : ips){
                        if(Integer.parseInt(ip)<0||Integer.parseInt(ip)>255){
                            return false;
                        }
                    }
                }catch (Exception e){
                    return false;
                }
                return true;
            }else{
                return false;
            }
        }
        return ipAddress;
    }
    /**
     * 验证端口号
     */
    public static boolean checkPort(int port)	{
        return port >= 0 && port <= 65536;
    }

}
