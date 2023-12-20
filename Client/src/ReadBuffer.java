/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 10:49
 */
public class ReadBuffer extends Thread{
    ChatClientUI chatClientUI;
    MsgBuffer msgBuffer;
    public ReadBuffer(ChatClientUI _chat,MsgBuffer _msgBuffer){
        chatClientUI = _chat;
        msgBuffer = _msgBuffer;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("接受中..");
            try{
                msgBuffer.full.acquire();
                msgBuffer.mutex.acquire();
            }catch (Exception e){
                e.printStackTrace();
            }
            Message nowstr = msgBuffer.getMsg();
            System.out.println("缓冲区有:" + nowstr.toString());
            chatClientUI.addMsg(nowstr);

            msgBuffer.empty.release();
            msgBuffer.mutex.release();
        }
    }
}
