package com.tzh;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

public class OkHttpDemo {
    static final Log log = LogFactory.getLog(OkHttpDemo.class);
    public static void main(String[] args) throws Exception {

        String url = "http://localhost:8801";
        String text = OkHttpDemo.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);
        OkHttpDemo.client = null;
    }
    private static OkHttpClient client = new OkHttpClient();

    private static String getAsString(String url)  {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }catch (Exception e){
            log.error("getAsString() [url:" +url+ " got exception!]", e);
        }
        return null;
    }
}
