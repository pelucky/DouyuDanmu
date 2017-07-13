package com.pelucky.danmu.util;

import java.io.IOException;
// import java.security.MessageDigest;
// import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pelucky.danmu.thread.KeepaliveSender;
import com.pelucky.danmu.thread.ReceiveData;

public class Danmu {
    TcpSocketClient tcpSocketClient;
    private Logger logger = LoggerFactory.getLogger(Danmu.class);
    private KeepaliveSender keepaliveSender;
    private ReceiveData receiveData;
    private String roomID;

    public Danmu(String danmu_server, int danmu_port, String roomID) {
        tcpSocketClient = new TcpSocketClient(danmu_server, danmu_port);
        keepaliveSender = new KeepaliveSender(tcpSocketClient);
        receiveData = new ReceiveData(tcpSocketClient);
        this.roomID = roomID;
    }

    public void start() {
        try {
            receiveData();
            tcpSocketClient.sendData("type@=loginreq/roomid@=" + roomID + "/");
            tcpSocketClient.sendData("type@=joingroup/rid@=" + roomID + "/gid@=-9999/");
            sendKeepalive();
            tcpSocketClient.sendData(
                    "type@=chatmessage/receiver@=0/content@=ghjkl/scope@=/col@=0/pid@=/p2p@=0/nc@=0/rev@=0/ifs@=0/");
            logger.info("Danmu start succefully!");
        } catch (IOException e) {
            logger.error("Danmu init error!");
            e.printStackTrace();
            tcpSocketClient.closeSocket();
        }
    }

    private void sendKeepalive() {
        new Thread(keepaliveSender).start();
    }

    private void receiveData() {
        new Thread(receiveData).start();
    }

    /* Try to send Danmu, but failed!
     *
     * public void sendDanmu() {
        TcpSocketClient tcpSocketClientInfo = new TcpSocketClient("119.90.49.89", 8092);
        TcpSocketClient tcpSocketClientDanmu = new TcpSocketClient("117.187.26.60", 12601);
        try {
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);// 获取当前时间戳
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();// 构造uuid作为devid参数
            String vk = MD5Util.MD5(timestamp + "7oE9nPEG9xXV69phU31FYCLUagKeYtsF" + uuid);// vk参数

            String loginreqInfo = "type@=loginreq/username@=/ct@=0/password@=/roomid@=1688149/devid@=" + uuid + "/rt@="
                    + timestamp + "/vk@=" + vk
                    + "/ver@=20150929/aver@=2017063001/ltkid@=61087238/biz@=1/stk@=9ab5c1c99201daca/";
            String loginreqDanmu = "type@=loginreq/username@=auto_uiccRqlXfM/password@=1234567890123456/roomid@=1688149/";

            tcpSocketClientInfo.sendData(loginreqInfo);
            tcpSocketClientInfo.receiveData();
            tcpSocketClientInfo.sendData("type@=qtlnq/");
            tcpSocketClientInfo.sendData("type@=qtlq/");
            tcpSocketClientInfo.sendData("type@=reqog/uid@=5235370/");
            tcpSocketClientInfo
                    .sendData("type@=keeplive/tick@=" + timestamp + "/vbw@=0/k@=8624d25cba824a2622c455498a025e34/");
            tcpSocketClientInfo.receiveData();

            tcpSocketClientInfo.sendData("type@=get_online_noble_list/rid@=1688149/");
            tcpSocketClientInfo.sendData("type@=qrl/rid@=1688149/et@=0/");
            tcpSocketClientInfo.receiveData();
            tcpSocketClientInfo.receiveData();

            tcpSocketClientInfo.sendData(
                    "type@=sus/pd@=f30b3689057c6c20a22a514ec5defa504febe7f133233bb44b3c5bf86a367609484ec6a9fee56338df72413adc2459fd24f742ee52dcfecbba6c921dc5eaa8dd36d458cbe7ca722da215/tt@="
                            + timestamp + "/sqn@=0/t@=13/v@=22d3ea14/");
            tcpSocketClientInfo.receiveData();
            tcpSocketClientInfo.sendData(
                    "d7d2bf1a29e13cdd09af6e6a325c7a523a6a48b90cb2abe601226bf82e958461a705c33f644513d092ccd994f82853032894d1191004b1f95e8f7a273157146db2a50ebe71d3004793175d6e3dae43e93577e65ae9c54a6d0e108d8e122bd6a79815ee0a4fbe53bbf402cb95116ca96404734735761e85ed40a7d/tt@="
                            + timestamp + "/sqn@=1/t@=13/v@=ff120d44/");
            tcpSocketClientInfo.receiveData();
            tcpSocketClientInfo.sendData(
                    "type@=smi/did@=WHJMrwNw1k/FuJrR9wIzcQrB4YI9n7hcmyw1CsTpEx6e3uzSG2OZnPAqDaNP94YJr8a2ukOH0h9MYuqcL6bBlqtqeQZw9BGdn/xbAlu1JcB53kN2ZvZQ0ApSD3EmPbcN1lY84oDL/jgJcRyfBiMVFVE+KJU2N3dNi6g6xLn6afrD+9bxeXdAl37K5rfholf+0fUGgIqCuSLQ=1487582755342/");
            tcpSocketClientInfo.receiveData();

            tcpSocketClientDanmu.sendData(loginreqDanmu);
            tcpSocketClientDanmu.receiveData();
            tcpSocketClientDanmu.sendData("type@=joingroup/rid@=1688149/gid@=1/");
            // tcpSocketClientDanmu.receiveData();

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tcpSocketClientInfo.sendData(
                    "type@=chatmessage/receiver@=0/content@=ghjkl/scope@=/col@=0/pid@=/p2p@=0/nc@=0/rev@=0/ifs@=0/");
            tcpSocketClientInfo.receiveData();
            tcpSocketClientInfo.receiveData();
            tcpSocketClientInfo.receiveData();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}

/**
 * Created by Brucezz on 2016/01/04. DouyuCrawler

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
*/