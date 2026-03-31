package com.azathorpe.Utils;

import com.alibaba.fastjson2.JSON;
import com.azathorpe.Entities.Bus;
import com.azathorpe.Entities.JDBCConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

/**
 * 关于数据库的类
 * @version 1.0
 * @author Azathorpe
 * @date 2026年3月31日
 * @since 1.0
 * 更新日志:
 * </br>
 *  - 1.0 (2026年3月31日): 初始版本，包含基本的增删改查功能
 */
public class DataBaseUtils {
    private static final Logger log = LoggerFactory.getLogger(DataBaseUtils.class);
    static JDBCConfig jdbcConfig;

    /**
     * 数据库连接对象      * 单例模式，整个程序只会有一个数据库连接对象
     */
    static Connection conn;

    // 特化吧 只写Bus的，反正我只需要操作BUS就可以了 xixi

    // 增
    public static void addQuery(Bus bus) {
        String sql = "INSERT INTO bus (busID, busName, startTime, ticket, destination, nextStation, busStatue) VALUES (?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bus.getBusID());
            stmt.setString(2, bus.getBusName());
            stmt.setString(3, String.valueOf(bus.getStartTime()));
            stmt.setString(4, String.valueOf(bus.getTicket()));
            stmt.setString(5, bus.getDestination());
            stmt.setString(6, bus.getNextStation());
            stmt.setString(7, bus.getBusStatue().name());
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
    public static void getQuery(String BusID) {
        String sql = "SELECT * FROM bus WHERE busID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, BusID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                log.info("BusID: {}, BusName: {}, StartTime: {}, Ticket: {}, Destination: {}, NextStation: {}, BusStatue: {}",
                        rs.getString("busID"), rs.getString("busName"), rs.getString("startTime"), rs.getString("ticket"), rs.getString("destination"), rs.getString("nextStation"), rs.getString("busStatue"));
            }
        } catch (SQLException e) {
            log.error("Failed to execute getQuery", e);
        }
    }

    // 改
    public static void modifyQuery(Bus bus){
        String sql = "UPDATE bus SET busName = ?, startTime = ?, ticket = ?, destination = ?, nextStation = ?, busStatue = ? WHERE busID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bus.getBusName());
            stmt.setString(2, String.valueOf(bus.getStartTime()));
            stmt.setString(3, String.valueOf(bus.getTicket()));
            stmt.setString(4, bus.getDestination());
            stmt.setString(5, bus.getNextStation());
            stmt.setString(6, bus.getBusStatue().name());
            stmt.setString(7, bus.getBusID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to execute modifyQuery", e);
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
