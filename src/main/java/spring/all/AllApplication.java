package spring.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AllApplication {
	
		public static void main(String[] args) {
		SpringApplication.run(AllApplication.class, args);
	//	Set<Thread> threads = Thread.getAllStackTraces().keySet();
		/*
		 * for (Thread t : threads) { String name = t.getName(); Thread.State state =
		 * t.getState(); int priority = t.getPriority(); String type = t.isDaemon() ?
		 * "Daemon" : "Normal"; System.out.printf("%-20s \t %s \t %d \t %s\n", name,
		 * state, priority, type); }
		 */
	}
		


}
