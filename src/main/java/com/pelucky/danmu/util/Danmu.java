package com.pelucky.danmu.util;

import java.security.MessageDigest;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.danmu.thread.KeepaliveSender;
import com.pelucky.danmu.thread.ReceiveData;

public class Danmu {
    TcpSocketClient tcpSocketClient;
    private TcpSocketClient tcpSocketClientAuth;
    private Logger logger = LoggerFactory.getLogger(Danmu.class);
    private KeepaliveSender keepaliveSender;
    private ReceiveData receiveData;
    private ReceiveData receiveDataAuth;
    private String roomID;

    public Danmu(String danmu_server, int danmu_port, String roomID) {
        tcpSocketClient = new TcpSocketClient(danmu_server, danmu_port);
        keepaliveSender = new KeepaliveSender(tcpSocketClient);
        receiveData = new ReceiveData(tcpSocketClient);

        tcpSocketClientAuth = new TcpSocketClient("119.90.49.89", 8092);
        receiveDataAuth = new ReceiveData(tcpSocketClientAuth);
        this.roomID = roomID;
    }

    public void start() {
        receiveData();
        tcpSocketClient.sendData("type@=loginreq/roomid@=" + roomID + "/");
        tcpSocketClient.sendData("type@=joingroup/rid@=" + roomID + "/gid@=-9999/");
        sendKeepalive();
        logger.info("Danmu start succefully!");
    }

    private void sendKeepalive() {
        new Thread(keepaliveSender).start();
    }

    private void receiveData() {
        new Thread(receiveData).start();
    }

    /**
     *
     * Auth server, The
     */
    public void authDanmu() {
        // Auth server
        new Thread(receiveDataAuth).start();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 获取当前时间戳
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();// 构造uuid作为devid参数
        String vk = MD5Util.MD5(timestamp + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid);// vk参数
        // String vk = MD5Util.MD5(timestamp +
        // "r5*^5;}2#\\${XF[h+;'./.Q'1;,-]f'p[" + uuid);// vk参数

        String username = "auto_uiccRqlXfM";
        String ltkid = "61087243";
        String stk = "50402ea12d0f5cec";
        String loginreqInfo = "type@=loginreq/username@=" + username + "/ct@=0/password@=/roomid@=" + roomID
                + "/devid@=" + uuid + "/rt@=" + timestamp + "/vk@=" + vk + "/ver@=20150929/aver@=2017073111/ltkid@="
                + ltkid + "/biz@=1/stk@=" + stk + "/";

        tcpSocketClientAuth.sendData(loginreqInfo);
    }

    public void sendDanmu(String message) {
        // byte [] messageByte = DouyuProtocolMessage.encodeMessage(message);
        message = DouyuProtocolMessage.encodeMessage(message);
        System.out.println("message: " + message);
        System.out.println();
        // byte[] messageByte = new byte[] { (byte) 0xe6, (byte) 0xb5, (byte) 0x8b, (byte) 0xe8, (byte) 0xaf, (byte) 0x95 };
        tcpSocketClientAuth.sendData("type@=chatmessage/receiver@=0/content@=" + message
                + "/scope@=/col@=0/pid@=/p2p@=0/nc@=0/rev@=0/ifs@=0/");
    }
}

/**
 * Created by Brucezz on 2016/01/04. DouyuCrawler
 */
class MD5Util {
    public static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要, 获得密文
            byte[] md = mdInst.digest(s.getBytes());
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte b : md) {
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
