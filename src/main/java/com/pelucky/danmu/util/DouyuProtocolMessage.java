package com.pelucky.danmu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DouyuProtocolMessage {
    private int[] messageLength;
    private int[] code;
    private int[] end;
    private ByteArrayOutputStream byteArrayOutputStream;
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private Logger logger = LoggerFactory.getLogger(DouyuProtocolMessage.class);

    public DouyuProtocolMessage() {
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    public byte[] sendMessageContent(String content) throws IOException {
        this.messageLength = new int[] { calcMessageLength(content), 0x00, 0x00, 0x00 };
        this.code = new int[] { 0xb1, 0x02, 0x00, 0x00 };
        this.end = new int[] { 0x00 };

        byteArrayOutputStream.reset();
        for (int i : messageLength)
            byteArrayOutputStream.write(i);
        for (int i : messageLength)
            byteArrayOutputStream.write(i);
        for (int i : code)
            byteArrayOutputStream.write(i);
        byteArrayOutputStream.write(content.getBytes());
        for (int i : end)
            byteArrayOutputStream.write(i);
        return byteArrayOutputStream.toByteArray();
    }

    private int calcMessageLength(String content) {
        return 4 + 4 + (content == null ? 0 : content.length()) + 1;
    }

    public void receivedMessageContent(byte[] receiveMsg) {
        // Copy from stackoverflow
        String message = bytesToHex(receiveMsg);

        // Get first "/"
        int slashIndex = message.indexOf("2F") / 2;
        String messageType = new String();
        for (int i = 18; i < slashIndex; i++) {
            messageType += (char) receiveMsg[i];
        }

        // Determine type of message
        if (messageType.equals("chatmsg")) {
            // "/nn@="
            int nicknameIndex = message.indexOf("2F6E6E403D") / 2;
            // "/txt@="
            int textIndex = message.indexOf("2F747874403D") / 2;
            // "/cid@="
            int textEndIndex = message.indexOf("2F636964403D") / 2;
            String nickname = new String();
            String text = new String();
            // Change nickname into Chinese if need
            for (int i = nicknameIndex + 5; i < textIndex; i++) {
                if (receiveMsg[i] < 32 || receiveMsg[i] > 126) {
                    try {
                        nickname += "%" + Integer.toHexString((receiveMsg[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                    } catch (StringIndexOutOfBoundsException e) {
                        logger.info("String index out of range. receiveMsg: {}", receiveMsg[i]);
                        e.printStackTrace();
                    }
                } else {
                    nickname += (char) receiveMsg[i];
                }
            }
            String decodedNickname = null;
            try {
                decodedNickname = URLDecoder.decode(nickname, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                logger.info("Decode error! nickname: {}, {}", nickname, receiveMsg);
                e1.printStackTrace();
            }
            decodedNickname = decode(decodedNickname);

            // Change text into Chinese if need
            for (int i = textIndex + 6; i < textEndIndex; i++) {
                if (receiveMsg[i] < 32 || receiveMsg[i] > 126) {
                    try {
                        text += "%" + Integer.toHexString((receiveMsg[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                    } catch (StringIndexOutOfBoundsException e) {
                        logger.info("String index out of range. receiveMsg: {}", receiveMsg[i]);
                        e.printStackTrace();
                    }
                } else {
                    text += (char) receiveMsg[i];
                }
            }

            String decodedText = null;
            try {
                decodedText = URLDecoder.decode(text, "utf-8");
            } catch (UnsupportedEncodingException e1) {
                logger.info("Decode error! text: {}, {}", text, receiveMsg);
                e1.printStackTrace();
            }
            decodedText = decode(decodedText);
            // logger.debug("{}: {}", decodedNickname, decodedText);
            System.out.println(decodedNickname + ": " + decodedText);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String encode(String str) {
        return str.replace("@", "@A").replace("/", "@S");
    }

    public String decode(String str) {
        return str.replace("@A", "@").replace("@S", "/");
    }
}
