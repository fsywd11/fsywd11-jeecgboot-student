package org.jeecg.modules.erp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.erp.entity.ErpHelloEntity;
import org.jeecg.modules.erp.mapper.ErpHelloMapper;
import org.jeecg.modules.erp.service.IErpHelloService;
import org.springframework.stereotype.Service;

/**
 * 测试Service
 */
@Service
public class ErpHelloServiceImpl extends ServiceImpl<ErpHelloMapper, ErpHelloEntity> implements IErpHelloService {

    @Override
    public String hello() {
        return "hello ，我是 erp 微服务节点!";
    }
}
