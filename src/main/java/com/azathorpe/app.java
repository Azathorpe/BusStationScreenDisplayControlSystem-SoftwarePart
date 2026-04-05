package com.azathorpe;

import com.azathorpe.Utils.DataBaseUtils;
import com.azathorpe.Utils.SerialPortUtils;

import java.util.ArrayList;

// 整个程序的入口 需要作为服务一直挂着这个程序
// 整个程序原理就是监听串口 然后通过串口和单片机交互
public class app {
    public static ArrayList<Thread> threadPool = new ArrayList<>();

    public static void main(String[] args) {
        //每次启动系统都可以重置所有汽车状态
//        DataBaseUtils.resetAllBus();

        SerialPortUtils.serialPortInitialization();
        System.out.println(SerialPortUtils.startListenMessageThread());

        while(!threadPool.isEmpty()){
            //管理员面板
            //写完记得push到仓库



        }

    }
}
