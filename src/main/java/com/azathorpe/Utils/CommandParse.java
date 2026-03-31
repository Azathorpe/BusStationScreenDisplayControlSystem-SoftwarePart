package com.azathorpe.Utils;

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
    /**
     * 0x01 -> 单片机准备好接受下一个数据
     * 0x02 -> 单片机需要接受下一个车次单了
     * @param command
     */
    public static void parse(byte command){
        if(command == (byte) 0x01)
            SerialPortUtils.setIsOK(true);
        else if(command == (byte) 0x02)
            SerialPortUtils.sendMessage("NEXT");
    }
}
