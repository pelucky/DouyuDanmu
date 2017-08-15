package com.pelucky.danmu;

import java.io.IOException;
import java.io.InputStream;
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
        String room_id = null;
        try {
            inputStream = DanmuApp.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(inputStream);
            danmu_server = properties.getProperty("danmu_server", "openbarrage.douyutv.com");
            danmu_port = Integer.valueOf(properties.getProperty("danmu_port", "8601"));
            room_id = properties.getProperty("room_id");
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
        Danmu danmu = new Danmu(danmu_server, danmu_port, room_id);
        danmu.start();
        danmu.authDanmu();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        danmu.sendDanmu("fuxxxxx");
    }
}
