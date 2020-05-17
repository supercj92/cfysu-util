package com.cfysu.util.autoconfigure;

import com.cfysu.util.listener.event.BotEvent;
import com.cfysu.util.listener.publisher.BotEventComponent;
import com.cfysu.util.listener.publisher.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author canglong
 * @Date 2020/5/17
 */
@Configuration
@ConditionalOnClass(BotEvent.class)
@EnableConfigurationProperties(ListenerProperties.class)
public class ListenerAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ListenerProperties listenerProperties;

    @Bean
    public EventPublisher eventPublisher(){
        logger.info(listenerProperties.getTestMsg());
        logger.info("BotEventComponent creating ...");
        return new BotEventComponent();
    }

}
