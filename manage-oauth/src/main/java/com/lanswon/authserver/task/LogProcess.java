package com.lanswon.authserver.task;

import com.lanswon.authcore.contants.MessageQueue;
import com.lanswon.base.log.LogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description: 日志处理
 * @Author: GU-YW
 * @Date: 2020/7/13 13:30
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class LogProcess implements Runnable {


    private final RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    private void init(){
        new Thread(this).start();
    }

    @Override
    public void run() {

        synchronized (this){
            while (true){
                try {
                    if(MessageQueue.LOGQUEUE.isEmpty()){
                        Thread.sleep(500
                        );
                    }else{
                        //TODO 执行业务逻辑
                        executeTask();
                    }
                }catch (Exception ex){
                    log.error("后台进程出现异常:[{}]",ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    private void executeTask(){
        LogDto poll = MessageQueue.LOGQUEUE.poll();
        log.debug(poll.toString());
        rocketMQTemplate.convertAndSend("topic-log",poll);
        log.debug("发送信息成功");
    }

}
