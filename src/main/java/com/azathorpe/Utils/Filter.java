package com.azathorpe.Utils;

import com.azathorpe.Entities.Bus;
import com.azathorpe.Entities.BusStatue;

public class Filter {
    public static boolean enableFilter = false;

    public static int ticketLimit = -1;
    public static String bus_Name = "NO";
    public static String next_Station = "NO";
    public static String destination = "NO";
    public static BusStatue statue = null;

    public static boolean match(Bus bus){
        if(!enableFilter) return true;
        if(ticketLimit != -1 && bus.getTicket() > ticketLimit) return false;
        if(!bus_Name.equals("NO") && !bus.getBus_name().equals(bus_Name)) return false;
        if(!next_Station.equals("NO") && !bus.getNext_station().equals(next_Station)) return false;
        if(!destination.equals("NO") && !bus.getDestination().equals(destination)) return false;
        return true;
    }

    public static void setVal(String tl, String name, String st, String des, String stat){
        try {
            ticketLimit = Integer.parseInt(tl);
        } catch (NumberFormatException e) {
            ticketLimit = -1;
        }
        bus_Name = name.isEmpty() ? "NO" : name;
        next_Station = st.isEmpty() ? "NO" : st;
        destination = des.isEmpty() ? "NO" : des;
        statue = BusStatue.valueOf(stat);
    }

}
