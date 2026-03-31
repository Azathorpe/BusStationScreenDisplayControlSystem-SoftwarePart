package com.azathorpe.protocols;

/**
 * 协议接口
 * 定义了协议的基本结构和方法，所有具体协议类都需要实现这个接口
 * 包含两个默认方法 convertString 和 convertBytes，分别用于将字符串和字节数组转换为协议格式
 * 具体协议类可以根据需要重写这些方法来实现不同的协议格式
 *
 * @author Azathorpe
 * @version 1.0
 * @since 1.0
 * 更新日志:
 * </br>
 *  - 1.0 (2026年3月31日): 初始版本，定义了协议接口和默认方法
 */
public interface Protocol {
    default String convertString(String message){
        return message;
    };
    default byte[] convertBytes(byte[] buffers){
        return buffers;
    };
}
