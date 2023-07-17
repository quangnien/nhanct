//package com.example.nhanct.scheduled;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import com.example.nhanct.entity.ConfirmationTokenEntity;
//import com.example.nhanct.service.Tokenservice;
//
//@EnableScheduling
//@Configuration
//@ConditionalOnProperty(name = "spring.enable.scheduling")
//public class SchedulerJob  {
//
//	@Autowired
//	private Tokenservice tokenservice;
//
//	@Scheduled(cron = "0/10 * * * * ?")
//	public void SchedulerJobSendEmailTimeLimit() {
//		long currentTime = System.currentTimeMillis();
//		List<ConfirmationTokenEntity> listDto = tokenservice.getAllListFlag0();
//		long timeBefore;
//		for (ConfirmationTokenEntity item : listDto) {
//			timeBefore = item.getCreatedDate().getTime();
//			if(currentTime - timeBefore > 60000) {
//				item.setFlag(1);
//				tokenservice.save(item);
//			}
//		}
//		System.out.println("run job SchedulerJobSendEmailTimeLimit");
//	}
//
//}
