import java.net.Socket;
import java.util.concurrent.Semaphore;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 11:40
 */
public class MySocket {
    Socket socket;
    Semaphore mutex;
    boolean effective;
    MySocket(Socket _socket){
        socket = _socket;
        mutex = new Semaphore(1);
        effective = true;
    }
}
