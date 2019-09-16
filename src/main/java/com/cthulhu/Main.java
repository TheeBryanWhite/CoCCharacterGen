package com.cthulhu;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) throws IOException, JSONException, NoSuchAlgorithmException, KeyManagementException {

        String apiUrl = "https://api.namefake.com/";

        // Bypass Namefake API https cert business
        certTrust.trustMgr();

        // Hit that API and fetch the data
        JSONObject json = JSONFetch.readJsonFromUrl(apiUrl);

        // Gimme a name, bro
        System.out.println(json.get("name"));
    }

    public static class certTrust {

        public static void trustMgr () throws NoSuchAlgorithmException, KeyManagementException {

            TrustManager[] trustAllCerts = new TrustManager[] {

                    new X509TrustManager() {

                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}

                    }

            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        }
    }

    public static class JSONFetch {

        private static String readData(Reader reader) throws IOException {

            StringBuilder stringBuilder = new StringBuilder();

            int count;

            while ((count = reader.read()) != -1) {
                stringBuilder.append((char) count);
            }

            return stringBuilder.toString();

        }

        public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

            InputStream inputStream = new URL(url).openStream();

            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String jsonString = readData(reader);
                JSONObject jsonObjString = new JSONObject(jsonString);

                return jsonObjString;

            } finally {

                inputStream.close();

            }

        }

    }

}
