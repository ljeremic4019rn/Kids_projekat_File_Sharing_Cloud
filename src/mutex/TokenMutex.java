package mutex;

import app.AppConfig;
import servent.base_message.Message;
import servent.base_message.util.MessageUtil;
import servent.storage_message.TokenMessage;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class TokenMutex {

    private static final AtomicBoolean localLock = new AtomicBoolean(false);

    private static volatile boolean haveToken = false;
    private static volatile boolean wantLock = false;

    //Poziva ga samo prvi cvor u sistemu
    public static void init() {
        haveToken = true;
    }

    public static void lock() {
        wantLock = true;

        long sleepTime = 1;
        while (!haveToken) {
            try {
                Thread.sleep(sleepTime);
                sleepTime = (sleepTime * 2) > 100 ? 100 : (sleepTime * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void unlock() {
        haveToken = false;
        wantLock = false;
        sendToken();
    }

    public static void receiveToken() {
        if (wantLock) {
            haveToken = true;
        } else {
            sendToken();
        }
    }

    private static void sendToken() {
        String nextNodeIp = AppConfig.chordState.getNextNodeIp();
        int nextNodePort = AppConfig.chordState.getNextNodePort();
        Message tokenMessage = new TokenMessage(AppConfig.myServentInfo.getIpAddress(), AppConfig.myServentInfo.getListenerPort(), nextNodeIp, nextNodePort);
//        AppConfig.timestampedStandardPrint("# SENDING TOKEN FROM " + AppConfig.myServentInfo.getListenerPort() + " TO " + nextNodePort);
        MessageUtil.sendMessage(tokenMessage);
    }

}
