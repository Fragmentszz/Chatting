import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.time.LocalDateTime;

public class ChatClientUI extends JFrame {
    private JTextPane chatPane;
    MySocket socket;
    private JTextField messageField;
    private  JTextField receiverField;
    private String name;
    private JScrollPane jScrollPane;
    public ChatClientUI( MySocket _s,String _name) {
        super("Simple Chat Client");
        name = _name;
        socket = _s;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // 创建聊天区域
        chatPane = new JTextPane();
        chatPane.setContentType("text/html");
        chatPane.setEditable(false);

        // 创建消息输入框和发送按钮
        messageField = new JTextField();
        receiverField = new JTextField("输入接收者");
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // 布局设置
        setLayout(new BorderLayout());
        jScrollPane = new JScrollPane(chatPane);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(receiverField, BorderLayout.LINE_START);
        bottomPanel.add(sendButton, BorderLayout.LINE_END);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String context = messageField.getText();
        String to = receiverField.getText();
        String time = LocalDateTime.now().toString().substring(0,19);
        Message message = new Message(time,to,context,name);
        // 在这里添加发送消息的逻辑，可以通过网络连接将消息发送给服务器
        // 然后将消息显示在聊天区域中
        SendThread sendThread = new SendThread(message,socket);
        sendThread.start();
        // 设置消息格式，使用CSS来设置样式
        String htmlMessage = "<div style='text-align: right; color: blue;'> " + message.toHTMLString() + "</div>";
        appendToPane(chatPane, htmlMessage);

        messageField.setText(""); // 清空输入框
    }
    public void addMsg(Message msg){
        String addmsg = "";
        if(msg.from.equals(name)){
            addmsg =  "<div style='text-align: right; color: red;'> " + msg.toHTMLString() + "</div>";
        }else if(msg.to.equals(name)){
            addmsg = "<div style='text-align: left; color: red;'> " + msg.toHTMLString() + "</div>";
        }
        appendToPane(chatPane,addmsg);

    }
    private void appendToPane(JTextPane tp, String msg) {
        // 将消息附加到文本窗格
        HTMLDocument doc = (HTMLDocument) tp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit) tp.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollChatPaneToBottom();
    }
    public void scrollChatPaneToBottom() {
        // 将聊天区域的滚动条定位到文档的最底部
        int documentLength = chatPane.getDocument().getLength();
        chatPane.setCaretPosition(documentLength);
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ChatClientUI chatClientUI = new ChatClientUI();
//                chatClientUI.setVisible(true);
//                chatClientUI.setLocationRelativeTo(null);
//            }
//        });
//    }
}
