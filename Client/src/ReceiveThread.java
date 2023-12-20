import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 10:04
 */


public class ReceiveThread extends Thread{
    MySocket socket;
    MsgBuffer msgBuffer;
    public ReceiveThread(MySocket _socket,MsgBuffer _msgBuffer){
        socket = _socket;
        msgBuffer = _msgBuffer;
    }
    @Override
    public void run() {
        while(true){
            try {
                InputStream inputStream = socket.socket.getInputStream();
                OutputStream outputStream = socket.socket.getOutputStream();
                DataInputStream in = new DataInputStream(inputStream);
                DataOutputStream out = new DataOutputStream(outputStream);
                byte msg[] = new byte[8192];
                int lenofmsg = -1;
                while(lenofmsg == -1){
                    lenofmsg = in.read(msg);
                }
                String str = new String(msg,0,lenofmsg,"unicode");
                socket.mutex.release();
                try {

                    msgBuffer.empty.acquire();
                    msgBuffer.mutex.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                msgBuffer.insertMsg(Message.Parse(str));
                System.out.println("客户端接收到消息:" + str);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(msgBuffer.full.availablePermits());
            msgBuffer.full.release();
            System.out.println(msgBuffer.full.availablePermits());
            msgBuffer.mutex.release();
        }

    }
}
