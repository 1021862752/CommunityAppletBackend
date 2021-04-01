package org.jeecg.modules.demo.crontab;

import org.jeecg.modules.demo.crontab.mapper.CrontabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshOrderTask {

    @Autowired
    private CrontabMapper crontabMapper;

    /**
     * 定时修改超时订单状态
     */
    @Scheduled(cron = "0/10 * * * * ? ")
    public void RefreshOrder(){
        crontabMapper.refreshOrderState();
        crontabMapper.refreshBorrowOrderState();
    }
}
