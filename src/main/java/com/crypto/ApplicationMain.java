package com.crypto;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertificateException;


public class ApplicationMain {

    public static void main(String [] args)
    {
//        ApiSpecificationDao dao = new ApiSpecificationDaoImpl();
//        ApiResponse response = dao.send(null);
//        System.out.println(response.getPublicKey());
        try {
            connectWS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void connectWS() throws URISyntaxException, JSONException {
        JSONArray pairs = new JSONArray();
        pairs.put("0~Poloniex~BTC~USD");
        pairs.put("0~Cryptsy~BTC~USD");
        JSONObject params = new JSONObject();
        params.put("subs", pairs);
//        String params = "{ subs: ['0~Poloniex~BTC~USD', '0~Cryptsy~BTC~USD'] }";

        OkHttpClient okHttpClient = getUnsafeOkHttpClient();

        // default settings for all sockets
        IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
        IO.setDefaultOkHttpCallFactory(okHttpClient);

        // set as an option
        IO.Options opts = new IO.Options();
        opts.callFactory = okHttpClient;
        opts.webSocketFactory = okHttpClient;
        Socket socket = IO.socket("https://streamer.cryptocompare.com/", opts);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("Connect!!!");
                socket.emit("SubAdd", params);

                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            socket.emit("SubRemove", params);
                            socket.disconnect();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })).start();
            }

        }).on("m", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                if(args.length == 1) {
                    System.out.println(args[0]);
                } else {
                    System.out.println("args = " + args.length);
                }

            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                System.out.println("Discconnect!!!");
            }

        });
        socket.connect();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };
            final TrustManager[] trustAllCerts = new TrustManager[] { tm };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslSocketFactory,tm)
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
