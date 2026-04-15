package com.azathorpe.Utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.Entities.Bus;
import com.azathorpe.Entities.BusStatue;
import com.azathorpe.Entities.JDBCConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * 关于数据库的类
 *
 * @author Azathorpe
 * @version 1.0
 * @date 2026年3月31日
 * @since 1.0
 * 更新日志:
 * </br>
 * - 1.0 (2026年3月31日): 初始版本，包含基本的增删改查功能
 */
public class DataBaseUtils {
    private static final Logger log = LoggerFactory.getLogger(DataBaseUtils.class);
    static JDBCConfig jdbcConfig;

    /**
     * 数据库连接对象      * 单例模式，整个程序只会有一个数据库连接对象
     */
    static Connection conn;

    // 特化吧 只写Bus的，反正我只需要操作BUS就可以了 xixi

    /**
     * 当busid为-1 采用自增主键
     * @param bus
     */
    // 增
    public static void addQuery(Bus bus) {
        String sql = "";
        if (bus.getBusID().equals("-1"))
            sql = "INSERT INTO bus (bus_name, start_time, ticket, destination, next_station, bus_statue) VALUES (?,?,?,?,?,?);";
        else
            sql = "INSERT INTO bus (busID, bus_name, start_time, ticket, destination, next_station, bus_statue) VALUES (?,?,?,?,?,?,?);";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (!bus.getBusID().equals("-1")) {
                stmt.setString(1, bus.getBusID());
                stmt.setString(2, bus.getBus_name());
                stmt.setString(3, String.valueOf(bus.getStart_time()));
                stmt.setString(4, String.valueOf(bus.getTicket()));
                stmt.setString(5, bus.getDestination());
                stmt.setString(6, bus.getNext_station());
                stmt.setString(7, String.valueOf(bus.getBus_statue().ordinal()));
            }else{
                stmt.setString(1, bus.getBus_name());
                stmt.setString(2, String.valueOf(bus.getStart_time()));
                stmt.setString(3, String.valueOf(bus.getTicket()));
                stmt.setString(4, bus.getDestination());
                stmt.setString(5, bus.getNext_station());
                stmt.setString(6, String.valueOf(bus.getBus_statue().ordinal()));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to execute addQuery", e);
        }
    }

    // 删
    public static void removeQuery(Bus bus) {
        removeQuery(bus.getBusID());
    }

    // 删
    public static void removeQuery(String BusID) {
        String sql = "DELETE FROM bus WHERE busID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, BusID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to execute removeQuery", e);
        }
    }

    // 查
    public static String getQuery(String BusID) {
        String sql = "SELECT * FROM bus WHERE busID = ?;";
        ArrayList<Bus> res = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, BusID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                log.info("BusID: {}, bus_name: {}, start_time: {}, Ticket: {}, Destination: {}, next_station: {}, bus_statue: {}",
                        rs.getString("busID"), rs.getString("bus_name"), rs.getString("start_time"), rs.getString("ticket"), rs.getString("destination"), rs.getString("next_station"), rs.getString("bus_statue"));
                res.add(Bus.getInstance(
                        rs.getString("busID"),
                        rs.getString("bus_name"),
                        Long.parseLong(rs.getString("start_time")),
                        rs.getString("ticket"),
                        rs.getString("destination"),
                        rs.getString("next_station"),
                        BusStatue.values()[Integer.parseInt((rs.getString("bus_statue")))]));
            }
        } catch (SQLException e) {
            log.error("Failed to execute getQuery", e);
        }
        return JSON.toJSONString(res);
    }

    //查
    public static String getQuery() {
        String sql = "SELECT * FROM bus";
        ArrayList<Bus> res = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                log.info("BusID: {}, bus_name: {}, start_time: {}, Ticket: {}, Destination: {}, next_station: {}, bus_statue: {}",
                        rs.getString("busID"), rs.getString("bus_name"), rs.getString("start_time"), rs.getString("ticket"), rs.getString("destination"), rs.getString("next_station"), rs.getString("bus_statue"));
                res.add(Bus.getInstance(
                        rs.getString("busID"),
                        rs.getString("bus_name"),
                        Long.parseLong(rs.getString("start_time")),
                        rs.getString("ticket"),
                        rs.getString("destination"),
                        rs.getString("next_station"),
                        BusStatue.values()[Integer.parseInt((rs.getString("bus_statue")))]));
            }
        } catch (SQLException e) {
            log.error("Failed to execute getQuery", e);
        }
        return JSON.toJSONString(res);
    }

    // 改
    public static void modifyQuery(Bus bus) {
        String sql = "UPDATE bus SET bus_name = ?, start_time = ?, ticket = ?, destination = ?, next_station = ?, bus_statue = ? WHERE busID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bus.getBus_name());
            stmt.setString(2, String.valueOf(bus.getStart_time()));
            stmt.setString(3, String.valueOf(bus.getTicket()));
            stmt.setString(4, bus.getDestination());
            stmt.setString(5, bus.getNext_station());
            stmt.setString(6, bus.getBus_statue().name());
            stmt.setString(7, bus.getBusID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to execute modifyQuery", e);
        }
    }

    /**
     * @deprecated 重置所有BUS的状态为未到达，主要用于程序启动时重置状态
     */
    public static void resetAllBus() {
        String sql = "UPDATE bus SET bus_statue = ? WHERE busID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, BusStatue.BUS_NOT_ARRIVED.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            //加载配置文件
            jdbcConfig = JSON.parseObject(FileUtils.readResources("JDBC.config"), JDBCConfig.class);
            //加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //创建连接
            conn = DriverManager.getConnection(jdbcConfig.getURL(), jdbcConfig.getUSER(), jdbcConfig.getPASSWORD());
        } catch (SQLException e) {
            log.error("Failed to connect to database", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            log.error("Failed to load JDBC driver", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Failed to read JDBC config file", e);
            throw new RuntimeException(e);
        }
    }

    public DataBaseUtils() {
    }
}
