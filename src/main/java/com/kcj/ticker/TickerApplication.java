package com.kcj.ticker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class TickerApplication {

	private static final Logger log = LoggerFactory.getLogger(TickerApplication.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	public static void main(String[] args) {
		SpringApplication.run(TickerApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	@Scheduled(fixedRate = 30000)
	public void runEveryThirtySeconds() throws JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?market_code=nex", String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
		JsonNode tickerNode = rootNode.get(0).get("ticker");
		String ask = tickerNode.get("ask").asText();
		String bid = tickerNode.get("bid").asText();
		Ticker ticker = new Ticker(ask, bid);
		log.info("The time is now {}", dateFormat.format(new Date()));
		log.info("ticker="+ ticker.toString());
	}


	@Bean
	public CommandLineRunner run() throws Exception{
		return args -> runEveryThirtySeconds();
	}
}
