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

        System.out.println(newCharacterData.newCharacterName(apiUrl));

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

    public static class characterOptions {

        public static String[] characteristics() {

            String[] charArray = {
                    "strength",
                    "dexterity",
                    "intelligence",
                    "constitution",
                    "appearance",
                    "power",
                    "size",
                    "education"
            };

            return charArray;

        }

        public static String[] skills() {

            String[] skillsArray = {
                    "accounting",
                    "anthropology",
                    "appraise",
                    "archaeology",
                    "art/craft",
                    "charm",
                    "climb",
                    "credit rating",
                    "chtulhu mythos",
                    "disguise",
                    "dodge",
                    "drive (auto)",
                    "electric repair",
                    "fast talk",
                    "fighting (brawl)",
                    "firearms (handgun)",
                    "firearms (rifle/shotgun)",
                    "first aid",
                    "history",
                    "intimidate",
                    "jump",
                    "language (other)",
                    "language (own)",
                    "law",
                    "library use",
                    "listen",
                    "locksmith",
                    "mechanical repair",
                    "medicine",
                    "natural world",
                    "navigate",
                    "occult",
                    "operate heavy machine",
                    "persuade",
                    "pilot",
                    "psychology",
                    "psychoanalysis",
                    "ride",
                    "science",
                    "sleight of hand",
                    "spot hidden",
                    "stealth",
                    "survival",
                    "swim",
                    "throw",
                    "track"
            };

            return skillsArray;

        }

    }

    public static class newCharacterData {

        public static String newCharacterName(String url) throws IOException {

            // Hit that API and fetch the data
            JSONObject json = JSONFetch.readJsonFromUrl(url);

            String name = (String) json.get("name");

            return name;

        }

    }

}
