package com.azathorpe;

import com.azathorpe.Entities.Bus;
import com.azathorpe.Utils.SerialPortUtils;
import com.azathorpe.panel.AdminFrame;

import java.util.ArrayList;

// 整个程序的入口 需要作为服务一直挂着这个程序
// 整个程序原理就是监听串口 然后通过串口和单片机交互
public class app {
    public static ArrayList<Thread> threadPool = new ArrayList<>();
    private static AdminFrame adminFrame = new AdminFrame();
    public static boolean isRunning = false;
    public static ArrayList<Bus> allBus = new ArrayList<>();

    public static void main(String[] args) {
        isRunning = true;
        //每次启动系统都可以重置所有汽车状态
//        DataBaseUtils.resetAllBus();

        SerialPortUtils.serialPortInitialization();
        System.out.println(SerialPortUtils.startListenMessageThread());

        //管理员面板
        //写完记得push到仓库
        //初始化管理员面板
        adminFrame.init();
    }
}
