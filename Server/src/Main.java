import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 20:35
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static String formatIPAddress(byte[] ipAddress) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < ipAddress.length; i++) {
        result.append((ipAddress[i] & 0xFF)); // 使用位运算保证结果是无符号的
        if (i < ipAddress.length - 1) {
            result.append(".");
        }
    }
    return result.toString();
}

    public static void main(String[] args) throws IOException {
        MsgBuffer msgBuffer_send = null;

        ServerSocket receiveSocket = null;
        ServerSocket sendSocket = null;
        MySocket receive[] = new MySocket[10];
        MySocket send[] = new MySocket[10];
        String names[] = new String[10];
        MsgBuffer[] msgBuffer_receive = new MsgBuffer[10];
        int len = 0;
        try{
            msgBuffer_send = new MsgBuffer(1000,null);
            receiveSocket = new ServerSocket(1234);
            sendSocket = new ServerSocket(1235);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("创建服务器端失败！");
        }
        for (int i = 0; i < msgBuffer_receive.length; i++) {
            msgBuffer_receive[i] = new MsgBuffer(100,null);
        }
        Message init = Message.Parse("[a][Fragments][b][c]");


        moveMsg movemsg = new moveMsg(msgBuffer_send,msgBuffer_receive,receive,send);
        movemsg.start();

        while(true){
            Socket socket_send= null;
            Socket socket_receive = null;
            System.out.println("服务器端空闲...");
            try{
                socket_send = sendSocket.accept();
                socket_receive = receiveSocket.accept();
                System.out.println(formatIPAddress(socket_send.getInetAddress().getAddress()));
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("握手失败！");
            }
            if(socket_send != null && socket_send.isConnected()){
                int nowlen = -1;
                for (int i = 0; i < msgBuffer_receive.length; i++) {
                    if(msgBuffer_receive[i].name == null){
                        msgBuffer_receive[i].name = "occupied";
                        nowlen = i;
                        break;
                    }
                }
                len = nowlen;
                receive[len] = new MySocket(socket_receive);
                send[len] = new MySocket(socket_send);
                UserThread userThread = new UserThread(receive[len],send[len],msgBuffer_send,msgBuffer_receive[len]);
                userThread.start();
            }
        }
    }
}