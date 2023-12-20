import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 11:01
 */

public class SendThread extends Thread{
    MySocket socket;
    Message msg;
    public SendThread(Message _msg,MySocket _socket){
        msg = _msg;
        socket = _socket;
    }
    @Override
    public void run() {
        try {
            socket.mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //构建IO
        InputStream inputStream = null;
        try {
            inputStream = socket.socket.getInputStream();
            OutputStream outputStream = socket.socket.getOutputStream();
            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);
            //向服务器端发送一条消息
            byte mail[] = msg.toString().getBytes("unicode");
            out.write(mail,0,mail.length);
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        socket.mutex.release();
    }
}
