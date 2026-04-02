package com.azathorpe.Utils;

import com.azathorpe.Entities.SerialStatue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析单片机发送过来的指令
 * @author Azathorpe
 * @version 1.0
 * @since 1.0
 * 更新日志:
 * </br>
 *  - 1.0 (2026年3月31日): 初始版本，包含基本的指令解析功能
 */
public class CommandParse {
    private static final Logger log = LoggerFactory.getLogger(CommandParse.class);
    static SerialStatue statue = SerialStatue.WAITING;
    static int queryCnt = 0;
    /**
     * 0x01 -> 单片机准备好接受下一个数据
     * 0x02 -> 单片机需要接受下一个车次单了
     * @param command
     *
     * TODO: 把解析全部移动到这里来
     */
    public static void parse(byte command){
        if(statue == SerialStatue.WAITING_CNT){
            //那么这一条信息就是关于请求车次的数量的
            queryCnt = command;
            log.info("单片机请求发送 {} 条车次信息", queryCnt);
            //回复一条请求谁的信息
            SerialPortUtils.sendMessage("WITCH");
        }
        else if(command == (byte) 0x01)
            SerialPortUtils.setIsOK(true);
        else if(command == (byte) 0x02) {
            //询问需要发送什么字段
            SerialPortUtils.sendMessage("CNT");
            statue = SerialStatue.WAITING_CNT;
        }else{
            log.info("command : {} not founded",command);
        }
    }

    public static void parse(byte[] buffer, int bytesRead) {
    }
}
