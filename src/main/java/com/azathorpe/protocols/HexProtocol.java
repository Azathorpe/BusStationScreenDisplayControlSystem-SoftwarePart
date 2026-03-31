package com.azathorpe.protocols;

/**
 * HEX 协议</br>
 * 协议格式：</br>
 * | 头部 (Header) | 数据 (Data) | 尾部 (Tail) |</br>
 *</br>
 * 头部 (Header)：</br>
 * 固定长度 1 字节</br>
 * 0xFF 表示数据包的开始</br>
 * 仅仅只是开始而已 bro...</br>
 * 数据 (Data)：</br>
 * 定长数据 4 字节，表示实际传输的数据</br>
 * 尾部 (Tail)：</br>
 * 固定长度 1 字节</br>
 * 0xFE 表示数据包的结束</br>
 * 示例：</br>
 * | 0xFF | 0x01 0x02 0x03 0x04 | 0xFE |</br>
 *</br>
 * 解释：</br>
 * 头部 (Header)：0xFF，表示数据包的开始</br>
 * 数据 (Data)：0x01 0x02 0x03 0x04，表示实际传输的数据</br>
 * 尾部 (Tail)：0xFE，表示数据包的结束</br>
 *
 * @author Azathorpe
 * @version 1.0
 * @since 1.0
 * 更新日志:
 * </br>
 *  - 1.0 (2026年3月31日): 初始版本，包含基本的HEX协议实现
 */
public class HexProtocol {

    public static byte[][] convertBytes(byte[] buffers) {
        byte[][] byteBuffers = new byte[buffers.length / 4 + (buffers.length % 4 == 0 ? 0 : 1)][6];
        for(int i = 0; i < buffers.length; i++) {
            int row = i / 4;
            int col = i % 4 + 1;
            byteBuffers[row][0] = (byte) 0xFF; // Header
            byteBuffers[row][col] = buffers[i]; // Data
            if(col == 4 || i == buffers.length - 1) {
                byteBuffers[row][5] = (byte) 0xFE; // Tail
            }
        }
        return byteBuffers;
    }
}
