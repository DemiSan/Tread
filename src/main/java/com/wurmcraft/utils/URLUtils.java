package com.wurmcraft.utils;

import com.wurmcraft.Tread;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLUtils {

  public static final String USER_AGENT = "tread";

  public static <T extends Object> T get(String url, Class<T> type) {
    if (url != null && !url.isEmpty()) {
      try {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        return Tread.GSON.fromJson(response.toString(), type);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
