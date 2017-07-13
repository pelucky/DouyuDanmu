package com.pelucky.danmu.thread;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.danmu.util.TcpSocketClient;

public class KeepaliveSender implements Runnable {
    private TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(KeepaliveSender.class);

    public KeepaliveSender(TcpSocketClient tcpSocketClient) {
        this.tcpSocketClient = tcpSocketClient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                long unixTime = System.currentTimeMillis() / 1000L;
                this.tcpSocketClient.sendData("type@=keeplive/tick@=" + unixTime + "/");
            } catch (IOException e) {
                logger.info("Send keepalive fail!");
                e.printStackTrace();
            }
            try {
                Thread.sleep(40000);
            } catch (InterruptedException e) {
                logger.info("Sleep interrupted!");
                e.printStackTrace();
            }
        }
    }
}
