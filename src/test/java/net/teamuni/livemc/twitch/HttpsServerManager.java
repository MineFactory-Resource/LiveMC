package net.teamuni.livemc.twitch;

import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

public class HttpsServerManager {

    public static HttpsServer httpsServer;

    public static void main(String[] args) {
        try {
            InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 443); //주소 인스턴스생성.
            httpsServer = HttpsServer.create(address, 0); //서버 생성.
            SSLContext sslContext = SSLContext.getInstance("TLS");
            char[] password = "carrie7280*".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            //C:\Users\User
            //C:\Certbot\live\teamuni.kro.kr
            File file = new File("%PATH%\\test.jks");
            FileInputStream fis = new FileInputStream(file);
            ks.load(fis, password);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        SSLContext c = SSLContext.getDefault();
                        SSLEngine engine = c.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParameters);
                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
            httpsServer.createContext("/", new RootHandler(() -> System.out.println("TEST")));
            httpsServer.setExecutor(null);
            httpsServer.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static class RootHandler implements HttpHandler {

        private final CallBack callBack;

        public RootHandler(CallBack callBack) {
            this.callBack = callBack;
        }

        public void handle(HttpExchange exchange) throws IOException {
            callBack.onEvent();
            InputStream stream = exchange.getRequestBody();
            String response = JsonParser.parseString(new String(stream.readAllBytes(), StandardCharsets.UTF_8)).getAsJsonObject().get("challenge").getAsString();
            OutputStream os = exchange.getResponseBody();
            exchange.sendResponseHeaders(200, response.length());
            os.write(response.getBytes());
            os.close();
        }

//
//        private String getHmac(String secret, String message) {
//
//        }
//
//        private String getHmacMessage(Headers requestHeader, OutputStream body) {
//            String id = requestHeader.getFirst("Twitch-Eventsub-Message-Id".toLowerCase());
//            String timeStamp = requestHeader.getFirst("Twitch-Eventsub-Message-Timestamp".toLowerCase());
//            return id + timeStamp + body;
//        }
    }
}