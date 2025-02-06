package com.example.demo.global.api.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ContentClient", url = "http://ai-server.com")
public interface ContentClient {

	@PostMapping("/analyze")
	Map<String, Object> analyzeKeywords(@RequestBody Map<String, Object> request);

}
