package net.teamuni.livemc.twip;

import io.socket.client.IO;
import io.socket.client.Socket;
import net.teamuni.livemc.LiveMC;
import org.bukkit.Bukkit;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwipClient implements Closeable {

    private static final String URI_FORMAT = "https://io.mytwip.net?alertbox_key=%s&version=%s&token=%s";
    private static final String TWIP_URL = "https://twip.kr/widgets/alertbox/%s";

    private final Socket socket;
    private final String key;
    private final String token;

    public TwipClient(String key, String token) throws IOException {
        this.key = key;
        this.token = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String version = readVersion(key);
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket", "polling"};
        options.reconnection = true;

        this.socket = IO.socket(URI.create(String.format(URI_FORMAT, this.key, version, this.token)), options);
        this.socket
                .on("new donate", args -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), LiveMC.getInstance().getDonationCommand()))
                .on("new redemption", args -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), LiveMC.getInstance().getMarketRewardCommand()));

        this.socket.connect();
    }

    private String readVersion(String key) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(String.format(TWIP_URL, key)).openConnection();
        connection.setRequestMethod("GET");
        Pattern p = Pattern.compile("version: '(.*)'");
        Matcher m = p.matcher(new String(connection.getInputStream().readAllBytes()));
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public Socket getSocket() {
        return socket;
    }

    public String getKey() {
        return key;
    }

    public String getToken() {
        return token;
    }

    @Override
    public void close() {
        this.socket.close();
    }
}