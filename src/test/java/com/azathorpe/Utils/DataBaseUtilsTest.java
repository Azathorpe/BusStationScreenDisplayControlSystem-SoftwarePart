package com.azathorpe.Utils;

import com.azathorpe.Entities.Bus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseUtilsTest {

    @Test
    void addQuery() {
        DataBaseUtils.addQuery(new Bus(){{
            setBusID("2");
            setBusName("测试车");
            setStartTime(System.currentTimeMillis());
            setTicket(100);
            setDestination("测试终点");
            setNextStation("测试下一站");
            setBusStatue(com.azathorpe.Entities.BusStatue.BUS_NOT_ARRIVED);
        }});
    }

    @Test
    void removeQuery() {
        DataBaseUtils.removeQuery("1");
    }

    @Test
    void getQuery() {
        DataBaseUtils.getQuery("2");
    }
}