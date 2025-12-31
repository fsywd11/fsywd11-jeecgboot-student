package org.jeecg.modules.demo.LostAndFound.api;
import org.jeecg.modules.demo.LostAndFound.api.fallback.ErpHelloFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "jeecg-erp", fallbackFactory = ErpHelloFallback.class)
public interface ErpHelloApi {

    /**
     * erp hello 微服务接口
     * @param
     * @return
     */
    @GetMapping(value = "/erp/hello")
    String callHello();
}
