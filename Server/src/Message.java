import java.util.regex.Matcher;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/13 13:38
 */



public class Message {
    static public Message Parse(String input){
        // 使用正则表达式匹配方括号中的内容
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(input);
        String content[] = new String[5];
        int now = 0;
        // 逐个获取匹配到的内容
        while (matcher.find()) {
            String contentInBrackets = matcher.group(1);
            content[now++] = contentInBrackets;
        }
        return new Message(content[0],content[1],content[2],content[3]);
    }
    String msg;
    String from;
    String to;
    String time;
    Message(String _time, String _to,  String _msg,String _from){
        msg = _msg;
        to = _to;
        from = _from;
        time = _time;
    }
    public String toString(){
        String res = "";
        res += "[" + time + "]" + "[" + to + "]" + "[" + msg + "]" + "[" + from + "]";
        return res;
    }
}
