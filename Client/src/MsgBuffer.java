import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 10:07
 */
public class MsgBuffer{
    Semaphore full;
    Semaphore empty;
    Semaphore mutex;
    int maxlen;
    int len;
    Message []msgs;
    public MsgBuffer(int _len){
        maxlen = _len;
        len = 0;
        full = new Semaphore(0);
        empty = new Semaphore(maxlen);
        mutex = new Semaphore(1);
        msgs = new Message[maxlen];
    }
    public void insertMsg(Message msg){
        msgs[len++] = msg;
    }
    public Message getMsg(){
        return msgs[--len];
    }
}
