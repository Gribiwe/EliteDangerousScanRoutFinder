package service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HttpSender {


   public synchronized static String sendGet(String endpoint, String ... paramValues) throws Exception {
      HttpClient client = new DefaultHttpClient();
      StringBuilder builder = new StringBuilder("?");
      for (String paramValue : paramValues) {
         builder.append(paramValue).append("&");
      }
      System.out.println(endpoint+builder.toString());
      HttpGet request = new HttpGet(endpoint+builder.toString());
      HttpResponse response = client.execute(request);
      BufferedReader rd = new BufferedReader(
              new InputStreamReader(response.getEntity().getContent()));

      StringBuilder result = new StringBuilder();
      String line = "";
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      return result.toString();
   }
}
