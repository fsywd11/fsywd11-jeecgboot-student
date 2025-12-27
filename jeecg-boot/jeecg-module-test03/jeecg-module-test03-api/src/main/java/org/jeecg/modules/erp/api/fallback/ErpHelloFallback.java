package org.jeecg.modules.erp.api.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.jeecg.modules.erp.api.ErpHelloApi;
import lombok.Setter;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author JeecgBoot
 */
@Slf4j
@Component
public class ErpHelloFallback implements FallbackFactory<ErpHelloApi> {
    @Setter
    private Throwable cause;

    @Override
    public ErpHelloApi create(Throwable throwable) {
        log.error("微服务接口调用失败： {}", cause);
        return null;
    }

}
