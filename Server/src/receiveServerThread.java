import java.io.*;
import java.util.Date;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 20:39
 */
public class receiveServerThread extends Thread{
    MySocket socket;
    byte msg[];
    int lenofmsg;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    MsgBuffer buffer;
    String name;
    public receiveServerThread(MySocket _socket,MsgBuffer _msgBuffer,String _name){
        socket = _socket;
        buffer = _msgBuffer;
        name = _name;
    }
    public void run(){
        while(true){
            try {
                socket.mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            receiveThread receiveThread = new receiveThread(socket,buffer,name);
            receiveThread.start();
        }

    }
}
