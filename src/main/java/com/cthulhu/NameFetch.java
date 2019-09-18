package com.cthulhu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class NameFetch {

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
