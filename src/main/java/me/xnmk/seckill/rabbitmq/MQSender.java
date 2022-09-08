package me.xnmk.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:xnmk_zhan
 * @create:2022-08-04 15:28
 * @Description: 消息生产者
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg) {
        log.info("发送消息：" + msg);
        rabbitTemplate.convertAndSend("queue", msg);
    }

    public void sendSeckillMessage(String msg) {
        log.info("发送消息" + msg);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", msg);
    }
}
