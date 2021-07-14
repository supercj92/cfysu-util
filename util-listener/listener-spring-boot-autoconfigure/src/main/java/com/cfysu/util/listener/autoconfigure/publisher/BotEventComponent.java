package com.cfysu.util.listener.autoconfigure.publisher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cfysu.util.listener.core.domain.EventResult;
import com.cfysu.util.listener.core.domain.ListenerRunningInfo;
import com.cfysu.util.listener.core.domain.ListenerRunningInfos;
import com.cfysu.util.listener.core.event.BotEvent;
import com.cfysu.util.listener.core.exception.ListenerException;
import com.cfysu.util.listener.core.listener.BotListener;
import com.cfysu.util.listener.core.listener.BotListenerWrapper;
import com.cfysu.util.listener.core.listener.ListenerRegister;
import com.cfysu.util.listener.core.processor.Processor;
import com.cfysu.util.listener.core.publisher.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.ResolvableType;

/**
 * @Author canglong
 * @Date 2019/11/27
 */
@Slf4j
public class BotEventComponent implements EventPublisher, ListenerRegister, InitializingBean {

    public static Set<BotListenerWrapper> listenerList = new HashSet<>();

    private Map<String, List<BotListenerWrapper>> eventAssignListenerCache = new HashMap<>();

    private Processor processor;

    private Processor simpleProcessor;

    @Override
    public EventResult publishEvent(BotEvent event) {
        EventResult eventResult = new EventResult();
        //根据声明的事件类型对监听器进行分类
        List<BotListenerWrapper> eventAssignListeners = retrieveListeners(event);
        if (eventAssignListeners == null || eventAssignListeners.isEmpty()) {
            return eventResult;
        }

        ListenerRunningInfos listenerRunningInfos = new ListenerRunningInfos();
        for (BotListenerWrapper botListenerWrapper : eventAssignListeners) {
            BotListener botListener = botListenerWrapper.getBotListener();
            long start = System.currentTimeMillis();
            String listenerName = botListener.getClass().getSimpleName();
            ListenerRunningInfo listenerRunningInfo = new ListenerRunningInfo(listenerName);
            listenerRunningInfos.addInfo(listenerRunningInfo);
            try {
                if (!botListener.support(event)) {
                    continue;
                }
                listenerRunningInfo.setSupport(true);
                BotEvent botEvent = executeListener(botListenerWrapper, event, listenerName);
                listenerRunningInfo.setRt((System.currentTimeMillis() - start));
                if (botEvent == null) {
                    break;
                }
            } catch (Throwable e) {
                log.error("invokeListener error, listener:" + botListener.getClass().getName(), e);
                listenerRunningInfo.setException(true);
            }
        }
        eventResult.setListenerRunningInfos(listenerRunningInfos);
        return eventResult;
    }

    @Override
    public void registerListener(BotListenerWrapper wrapper) {
        listenerList.add(wrapper);
    }

    @Override
    public void afterPropertiesSet() {

    }

    private List<BotListenerWrapper> retrieveListeners(BotEvent botEvent) {
        String eventClazzName = botEvent.getClass().getSimpleName();
        List<BotListenerWrapper> eventAssignListenersFromCache = eventAssignListenerCache.get(eventClazzName);
        if (eventAssignListenersFromCache != null) {
            return eventAssignListenersFromCache;
        }
        List<BotListenerWrapper> eventAssignListeners = new ArrayList<>();
        for (BotListenerWrapper wrapper : listenerList) {
            if (supportsEvent(wrapper.getBotListener(), botEvent)) {
                eventAssignListeners.add(wrapper);
            }
        }
        //排序
        eventAssignListeners.sort(Comparator.comparingInt(BotListenerWrapper::getOrder));
        eventAssignListenerCache.put(eventClazzName, eventAssignListeners);

        return eventAssignListeners;
    }

    private boolean supportsEvent(BotListener botListener, BotEvent botEvent) {
        //listener
        ResolvableType declaredEventType = resolveDeclaredEventType(botListener.getClass());

        //event
        ResolvableType currentEvent = ResolvableType.forClass(botEvent.getClass());
        return (declaredEventType == null || declaredEventType.isAssignableFrom(currentEvent));
    }

    private ResolvableType resolveDeclaredEventType(Class<?> listenerType) {
        ResolvableType resolvableType = ResolvableType.forClass(listenerType).as(BotListener.class);
        if (resolvableType == null || !resolvableType.hasGenerics()) {
            return null;
        }
        return resolvableType.getGeneric();
    }

    private BotEvent executeListener(BotListenerWrapper botListenerWrapper, BotEvent botEvent, String listenerName)
        throws Exception {
        Processor processor = getProcessorByListener(botListenerWrapper);
        try {
            return processor.process(() -> botListenerWrapper.getBotListener().onEvent(botEvent),
                botListenerWrapper.getTimeout()
                , listenerName);
        } catch (ListenerException e) {
            return botEvent;
        }
    }

    private Processor getProcessorByListener(BotListenerWrapper botListenerWrapper) {
        long timeout = botListenerWrapper.getTimeout();
        Processor processor = simpleProcessor;
        if (timeout > 0) {
            processor = this.processor;
        }
        return processor;
    }
}
