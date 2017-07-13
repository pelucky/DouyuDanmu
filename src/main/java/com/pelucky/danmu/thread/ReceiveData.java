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
        while(true){
            try {
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                InputStream inputStream = tcpSocketClient.getSocket().getInputStream();

                // int length = 0;
                // length = inputStream.read();
                // byteOutput.write(length);
                // System.out.println("msg length:" + length);

                // byte[] msg = new byte[length + 3];
                byte[] msg = new byte[1024];

                int line;
                line = inputStream.read(msg);
                byteOutput.write(msg, 0, line);
                byte[] receiveMsg = byteOutput.toByteArray();
                tcpSocketClient.getDouyuProtocolMessage().receivedMessageContent(receiveMsg);

                // for (byte b : receiveMsg) {
                // System.out.print((char) b);
                // }
                // System.out.println();

            } catch (IOException e) {
                logger.info("Receive IO error!");
                e.printStackTrace();
            }
        }
    }
}
