package com.azathorpe.Entities;

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

    public void setBusStatue(BusStatue busStatue) {
        this.busStatue = busStatue;
    }

}
