import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String name  = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的昵称:");
        name = scanner.nextLine();
        String ip;
        System.out.println("服务器端ip");
        ip = scanner.nextLine();
        Socket sendSocket1 = new Socket(ip, 1234);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        outputStream = sendSocket1.getOutputStream();
        DataInputStream in = new DataInputStream(inputStream);
        DataOutputStream out = new DataOutputStream(outputStream);
        //向服务器端发送一条消息
        byte mail[] = name.getBytes("unicode");
        out.write(mail,0,mail.length);
        out.flush();

        MySocket sendSocket = new MySocket(sendSocket1);
        MsgBuffer receiveBuffer = new MsgBuffer(100);
        ChatClientUI chatClientUI = new ChatClientUI(sendSocket,name);
        chatClientUI.setVisible(true);
        chatClientUI.setLocationRelativeTo(null);


        Socket receiveSocket1 = new Socket(ip, 1235);
        MySocket receiveSocket = new MySocket(receiveSocket1);
        ReceiveSockect receiveServer = new ReceiveSockect(receiveBuffer,receiveSocket);
        ReadBuffer readBuffer = new ReadBuffer(chatClientUI,receiveBuffer);
        receiveServer.start();
        readBuffer.start();
    }
}