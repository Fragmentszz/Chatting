/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 19:14
 */
public class UserResource {
    MySocket receive;
    MsgBuffer sendBuffer;
    MsgBuffer publicReceiveBuffer;
    MySocket send;
    String username;
    public UserResource(MySocket _receive,MySocket _send,MsgBuffer publicReceive,MsgBuffer _sendBuffer){
        receive = _receive;
        send = _send;
        sendBuffer = _sendBuffer;
        publicReceiveBuffer = publicReceive;
    }
}
