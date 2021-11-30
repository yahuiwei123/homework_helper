package com.example.homeworkhelper.utils;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private static void request(byte[] image) throws Exception {
        String server_url = "https://isi.daliapp.net/isi/api/v1/item/search";
        URL url = new URL(server_url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
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

        //这里写上图片解码
        String imageBytes = encoder.encodeToString(image);

        String postData = "{\"base64Image\":\"" + imageBytes + "\"}";
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-lenght", Integer.toString(postData.length()));
        connection.setRequestProperty("Content-Type", "application/json");

        Long timestamp = System.currentTimeMillis();
        connection.setRequestProperty("x-isi-timestamp", timestamp.toString());


        String step1 = timestamp.toString() + ":" + ((url.getQuery() != null) ? url.getQuery().getBytes() : "") + postData;
        String step2 = createSignature(step1, sk);

        connection.setRequestProperty("x-isi-signmethod", "hmacsha256");
        connection.setRequestProperty("x-isi-signature", ak + ":" + step2);

        try {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(postData.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        }

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try {
                InputStream inputStream = (InputStream) connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String output = reader.readLine();
                System.out.println("chat" + output);
            } catch (Exception e) {
                throw new Exception("Exception while push the notification  $exception.message");
            }
        }
    }

    public static void test_api(byte[] src) {
        try{
            request(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String img_url = "C:/Users/wyh/Desktop/api_test.jpg";
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(img_url);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        request(data);
    }


}
