package org.jeecg.modules.erp.api;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.crouse.entity.EduStudent;
import org.jeecg.modules.erp.api.fallback.ErpHelloFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "jeecg-test01", fallbackFactory = ErpHelloFallback.class)
public interface EduStudentfeign {
    @GetMapping(value = "/test01/eduStudent/queryByUserId")
    public Result<EduStudent> queryByUserId(@RequestParam(name="userId",required=true) String userId);
}
