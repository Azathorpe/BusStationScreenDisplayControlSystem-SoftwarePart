package com.azathorpe;

import com.azathorpe.Utils.SerialPortUtils;

import java.util.ArrayList;

// 整个程序的入口 需要作为服务一直挂着这个程序
// 整个程序原理就是监听串口 然后通过串口和单片机交互
public class app {
    public static ArrayList<Thread> threadPool = new ArrayList<>();

    public static void main(String[] args) {
        SerialPortUtils.serialPortInitialization();
        System.out.println(SerialPortUtils.startListenMessageThread());

        while(!threadPool.isEmpty()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            SerialPortUtils.sendMessage("0");
        }

    }
}
