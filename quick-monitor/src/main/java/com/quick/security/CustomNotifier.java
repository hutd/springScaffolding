package com.quick.security;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.domain.values.StatusInfo;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;



@Slf4j
@Component
public class CustomNotifier extends AbstractStatusChangeNotifier {


    @Value("#{'${quick.monitor-phones}'.split(',')}")
    private List<String> phones;


    public CustomNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance)  {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                StatusInfo statusInfo = ((InstanceStatusChangedEvent) event).getStatusInfo();
                if (!statusInfo.isUp()) {
                    // 服务名称
                    String appName = instance.getRegistration().getName();
                    // 实例名称(在eureka中注册的唯一id)
                    String instanceId = instance.getId().toString();
                    // 事件发生的时间
                    String time = event.getTimestamp().plusMillis(TimeUnit.HOURS.toMillis(8)).toString();
                    // 服务器的ip
                    URL url = null;
                    try {
                        assert instance.getRegistration().getServiceUrl() != null;
                        url = new URL(instance.getRegistration().getServiceUrl());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    // 封装成自定义事件对象
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("服务名：".concat(appName).concat("(").concat(instanceId).concat(")"));
                    stringBuffer.append("\n地址：".concat(url.getHost())+":"+url.getPort());
                    stringBuffer.append("\n最后状态：".concat(statusInfo.getStatus()));
                    stringBuffer.append("\n时间：".concat(time));
                    // 事件的全部信息
                    log.info("微服务节点掉线:[{}]", stringBuffer.toString());
                    // todo  发送短信 并且存储到数据库

                }
            }
        });
    }





}
