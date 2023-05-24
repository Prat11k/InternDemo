package spring.all.Controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

	@RunWith(SpringRunner.class)
	@SpringBootTest
	@AutoConfigureMockMvc
	public class EmailControllerTest {

	    @Autowired
	    private MockMvc mockMvc;
	    
	    @MockBean
	    private JavaMailSender javaMailSender;

	    @Test
	    public void sendEmailTest() throws Exception {
	        String to = "test@example.com";
	        String subject = "Test Subject";
	        String message = "Test Message";
	        
	        SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(to);
	        mailMessage.setSubject(subject);
	        mailMessage.setText(message);
	        
	        Mockito.doNothing().when(javaMailSender).send(mailMessage);
	        
	        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/mail")
	            .param("to", to)
	            .param("subject", subject)
	            .param("message", message))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andReturn();
	          
	        Mockito.verify(javaMailSender, Mockito.times(1)).send(mailMessage);
	        
	        String responseBody = result.getResponse().getContentAsString();
	        assertEquals("Email sent successfully!", responseBody);
	    }
	}
