import java.io.*;
import java.net.Socket;
import java.util.Date;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 20:39
 */
public class receiveThread extends Thread{
    MySocket socket;
    byte msg[];
    int lenofmsg;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    MsgBuffer buffer;
    String name;
    public receiveThread(MySocket _socket,MsgBuffer _buffer,String _name){
        socket = _socket;
        buffer = _buffer;
        name = _name;
    }
    public void run(){
        while(socket.socket.isConnected()){
            try{
                inputStream = socket.socket.getInputStream();
                outputStream = socket.socket.getOutputStream();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("获取客户端输入/输出流失败！");
            }
            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);
            Date now = new Date();
            msg = new byte[8192];
            String str = null;
            try{
                lenofmsg = in.read(msg);
                while(lenofmsg == -1 && socket.socket.isConnected())
                    lenofmsg = in.read(msg);
                if(!socket.socket.isConnected()) {
                    System.out.println(buffer.name+ "offline...");
                    buffer.name = null;
                    socket.effective = false;
                    socket.mutex.release();
                    break;
                }else{
                    str = new String(msg,0,lenofmsg,"unicode");
                    System.out.println(str);
                }
                socket.mutex.release();
            }catch (Exception e){
                buffer.name = null;
                socket.effective = false;
                System.out.println(buffer.name+ "offline...");
                break;
            }
            try {
                buffer.empty.acquire();
                buffer.mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            str += "[" + name + "]";
            Message msg = Message.Parse(str);
            System.out.println("receive from client" + str);
            buffer.insertMsg(msg);
            buffer.full.release();
            buffer.mutex.release();
        }

    }
}
