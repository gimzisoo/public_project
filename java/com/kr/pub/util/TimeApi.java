package com.kr.pub.util;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;

public class TimeApi {
    public static String getTime() throws Exception{
        String apiUrl = "https://timeapi.io/api/Time/current/zone?timeZone=Asia/Seoul";
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(restTemplate.getForObject(apiUrl, String.class));
        System.out.println(jsonObject);
        	String seconds =	String.valueOf(jsonObject.get("seconds"));//앞에 0추가하기
        if(seconds.length() == 1) {
        		seconds = "0"+seconds;
        }	
        
        String time = (String) jsonObject.get("time") +":" + seconds;
        return time;
    }
    // ZonedDateTime을 Timestamp로 변환하는 함수
    public static Timestamp encodingTime(ZonedDateTime zoneTime) {
       Timestamp timeStamp = Timestamp.from(zoneTime.toInstant());
       return timeStamp;
    }
}
