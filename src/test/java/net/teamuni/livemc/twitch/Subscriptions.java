package net.teamuni.livemc.twitch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Subscriptions {

    public static void main(String[] args) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.twitch.tv/helix/eventsub/subscriptions").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer 0854mla3cdilur1r8ujoezo0j2kg95");
            connection.setRequestProperty("Client-Id", "yl13ma915vod2dyjl6fyg5q89mvgp6");
            connection.setRequestProperty("Content-Type", "application/json");
            String jsonData = "{\"type\":\"channel.cheer\",\"version\":\"1\",\"condition\":{\"broadcaster_user_id\":\"423815778\"},\"transport\":{\"method\":\"webhook\",\"callback\":\"https://teamuni.kro.kr/\",\"secret\":\"pc7etl9cjhonx8ncq54lgrimu9nonr\"}}";
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] request_data = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(request_data);
            }
            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            in.close();
            System.out.println(responseCode);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}