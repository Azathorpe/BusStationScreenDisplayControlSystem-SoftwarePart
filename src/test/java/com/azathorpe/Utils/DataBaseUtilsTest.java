package com.azathorpe.Utils;

import com.azathorpe.Entities.Bus;
import org.junit.jupiter.api.Test;

class DataBaseUtilsTest {

    @Test
    void addQuery() {
        DataBaseUtils.addQuery(new Bus(){{
            setBusID("2");
            setBus_name("测试车");
            setStart_time(System.currentTimeMillis());
            setTicket(100);
            setDestination("测试终点");
            setNext_station("测试下一站");
            setBus_statue(com.azathorpe.Entities.BusStatue.BUS_NOT_ARRIVED);
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