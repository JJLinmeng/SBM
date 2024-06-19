package com.mumu.studentbankmanagement.Util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class MapUtil {
    public static String[] reverseGeocode(double latitude, double longitude) {
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("src/main/resources/application.properties");
            prop.load(input);
            String apiKey = prop.getProperty("amap.api.key");
            URL url = new URL("https://restapi.amap.com/v3/geocode/regeo?output=json&location="
                    + latitude + "," + longitude + "&key=" + apiKey);
            System.out.println(url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 解析JSON字符串
            JSONObject obj = JSON.parseObject(String.valueOf(response));

            // 获取addressComponent对象
            JSONObject addressComponent = obj.getJSONObject("regeocode").getJSONObject("addressComponent");

            // 从addressComponent对象获取city和province
            String city = addressComponent.getString("city");
            String province = addressComponent.getString("province");

            return new String[]{province,city};



        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{"[]","[]"};
    }

//    public static void main(String[] args) {
//        double latitude = 116;
//        double longitude = 40;
//        String[] strings = reverseGeocode(longitude, latitude);
//      String content="";
//      content=!strings[0].equals("[]")?"在"+content+strings[0]:"";
////        System.out.println(content);
//      content=!strings[1].equals("[]")?content+strings[1]:content;
//        System.out.println(content);
//    }

}