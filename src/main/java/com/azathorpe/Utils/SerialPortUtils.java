package com.azathorpe.Utils;

import com.azathorpe.app;
import com.azathorpe.protocols.HexProtocol;
import com.azathorpe.protocols.TxtProtocol;
import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * 关于串口的类
 * @version 1.0
 * @author Azathorpe
 * @date 2026年3月31日
 * @since 1.0
 * 更新日志:
 * </br>
 *  - 1.0 (2026年3月31日): 初始版本，包含基本的串口操作功能
 */
public class SerialPortUtils {
    private static final Logger log = LoggerFactory.getLogger(SerialPortUtils.class);

    static SerialPort port;
    static boolean listening = false;

    /**
     * 标志位，表示单片机是否准备好接受下一个数据
     */
    private static boolean isOK = false;

    /**
     * 初始化串口
     */
    public static void serialPortInitialization(){
        // 查看所有串口，然后找到CH340
        SerialPort[] ports = SerialPort.getCommPorts();
        log.info("检测到 {} 个串口设备:", ports.length);

        for(SerialPort p : ports){
            if(p.getDescriptivePortName().contains("CH340")){
                port = p;
                log.info("找到CH340串口: {}", p.getSystemPortName());
                break;
            }
        }

        if(port == null){
            log.error("未找到CH340串口设备，请检查连接");
            return;
        }

        //设置串口参数(波特率等等)
        try {
            // 设置标准UART参数
            port.setComPortParameters(
                    9600,  // 波特率 (推荐STM32常用速率)
                    8,       // 数据位
                    1,       // 停止位
                    SerialPort.NO_PARITY  // 无校验
            );

            // 设置超时时间
            port.setComPortTimeouts(
                    SerialPort.TIMEOUT_NONBLOCKING,
                    100,  // 读取超时(ms)
                    100   // 写入超时(ms)
            );

            // 打开串口连接
            if (port.openPort()) {
                log.info("串口 {} 已成功打开", port.getSystemPortName());
            } else {
                throw new RuntimeException("无法打开串口: " + port.getSystemPortName());
            }
        } catch (Exception e) {
            log.error("初始化串口失败", e);
            System.exit(-1);
        }
    }

    /**
     * 根据协议 发送数据到单片机
     * 详情参阅 Protocol.MD 文件
     * @see com.azathorpe.protocols.TxtProtocol 协议
     */
    public static void sendMessage(String message){
        if(port == null){
            log.error("串口未初始化，无法发送消息");
            System.exit(-1);
        }

        try {
            String protocolMessage = TxtProtocol.convertString(message);
            port.openPort();
            port.getOutputStream().write(protocolMessage.getBytes());
            port.getOutputStream().flush();
            log.info("成功发送消息: {}", protocolMessage);
        } catch (Exception e) {
            log.error("发送消息失败", e);
        } finally {
            port.closePort();
        }
    }

    /**
     * 根据协议 发送数据到单片机
     * 详情参阅 Protocol.MD 文件
     * @see com.azathorpe.protocols.HexProtocol 协议
     */
    public static void sendMessage(byte[] buffers){
        if(port == null){
            log.error("串口未初始化，无法发送消息");
            System.exit(-1);
        }

        try {
            byte[][] byteBuffers = HexProtocol.convertBytes(buffers);
            port.openPort();
            for(byte[] bytes : byteBuffers) {
                isOK = false;
                port.getOutputStream().write(bytes);
                port.getOutputStream().flush();
                log.info("成功发送消息: {}", bytes);
                while (!isOK){
                    // 等待单片机准备好接受下一个数据
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            log.error("发送消息失败", e);
        } finally {
            port.closePort();
        }
    }

    /**
     * 打开监听线程
     * @return 如果成功启动监听线程返回true，否则返回false
     */
    public static boolean startListenMessageThread(){
        if(!listening){
            // 启动读取线程监听来自单片机的数据
            Thread readerThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                while (true) {
                    try {
                        if (port.bytesAvailable() > 0) {
                            int bytesRead = port.readBytes(buffer, buffer.length);

                            if(bytesRead == 1)
                                CommandParse.parse(buffer[0]);
                            else if(buffer[0] == (byte) 0xFF){
                                ArrayList<Byte> bytes = new ArrayList<>();
                                for(byte b : buffer){
                                    if(b == (byte) 0xFE){
                                        break;
                                    }
                                    bytes.add(b);
                                }
                                byte[] dataBytes = new byte[bytes.size() - 2];
                                for(int i = 1; i < bytes.size() - 1; i++){
                                    dataBytes[i - 1] = bytes.get(i);
                                }
                                log.info("接收数据(HEX): {}", dataBytes);
                            }else{
                                String receivedData = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                                log.info("接收数据(TXT): {}", receivedData);
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("读取数据时发生错误: " + e.getMessage());
                        break;
                    }
                }
            });
            app.threadPool.add(readerThread);
            readerThread.setDaemon(true);
            readerThread.start();
            log.info("监听线程已启动 线程名: {}",readerThread.getName());
            return true;
        }
        return false;
    }

    public static boolean isIsOK() {
        return isOK;
    }

    public static void setIsOK(boolean isOK) {
        SerialPortUtils.isOK = isOK;
    }
}
