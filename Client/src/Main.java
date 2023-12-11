import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1",7777);
            //构建IO
            InputStream inputStream = s.getInputStream();
            OutputStream outputStream = s.getOutputStream();
            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);
            //向服务器端发送一条消息
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            byte mail[] = msg.getBytes("unicode");
            out.write(mail,0,mail.length);
            out.flush();

            s.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}