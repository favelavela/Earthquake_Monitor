package com.example.damian.earthquakemonitor;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class JSONParser {
    private String urlString;
    private String data;
    public volatile boolean parsingComplete = true;

    public JSONParser(String url){
        this.urlString = url;
    }

    public String getData(){
        return data;
    }

    public void getJSONfromURL(){

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream stream = conn.getInputStream();
                    data = convertStreamToString(stream);
                    parsingComplete = false;
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}