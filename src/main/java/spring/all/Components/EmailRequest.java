package spring.all.Components;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class EmailRequest {
	  private List<String> emailAddresses;
      private String subject;
      private String message;
}