package net.teamuni.livemc.twip;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.thread.EventThread;
import net.teamuni.livemc.LiveMC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TwipClient {

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
                .on(Socket.EVENT_CONNECT, args -> Bukkit.getLogger().info("Twip에 연결되었습니다."))
                .on("new donate", args -> runSyncCommand(LiveMC.getInstance().getDonationCommand()))
                .on("new redemption", args -> runSyncCommand(LiveMC.getInstance().getMarketRewardCommand()));
        this.socket.connect();
    }

    private void runSyncCommand(String command) {
        try {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            }.runTask(LiveMC.getInstance());
        } catch (IllegalPluginAccessException ignored) {
        }
    }

    private String readVersion(String key) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(String.format(TWIP_URL, key)).openConnection();
        connection.setRequestMethod("GET");
        String html = new String(connection.getInputStream().readAllBytes());
        Pattern pattern = Pattern.compile("version: '(.*)'");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IOException("version을 찾을 수 없습니다.");
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
}