package com.azathorpe.Entities;

import com.mysql.cj.util.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * BUS类，包含车次、车牌、发车时间、余票、终点站、下一站和状态等属性
 * @version 1.0
 * @author Azathorpe
 * @date 2026年3月31日
 * @since 1.0
 * 更新日志:
 *  - 1.0 (2026年3月31日): 初始版本，包含基本属性和getter/setter方法
 */
public class Bus {
    // BUS的车次
    String busID;
    // BUS的车牌
    String busName;
    // BUS的发车时间(ms)
    long startTime;
    // BUS的余票
    int ticket;
    // BUS的终点站
    String destination;
    // BUS的下一站
    String nextStation;
    // 当前状态
    BusStatue busStatue;

    public static Bus getInstance(String busID, String busName, String startTime, String ticket, String destination, String nextStation, BusStatue busStatue) {
        Bus res = new Bus();
        res.setBusID(busID);
        res.setBusName(busName);
        long mil = 0;
        String[] split = startTime.split(":");
        mil += TimeUnit.HOURS.toMillis(Long.parseLong(split[0]));
        mil += TimeUnit.MINUTES.toMillis(Long.parseLong(split[1]));
        res.setStartTime(mil);
        res.setTicket(Integer.parseInt(ticket));
        res.setDestination(destination);
        res.setNextStation(nextStation);
        res.setBusStatue(busStatue);
        return res;
    }

    public static Bus getInstance(String busID, String busName, long startTime, String ticket, String destination, String nextStation, BusStatue busStatue) {
        Bus res = new Bus();
        res.setBusID(busID);
        res.setBusName(busName);
        res.setStartTime(startTime);
        res.setTicket(Integer.parseInt(ticket));
        res.setDestination(destination);
        res.setNextStation(nextStation);
        res.setBusStatue(busStatue);
        return res;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getStartTimeInString(){
        long hours = TimeUnit.MILLISECONDS.toHours(startTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(startTime) - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, minutes);
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    public BusStatue getBusStatue() {
        return busStatue;
    }

    public String getBusStatueInString(){
        switch (this.busStatue){
            case BUS_ARRIVED -> {
                return "已到达";
            }
            case BUS_DEPARTED -> {
                return "已离开";
            }
            case BUS_NOT_ARRIVED -> {
                return "未到达";
            }default -> {
                return "未知状态";
            }
        }
    }

    public void setBusStatue(BusStatue busStatue) {
        this.busStatue = busStatue;
    }
}
