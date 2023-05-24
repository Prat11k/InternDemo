package spring.all.Entity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="Notification")
@Component
public class Notification {
	@Field("to")
	private String to;
	@Id
	private String uuid;	
	@Field("from")
	private String from;
	@Field("date")
	private Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
//	@Field("version")
//	private long version;
	@Field("status")
	private List<Status> status;
}
