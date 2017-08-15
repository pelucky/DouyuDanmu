package com.pelucky.danmu.thread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.danmu.util.TcpSocketClient;

public class ReceiveData implements Runnable {
    private TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(ReceiveData.class);

    public ReceiveData(TcpSocketClient tcpSocketClient) {
        this.tcpSocketClient = tcpSocketClient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                InputStream inputStream = tcpSocketClient.getSocket().getInputStream();

                byte[] msg = new byte[10240];
                int line = 0;
                line = inputStream.read(msg);
                byteOutput.write(msg, 0, line);
                byte[] receiveMsg = byteOutput.toByteArray();
                tcpSocketClient.getDouyuProtocolMessage().receivedMessageContent(receiveMsg);
            } catch (IOException e) {
                logger.info("Receive IO error!");
                logger.info(e.getMessage());
            }
        }
    }
}
