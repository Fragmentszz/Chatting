import com.sun.security.ntlm.Server;

import java.net.ServerSocket;
import java.net.Socket;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 20:35
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(7777);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("������������ʧ�ܣ�");
        }
        while(true){
            Socket socket = null;
            System.out.println("�������˿���...");
            try{
                socket = serverSocket.accept();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("����ʧ�ܣ�");
            }
            if(socket != null && socket.isConnected()){
                receiveThread receiveThread = new receiveThread(socket);
                receiveThread.start();
            }
        }
    }
}