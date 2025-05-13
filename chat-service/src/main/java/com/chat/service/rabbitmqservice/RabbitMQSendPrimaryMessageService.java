package com.chat.service.rabbitmqservice;

import com.chat.common.vo.WebSocketVo;

public interface RabbitMQSendPrimaryMessageService
{
    /**
     * 这个除了群聊是通用的
     *
     * @param webSocketVo 向前端推送实时消息的类
     * @return 无返回值
     */
    public void sendPrivateMessage(WebSocketVo webSocketVo);

    /**
     * 发送的是群信息
     * @param webSocketVo
     * @retyrn 无返回值
     */
    public void sendGroupMessage(WebSocketVo webSocketVo);
}
