package org.jeecg.modules.erp.controller;

import org.jeecg.modules.erp.entity.ErpHelloEntity;
import org.jeecg.modules.erp.service.IErpHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/erp")
@Slf4j
public class ErpHelloController {

	@Autowired
	private IErpHelloService jeecgHelloService;

	@GetMapping(value = "/hello")
	public String sayHello() {
		log.info(" ---我被调用了--- ");
		return jeecgHelloService.hello();
	}

}
