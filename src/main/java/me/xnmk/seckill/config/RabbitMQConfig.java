package me.xnmk.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author:xnmk_zhan
 * @create:2022-08-04 15:25
 * @Description: RabbitMQ Config
 */
@Configuration
public class RabbitMQConfig {

    private static final String SECKILL_QUEUE = "seckillQueue";

    private static final String SECKILL_EXCHANGE = "seckillExchange";

    @Bean
    public Queue seckillQueue() {
        return new Queue(SECKILL_QUEUE);
    }

    @Bean
    public TopicExchange seckillExchange() {
        return new TopicExchange(SECKILL_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(seckillQueue()).to(seckillExchange()).with("seckill.#");
    }
}
