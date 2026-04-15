package com.azathorpe.Entities;

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
    String bus_name;
    // BUS的发车时间(ms)
    long start_time;
    // BUS的余票
    int ticket;
    // BUS的终点站
    String destination;
    // BUS的下一站
    String next_station;
    // 当前状态
    BusStatue bus_statue;

    public static Bus getInstance(String busID, String busName, String startTime, String ticket, String destination, String nextStation, BusStatue busStatue) {
        Bus res = new Bus();
        res.setBusID(busID);
        res.setBus_name(busName);
        long mil = 0;
        String[] split = startTime.split(":");
        mil += TimeUnit.HOURS.toMillis(Long.parseLong(split[0]));
        mil += TimeUnit.MINUTES.toMillis(Long.parseLong(split[1]));
        res.setStart_time(mil);
        res.setTicket(Integer.parseInt(ticket));
        res.setDestination(destination);
        res.setNext_station(nextStation);
        res.setBus_statue(busStatue);
        return res;
    }

    public static Bus getInstance(String busID, String busName, long startTime, String ticket, String destination, String nextStation, BusStatue busStatue) {
        Bus res = new Bus();
        res.setBusID(busID);
        res.setBus_name(busName);
        res.setStart_time(startTime);
        res.setTicket(Integer.parseInt(ticket));
        res.setDestination(destination);
        res.setNext_station(nextStation);
        res.setBus_statue(busStatue);
        return res;
    }

    public String getBusID() {
        return busID;
    }

    public void setBusID(String busID) {
        this.busID = busID;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public long getStart_time() {
        return start_time;
    }

    public String getStartTimeInString(){
        long hours = TimeUnit.MILLISECONDS.toHours(start_time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(start_time) - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, minutes);
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
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

    public String getNext_station() {
        return next_station;
    }

    public void setNext_station(String next_station) {
        this.next_station = next_station;
    }

    public BusStatue getBus_statue() {
        return bus_statue;
    }

    public String getBusStatueInString(){
        switch (this.bus_statue){
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

    public void setBus_statue(BusStatue bus_statue) {
        this.bus_statue = bus_statue;
    }
}
