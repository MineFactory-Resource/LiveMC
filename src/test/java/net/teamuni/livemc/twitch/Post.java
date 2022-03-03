package net.teamuni.livemc.twitch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {

    public static void main(String[] args) {
        try {
            HttpURLConnection url = (HttpURLConnection) new URL("https://id.twitch.tv/oauth2/authorize" +
                    "?client_id=<your client ID>&" +
                    "redirect_uri=https://teamuni.kro.kr&" +
                    "response_type=code" +
                    "&scope=bits:read").openConnection();
            url.setRequestMethod("GET");
            System.out.println(url.getResponseCode());
            System.out.println(url.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}