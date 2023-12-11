import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 20:39
 */
public class receiveThread extends Thread{
    Socket socket;
    byte msg[];
    int lenofmsg;
    InputStream inputStream = null;
    OutputStream outputStream = null;

    public receiveThread(Socket _socket){
        socket = _socket;

    }
    public void run(){
        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("获取客户端输入/输出流失败！");
        }
        DataInputStream in = new DataInputStream(inputStream);
        DataOutputStream out = new DataOutputStream(outputStream);
        Date now = new Date();
        msg = new byte[8192];
        try{
            //bw.write(now.toString());
            //bw.flush();
            lenofmsg = in.read(msg);
            String str = new String(msg,0,lenofmsg,"unicode");
            System.out.println(str);
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
