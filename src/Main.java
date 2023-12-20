import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *    @Author FragmentsZZ
 *    @Time   2023/12/6 20:26
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String input = "[2023][Fragments][111][hhhh]";

        // 使用正则表达式匹配方括号中的内容
        Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(input);

        // 逐个获取匹配到的内容
        while (matcher.find()) {
            String contentInBrackets = matcher.group(1);
            System.out.println("Content in brackets: " + contentInBrackets);
        }

    }
}