package com.azathorpe.protocols;

/**
 * 文本协议</br>
 * 协议格式:</br>
 * | 头部 (Header) | 数据 (Data) | 尾部 (Tail) |</br>
 *</br>
 * 头部 (Header)：</br>
 * 固定长度 1 字符</br>
 * '@' 表示数据包的开始</br>
 * 仅仅只是开始而已 bro...</br>
 * 数据 (Data)：</br>
 * 不定长数据，直到遇到尾部为止</br>
 * 尾部 (Tail)：</br>
 * 固定长度 1 字符</br>
 * '#' 表示数据包的结束</br>
 * 示例：</br>
 * | '@' | 'H' 'E' 'L' 'L' 'O' | '#' |</br>
 *</br>
 * 解释：</br>
 * 头部 (Header)：'@'，表示数据包的开始</br>
 * 数据 (Data)：'H' 'E' 'L' 'L' 'O'，表示实际传输的数据</br>
 * 尾部 (Tail)：'#'，表示数据包的结束</br>
 *
 * @author Azathorpe
 * @version 1.0
 * @since 1.0
 * 更新日志:
 * </br>
 *  - 1.0 (2026年3月31日): 初始版本，包含基本的文本协议实现
 */
public class TxtProtocol implements Protocol{

    @Override
    public String convertString(String message) {
        return "@" + message + "#";
    }
}
