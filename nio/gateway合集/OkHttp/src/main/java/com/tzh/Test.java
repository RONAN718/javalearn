package com.tzh;

import okhttp3.*;
import java.io.IOException;
public class Test {
    private static OkHttpClient client = new OkHttpClient();
    public static void main(String[] args) throws IOException {
        getBody1(client, "http://localhost:8888/hello");
        client = null;
    }


    private static void getBody1(OkHttpClient client, String url){

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            System.out.println(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            client = null;
        }
    }
}