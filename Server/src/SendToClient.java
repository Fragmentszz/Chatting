import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 14:04
 */
public class SendToClient extends Thread{
    MySocket socket;
    MsgBuffer msgBuffer;
    public SendToClient(MySocket _s,MsgBuffer _msg){
        socket = _s;
        msgBuffer = _msg;
    }

    @Override
    public void run() {
        while(socket.socket.isConnected()){
            try {
                msgBuffer.full.acquire();
                msgBuffer.mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message nowmsg = msgBuffer.getMsg();
            msgBuffer.empty.release();
            msgBuffer.mutex.release();


            String str = nowmsg.toString();
            System.out.println("send to client:" + str);
            try {
                socket.mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Socket s = socket.socket;
            if(s.isConnected()){
                InputStream inputStream = null;
                try {
                    inputStream = s.getInputStream();
                    OutputStream outputStream = s.getOutputStream();
                    DataInputStream in = new DataInputStream(inputStream);
                    DataOutputStream out = new DataOutputStream(outputStream);
                    //向服务器端发送一条消息
                    byte mail[] = str.getBytes("unicode");
                    out.write(mail,0,mail.length);
                } catch (IOException e) {
                    socket.effective = false;
                    try{
                        msgBuffer.empty.acquire();
                        msgBuffer.mutex.acquire();
                    } catch (InterruptedException ee) {
                        throw new RuntimeException(ee);
                    }
                    msgBuffer.insertMsg(nowmsg);
                    msgBuffer.full.release();
                    msgBuffer.mutex.release();
                }
            }else{
                msgBuffer.name = null;          //

            }


            socket.mutex.release();
        }
    }
}
