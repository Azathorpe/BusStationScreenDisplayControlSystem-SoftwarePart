package com.azathorpe.Entities;

public enum SerialStatue {
    WAITING, // 等待单片机准备好接受数据
    SENDING, // 正在发送数据
    FINISH,  // 数据发送完成
    ERROR,   // 发送数据过程中发生错误
    WAITING_CNT//等待发送数据数量
}
