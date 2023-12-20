/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 12:48
 */
public class moveMsg extends Thread {

    MsgBuffer publicSendBuffer;
    MsgBuffer[] receives;
    MySocket receive_sockets[];
    MySocket send_sockets[];
    String[] names;
    public moveMsg(MsgBuffer _send, MsgBuffer[] _receives,MySocket []_receive_sockets,MySocket[] _send_sockets){
        receives = _receives;
        publicSendBuffer = _send;
        receive_sockets = _receive_sockets;
        send_sockets = _send_sockets;
    }


    @Override
    public void run() {
        while(true){
            try{
                publicSendBuffer.full.acquire();
                publicSendBuffer.mutex.acquire();
            }catch (Exception e){
                System.out.println("服务器端获取公共缓冲区失败!");
                e.printStackTrace();
            }
            Message nowmsg = publicSendBuffer.getMsg();
            publicSendBuffer.empty.release();
            publicSendBuffer.mutex.release();
            for (int i = 0; i < receives.length; i++) {
                if(nowmsg.to == null || receives[i].name == null)   continue;
                if(!receive_sockets[i].effective || !send_sockets[i].effective){
                    receives[i].name = null;
                    continue;
                }
                System.out.println(receives[i].name);
                if(receives[i].name.equals(nowmsg.to)){
                    try{
                        receives[i].empty.acquire();
                        receives[i].mutex.acquire();
                    }catch (Exception e){
                        System.out.println("获取用户信箱出错！");
                        e.printStackTrace();
                    }
                    receives[i].insertMsg(nowmsg);
                    receives[i].full.release();
                    receives[i].mutex.release();
                }
            }


        }





        
    }
}
