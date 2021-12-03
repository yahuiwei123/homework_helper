package com.example.homeworkhelper.utils;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@RequiresApi(api = Build.VERSION_CODES.O)
public class APIUtils {
    private static String ak = "74677f9c254d4d6ab2cc4795f6f2a916";
    private static String sk = "4dac0b3c7021497bb9d487bd0d14c0fc";
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static String imageBytes;
    private static String result;
    private static String output;

    private static String createSignature(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        return encoder.encodeToString(array);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte item : bytes) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    public static void request() throws Exception {
        String server_url = "https://isi.daliapp.net/isi/api/v1/item/search";
        URL url = new URL(server_url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(300000);
        connection.setReadTimeout(300000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        TrustManager[] trustManagers = {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
                    @Override
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                }
        };

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, trustManagers, null);
        SSLSocketFactory sslSocketFactory = ctx.getSocketFactory();
        connection.setSSLSocketFactory(sslSocketFactory);



        String postData = "{\"base64Image\":\"" + imageBytes + "\"}";
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-lenght", Integer.toString(postData.length()));
        connection.setRequestProperty("Content-Type", "application/json");

        Long timestamp = System.currentTimeMillis() / 1000;
        connection.setRequestProperty("x-isi-timestamp", timestamp.toString());

        String step1 = timestamp.toString() + ":" + ((url.getQuery() != null) ? url.getQuery().getBytes() : "") + ":" + postData;
        String step2 = createSignature(step1, sk);

        System.out.println("step1" + step1);
        System.out.println("step2" + step2);
        connection.setRequestProperty("x-isi-signmethod", "hmacsha256");
        connection.setRequestProperty("x-isi-signature", ak + ":" + step2);

        try {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try {
                DataInputStream inputStream = new DataInputStream(connection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String output = reader.readLine();
                result = output;
                output = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 传入图片原始的字节数组
     */
    public static String call_api(byte[] src) {
        try{
            imageBytes = encoder.encodeToString(src);
            new Thread(){
                @Override
                public void run() {
                    try {
                        request();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入图片的Base64编码字符串
     */
    public static String call_api(String src) {
        try{
            imageBytes = src;
            new Thread(){
                @Override
                public void run() {
                    try {
                        request();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
