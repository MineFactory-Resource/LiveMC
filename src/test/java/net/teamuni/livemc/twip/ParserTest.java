package net.teamuni.livemc.twip;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserTest {

    public static void main(String[] args) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL("https://twip.kr/widgets/alertbox/Q0534paMMN").openConnection();
        connection.setRequestMethod("GET");
        System.out.println(System.currentTimeMillis());
        String html = new String(connection.getInputStream().readAllBytes());
        System.out.println(html);
        System.out.println(parseVersion(html));
        System.out.println(System.currentTimeMillis());
    }

    private static String parseVersion(String script) {
        Pattern p = Pattern.compile("version: '(.*)'");
        Matcher m = p.matcher(script);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
}