package com.lanswon.authserver.task;

import com.lanswon.authcore.contants.MessageQueue;
import com.lanswon.authserver.dao.TLpMapper;
import com.lanswon.authserver.domain.entity.TLp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description: 保存令牌
 * @Author: GU-YW
 * @Date: 2020/7/1 14:06
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class ActiveProcess implements Runnable {

    private final TLpMapper lpMapper;

    @PostConstruct
    private void init(){
        new Thread(this).start();
    }

    @Override
    public void run() {

        synchronized (this){
            while (true){
                try {
                    if(MessageQueue.activeQueue.isEmpty()){
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

    //TODO 新增下发token
    private void executeTask(){
        TLp poll = MessageQueue.activeQueue.poll();
        int i = lpMapper.insertSelective(poll);
        System.out.println(i);
    }

}
