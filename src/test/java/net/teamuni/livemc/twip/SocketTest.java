package net.teamuni.livemc.twip;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class SocketTest {

    private static Socket socket;

    public static void main(String[] args) throws IOException {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//                a(new String[]{"Q0534paMMN", version, encodeURIComponent(token)});
            }
        }, 0, 10 * 1000);
    }

    private static String parseVersion(String script) {
//        Pattern p = Pattern.compile("version: '(.*)'");
//        Matcher m = p.matcher(script);
//        if (m.find()) {
//            return m.group(1);
//        }
        return "1.1.63";
    }

    private static String parseToken(String script) {
//        Pattern p = Pattern.compile("window.__TOKEN__ = '(.*)'");
//        Matcher m = p.matcher(script);
//        if (m.find()) {
//            return m.group(1);
//        }
        return "ZmQ5MTIzNTEzMGYxMDAwMA==C3cgzYXwkBf7g+Jyu3UV5L7vhBxD2P0SMPCREL9Sj/hd2US29yaRdGKlEqR9RwYdOFhA241tY+BYebqxZR2hlN3OXZ1J6jtSJU+Tr0kL9p42zDn+1egLBTgmZFbM9tjVYMuMSyej3ExYVo47Ak+hvMgOIbAADp+duOXXLghYnVw=";
    }

    public static String encodeURIComponent(String s) {
        String result;
        result = URLEncoder.encode(s, StandardCharsets.UTF_8);
        return result;
    }

    private static void a(String[] a) {
        if (socket != null) {
            socket = socket.disconnect().connect();
            return;
        }
        String uri = "https://io.mytwip.net?alertbox_key=" + a[0]
                + "&version=" + a[1] + "&token=" + a[2];
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{"websocket", "polling"};
        opts.reconnection = true;

        try {
            socket = IO.socket(uri, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, (args) -> System.out.println("CONNECTED!"))
                .on(Socket.EVENT_CONNECT_ERROR, (args) -> Arrays.stream(args).forEach(System.out::println))
                .on("new donate", System.out::println);
        socket.connect();
    }
}
