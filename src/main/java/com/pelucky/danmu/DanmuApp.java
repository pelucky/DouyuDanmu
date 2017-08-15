package com.pelucky.danmu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.danmu.util.Danmu;


public class DanmuApp {
    private static Logger logger = LoggerFactory.getLogger(DanmuApp.class);
    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream inputStream = null;

        String danmu_server = null;
        int danmu_port = 0;
        String auth_server = null;
        int auth_port = 0;
        String room_id = null;
        String username = null;
        String ltkid = null;
        String stk = null;
        try {
            inputStream = DanmuApp.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
            danmu_server = properties.getProperty("danmu_server", "openbarrage.douyutv.com");
            danmu_port = Integer.valueOf(properties.getProperty("danmu_port", "8601"));
            auth_server = properties.getProperty("auth_server", "119.90.49.89");
            auth_port = Integer.valueOf(properties.getProperty("auth_port", "8092"));
            room_id = properties.getProperty("room_id");
            username = properties.getProperty("username");
            ltkid = properties.getProperty("ltkid");
            stk = properties.getProperty("stk");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
        }
        Danmu danmu = new Danmu(danmu_server, danmu_port, auth_server, auth_port, room_id, username, ltkid, stk);

        // Start Danmu server for receiving danmu
        /*danmu.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // Auth server for send danmu
        danmu.authDanmu();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        danmu.sendDanmu("#签到 " +new Date().toString());
    }
}
