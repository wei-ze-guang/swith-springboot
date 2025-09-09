package com.chat.service.listenner;

import com.chat.common.vo.WebSocketVo;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.chat.service.event.RabbitMqEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
@Component
public class RabbitMqEventListenner {
    @Autowired

    private RabbitMQSendPrimaryMessageService rabbitMQSendPrimaryMessageService;

    //   事务成功之后监听
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onFriendAdded(RabbitMqEvent event) {
        if(event.getWebSocketVo().getType() == WebSocketVo.privateType){

            rabbitMQSendPrimaryMessageService.sendPrivateMessage(event.getWebSocketVo());
        }
        else if(event.getWebSocketVo().getType() == WebSocketVo.publicType){

            rabbitMQSendPrimaryMessageService.sendGroupMessage(event.getWebSocketVo());

        }

    }
}
