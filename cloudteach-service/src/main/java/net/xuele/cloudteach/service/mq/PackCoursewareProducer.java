package net.xuele.cloudteach.service.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yong.zhang on 2015/7/24 0024.
 * 生产消息，消息入队
 */
@Component
public class PackCoursewareProducer {

    private static final Logger logger = LoggerFactory.getLogger(PackCoursewareProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDataToCrQueue(Object obj) {

        try {

            rabbitTemplate.convertAndSend("courseware.pack", obj);

        } catch (Exception e) {

            logger.error("发送消息到mq出现异常", e);
        }
    }

}
