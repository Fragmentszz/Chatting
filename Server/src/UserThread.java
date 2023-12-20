import java.io.*;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 15:51
 */
public class UserThread extends Thread{
    MySocket receive;
    MsgBuffer sendBuffer;
    MsgBuffer receiveBuffer;
    MySocket send;
    public UserThread(MySocket _receive,MySocket _send,MsgBuffer _sendBuffer,MsgBuffer _receiveBuffer){
        receive = _receive;
        send = _send;
        sendBuffer = _sendBuffer;
        receiveBuffer = _receiveBuffer;
    }
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = receive.socket.getInputStream();
            outputStream = receive.socket.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("获取客户端输入/输出流失败！");
        }
        DataInputStream in = new DataInputStream(inputStream);
        DataOutputStream out = new DataOutputStream(outputStream);
        byte []bytes = new byte[5000];
        String name = null;
        int lenofname = 0;
        try {
            lenofname = in.read(bytes);
            while(lenofname == -1){
                lenofname = in.read(bytes);
            }
            name = new String(bytes,0,lenofname,"unicode");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        receiveServerThread receiveThread = new receiveServerThread(receive,sendBuffer,name);            //用户要send
        receiveBuffer.name = name;
        SendToClient sendToClient = new SendToClient(send,receiveBuffer);

        receiveThread.start();
        sendToClient.start();
    }
}
