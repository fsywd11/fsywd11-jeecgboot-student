package org.jeecg.modules.erp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.erp.entity.ErpHelloEntity;

/**
 * 测试接口
 */
public interface IErpHelloService extends IService<ErpHelloEntity> {

    String hello();

}
