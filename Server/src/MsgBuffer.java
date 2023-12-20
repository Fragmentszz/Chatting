import java.util.concurrent.Semaphore;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 10:07
 */
public class MsgBuffer {
    Semaphore full;
    Semaphore empty;
    Semaphore mutex;
    int maxlen;
    String name;
    int len;
    Message []msgs;
    public MsgBuffer(int _len,String _name){
        maxlen = _len;
        name = _name;
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
