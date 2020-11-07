package com.lanswon.admin.notify;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/10/10 10:15
 */
@Component
@Slf4j
public class SmsNotifier extends AbstractEventNotifier {

    public SmsNotifier(InstanceRepository repository){
        super(repository);
    }
    @Override
    protected Mono<Void> doNotify(InstanceEvent instanceEvent, Instance instance) {
        return Mono.fromRunnable(() ->{
            if(instanceEvent instanceof InstanceStatusChangedEvent){
                log.debug("Instance {} ({}) is {}",instance.getRegistration().getName(),instanceEvent.getInstance(),
                        ((InstanceStatusChangedEvent)instanceEvent).getStatusInfo().getStatus());
            }else{
                log.debug("Instance {} ({}) is {}",instance.getRegistration().getName(),instanceEvent.getInstance(),
                        instanceEvent.getType());
            }
        });
    }
}
