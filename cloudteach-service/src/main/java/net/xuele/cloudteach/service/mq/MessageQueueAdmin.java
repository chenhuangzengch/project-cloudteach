package net.xuele.cloudteach.service.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MessgeQueueAdmin
 * 消息队列管理工具
 *
 * @author sunxh
 * @date 15/11/17
 */
@Component
public class MessageQueueAdmin {

    @Autowired
    private RabbitAdmin admin;

    private static final Logger logger = LoggerFactory.getLogger(MessageQueueAdmin.class);

    /**
     * 获取队列深度
     *
     * @param name 队列名
     * @return MessageCount
     */
    public int getQueueCount(final String name) {
        int depth = 0;
        AMQP.Queue.DeclareOk declareOk = admin.getRabbitTemplate().execute(
                new ChannelCallback<AMQP.Queue.DeclareOk>() {
                    @Override
                    public AMQP.Queue.DeclareOk doInRabbit(Channel channel) throws Exception {
                        return channel.queueDeclarePassive(name);
                    }
                });
        depth = declareOk.getMessageCount();

        logger.warn("queue named:{} depth={}", name, depth);
        return depth;
    }
}
