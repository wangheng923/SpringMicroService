package com.hyc.wh.ServiceConsumerRibbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ConsumerController {

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
	}

	@Autowired
	private ComputeService computeService;

	@RequestMapping(value = "/addBreak", method = RequestMethod.GET)
	public String addBreak() {
		return computeService.addService();
	}

	@HystrixCommand(fallbackMethod = "addServiceFallback")
	@RequestMapping(value = "/addBreakDirect", method = RequestMethod.GET)
	public String addService() {
		return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
	}

	public String addServiceFallback() {
		return "errordirect";
	}
}
