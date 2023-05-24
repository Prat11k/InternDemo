package spring.all.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import spring.all.Entity.FactoryUser;

@Service
@RequiredArgsConstructor
public class RestTemplateService {

	@Autowired
	private RestTemplate restTemplate;

	Logger logger = LoggerFactory.getLogger(RestTemplateService.class);
	
	public List<FactoryUser> getFactoryUserList(String type, String token) {
		logger.info("Inside getFactoryUserList method");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.setAccept(Arrays.asList(MediaType.ALL));
		HttpEntity<List<FactoryUser>> entity = new HttpEntity<List<FactoryUser>>(headers);
		String url = "http://localhost:9090/factorycrud/getall/{type}";
		
		URI expanded = UriComponentsBuilder.fromUriString(url).buildAndExpand(type).toUri();
		logger.info(String.format("Getting List from URI : %s", expanded.toString()));
		return restTemplate.exchange(expanded, HttpMethod.GET, entity, ArrayList.class).getBody();
	}

	public String deleteFactoryUser(Integer id, String type, String token) {
		logger.info("inside method deleteFactoryUser");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.setAccept(Arrays.asList(MediaType.ALL));
		HttpEntity<FactoryUser> entity = new HttpEntity<FactoryUser>(headers);
		String url = "http://localhost:9090/factorycrud/delete/{id}/{type}";
		URI expanded = UriComponentsBuilder.fromUriString(url).buildAndExpand(id, type).toUri();
		logger.info(String.format("Getting List from URI : %s", expanded.toString()));
		restTemplate.exchange(expanded, HttpMethod.DELETE, entity, String.class);
		return "User deleted";
	}

	public String addFactoryUser(String type, String token, FactoryUser factoryUser) {
		logger.info("inside method addFactoryUser");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.setAccept(Arrays.asList(MediaType.ALL));
		HttpEntity<FactoryUser> entity = new HttpEntity<FactoryUser>(factoryUser, headers);
		String url = "http://localhost:9090/factorycrud/add/{type}";
		URI expanded = UriComponentsBuilder.fromUriString(url).buildAndExpand(type).toUri();
		logger.info(String.format("Getting List from URI : %s", expanded.toString()));
		restTemplate.postForEntity(expanded, entity, String.class);
		return "User added";

	}

	public String updateFactoryUser(String type, String token, FactoryUser factoryUser) {
		logger.info("inside method updateFactoryUser");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.setAccept(Arrays.asList(MediaType.ALL));
		HttpEntity<FactoryUser> entity = new HttpEntity<FactoryUser>(factoryUser, headers);
		String url = "http://localhost:9090/factorycrud/update/{type}";
		URI expanded = UriComponentsBuilder.fromUriString(url).buildAndExpand(type).toUri();
		logger.info(String.format("Getting List from URI : %s", expanded.toString()));
		restTemplate.exchange(expanded, HttpMethod.PUT, entity, String.class);
		return "User updated";
	}
}
