import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 9:59
 */
public class ReceiveSockect extends Thread{
    MySocket socket;
    MsgBuffer msgBuffer;
    public ReceiveSockect(MsgBuffer _msgbuffer,MySocket _socket) throws IOException {
        socket = _socket;
        System.out.println("启动服务器....");
        msgBuffer = _msgbuffer;
    }
    @Override
    public void run() {
        while(true){
            try {
                socket.mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ReceiveThread receiveThread = new ReceiveThread(socket,msgBuffer);
            receiveThread.start();
        }

    }
}
