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
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, JSONException, NoSuchAlgorithmException, KeyManagementException {

        // Bypass Namefake API https cert business
        certTrust.trustMgr();

        newCharacterData.configureStats();

        System.out.println(newCharacterData.charName);
        System.out.println(newCharacterData.strVal);
        System.out.println(newCharacterData.intelVal);
        System.out.println(newCharacterData.conVal);
        System.out.println(newCharacterData.appVal);
        System.out.println(newCharacterData.powVal);
        System.out.println(newCharacterData.sizeVal);
        System.out.println(newCharacterData.eduVal);
        System.out.println(newCharacterData.dexVal);

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

    public static class rollDice {

        public static int rollDSix(int numDice) {

            Random random = new Random();

            int i;
            int randomInteger = 0;
            int totalInt = 0;

            // Roll them bones! No zeroes, though
            for (i = 0; i < numDice; i++) {
                randomInteger = random.nextInt(6) + 1;

                totalInt = totalInt + randomInteger;
            }

            return totalInt;
        }
    }

    public static class newCharacterData {

        private static String charName;

        static {
            try {
                charName = newCharacterName(Globals.apiUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static String strVal;
        private static String intelVal;
        private static String conVal;
        private static String sizeVal;
        private static String dexVal;
        private static String appVal;
        private static String powVal;
        private static String eduVal;

        public static String newCharacterName(String url) throws IOException {

            // Hit that API and fetch the data
            JSONObject json = JSONFetch.readJsonFromUrl(url);

            String name = (String) json.get("name");

            return name;

        }

        public static void configureStats() {

            String[] skillsToConfigure = characterOptions.characteristics();

            int i;

            for (i = 0; i < skillsToConfigure.length; i++) {

                int thisScore;

                String thisSkill = skillsToConfigure[i];

                // Switch to determine stat and assign score
                switch(thisSkill) {

                    case "strength":
                       thisScore = rollDice.rollDSix(3);
                       strVal = "STR: " + Integer.toString(thisScore);
                       break;

                    case "dexterity":
                        thisScore = rollDice.rollDSix(3);
                        dexVal = "DEX: " + Integer.toString(thisScore);

                    case "intelligence":
                        thisScore = rollDice.rollDSix(2);
                        intelVal = "INT: " + Integer.toString(thisScore + 6);

                    case "constitution":
                        thisScore = rollDice.rollDSix(3);
                        conVal = "CON: " + Integer.toString(thisScore);

                    case "appearance":
                        thisScore = rollDice.rollDSix(3);
                        appVal = "APP: " + Integer.toString(thisScore);

                    case "power":
                        thisScore = rollDice.rollDSix(3);
                        powVal = "POW: " + Integer.toString(thisScore);

                    case "size":
                        thisScore = rollDice.rollDSix(2);
                        sizeVal = "SIZ: " + Integer.toString(thisScore + 6);

                    case "education":
                        thisScore = rollDice.rollDSix(2);
                        eduVal = "EDU: " + Integer.toString(thisScore + 6);


                    default:
                        break;
                }
            }
        }

    }

}
