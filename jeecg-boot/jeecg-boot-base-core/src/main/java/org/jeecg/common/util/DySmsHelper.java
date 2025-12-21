//package org.jeecg.common.util;
//
//import cn.hutool.core.collection.CollectionUtil;
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import org.apache.commons.lang3.StringUtils;
//import org.jeecg.common.constant.CommonConstant;
//import org.jeecg.common.constant.enums.DySmsEnum;
//import org.jeecg.config.JeecgBaseConfig;
//import org.jeecg.config.JeecgSmsTemplateConfig;
//import org.jeecg.config.StaticConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
///**
// * Created on 17/6/7.
// * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
// * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
// * 工程依赖了2个jar包(存放在工程的libs目录下)
// * 1:aliyun-java-sdk-core.jar
// * 2:aliyun-java-sdk-dysmsapi.jar
// *
// * 备注:Demo工程编码采用UTF-8
// * 国际短信发送请勿参照此DEMO
// * @author: jeecg-boot
// */
//public class DySmsHelper {
//
//	private final static Logger logger=LoggerFactory.getLogger(DySmsHelper.class);
//
//    /**产品名称:云通信短信API产品,开发者无需替换*/
//    static final String PRODUCT = "Dysmsapi";
//    /**产品域名,开发者无需替换*/
//    static final String DOMAIN = "dysmsapi.aliyuncs.com";
//
//    /**TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)*/
//    static  String accessKeyId;
//    static  String accessKeySecret;
//
//    public static void setAccessKeyId(String accessKeyId) {
//        DySmsHelper.accessKeyId = accessKeyId;
//    }
//
//    public static void setAccessKeySecret(String accessKeySecret) {
//        DySmsHelper.accessKeySecret = accessKeySecret;
//    }
//
//    public static String getAccessKeyId() {
//        return accessKeyId;
//    }
//
//    public static String getAccessKeySecret() {
//        return accessKeySecret;
//    }
//
//
//    public static boolean sendSms(String phone, JSONObject templateParamJson, DySmsEnum dySmsEnum) throws ClientException {
//        JeecgBaseConfig config = SpringContextUtils.getBean(JeecgBaseConfig.class);
//        String smsSendType = config.getSmsSendType();
//        if(oConvertUtils.isNotEmpty(smsSendType) && CommonConstant.SMS_SEND_TYPE_TENCENT.equals(smsSendType)){
//            return TencentSms.sendTencentSms(phone, templateParamJson, config.getTencent(), dySmsEnum);
//        }
//        //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//
//        // 代码逻辑说明: 配置类数据获取
//        StaticConfig staticConfig = SpringContextUtils.getBean(StaticConfig.class);
//        //logger.info("阿里大鱼短信秘钥 accessKeyId：" + staticConfig.getAccessKeyId());
//        //logger.info("阿里大鱼短信秘钥 accessKeySecret："+ staticConfig.getAccessKeySecret());
//        setAccessKeyId(staticConfig.getAccessKeyId());
//        setAccessKeySecret(staticConfig.getAccessKeySecret());
//
//        //初始化acsClient,暂不支持region化
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//
//        //验证json参数
//        validateParam(templateParamJson,dySmsEnum);
//
//        // 代码逻辑说明: 【QQYUN-9422】短信模板管理，阿里云---
//        String templateCode = dySmsEnum.getTemplateCode();
//        JeecgSmsTemplateConfig baseConfig = SpringContextUtils.getBean(JeecgSmsTemplateConfig.class);
//        if(baseConfig != null && CollectionUtil.isNotEmpty(baseConfig.getTemplateCode())){
//            Map<String, String> smsTemplate = baseConfig.getTemplateCode();
//            if(smsTemplate.containsKey(templateCode) && StringUtils.isNotEmpty(smsTemplate.get(templateCode))){
//                templateCode = smsTemplate.get(templateCode);
//                logger.info("yml中读取短信code{}",templateCode);
//            }
//        }
//        //签名名称
//        String signName = dySmsEnum.getSignName();
//        if(baseConfig != null && StringUtils.isNotEmpty(baseConfig.getSignature())){
//            logger.info("yml中读取签名名称{}",baseConfig.getSignature());
//            signName = baseConfig.getSignature();
//        }
//
//        //组装请求对象-具体描述见控制台-文档部分内容
//        SendSmsRequest request = new SendSmsRequest();
//        //必填:待发送手机号
//        request.setPhoneNumbers(phone);
//        //必填:短信签名-可在短信控制台中找到
//        request.setSignName(signName);
//        //必填:短信模板-可在短信控制台中找到
//        request.setTemplateCode(templateCode);
//        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam(templateParamJson.toJSONString());
//
//        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//        //request.setSmsUpExtendCode("90997");
//
//        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        //request.setOutId("yourOutId");
//
//        boolean result = false;
//
//        //hint 此处可能会抛出异常，注意catch
//        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//        logger.info("短信接口返回的数据----------------");
//        logger.info("{Code:" + sendSmsResponse.getCode()+",Message:" + sendSmsResponse.getMessage()+",RequestId:"+ sendSmsResponse.getRequestId()+",BizId:"+sendSmsResponse.getBizId()+"}");
//        String ok = "OK";
//        if (ok.equals(sendSmsResponse.getCode())) {
//            result = true;
//        }
//        return result;
//
//    }
//
//    private static void validateParam(JSONObject templateParamJson,DySmsEnum dySmsEnum) {
//    	String keys = dySmsEnum.getKeys();
//    	String [] keyArr = keys.split(",");
//    	for(String item :keyArr) {
//    		if(!templateParamJson.containsKey(item)) {
//    			throw new RuntimeException("模板缺少参数："+item);
//    		}
//    	}
//    }
//
//
////    public static void main(String[] args) throws ClientException, InterruptedException {
////    	JSONObject obj = new JSONObject();
////    	obj.put("code", "1234");
////    	sendSms("13800138000", obj, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
////    }
//}




package org.jeecg.common.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.enums.DySmsEnum;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.config.JeecgSmsTemplateConfig;
import org.jeecg.config.StaticConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 * @author: jeecg-boot
 */
public class DySmsHelper {

    private final static Logger logger=LoggerFactory.getLogger(DySmsHelper.class);

    /**产品名称:云通信短信API产品,开发者无需替换*/
    static final String PRODUCT = "Dysmsapi";
    /**产品域名,开发者无需替换*/
    static final String DOMAIN = "dysmsapi.aliyuncs.com";

    /**TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)*/
    static  String accessKeyId;
    static  String accessKeySecret;

    // 【新增】本地测试开关：true=控制台输出验证码（跳过阿里云），false=走阿里云短信接口
    private static final boolean LOCAL_TEST_MODE = true;

    public static void setAccessKeyId(String accessKeyId) {
        DySmsHelper.accessKeyId = accessKeyId;
    }

    public static void setAccessKeySecret(String accessKeySecret) {
        DySmsHelper.accessKeySecret = accessKeySecret;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }


    public static boolean sendSms(String phone, JSONObject templateParamJson, DySmsEnum dySmsEnum) throws ClientException {
        JeecgBaseConfig config = SpringContextUtils.getBean(JeecgBaseConfig.class);
        String smsSendType = config.getSmsSendType();
        if(oConvertUtils.isNotEmpty(smsSendType) && CommonConstant.SMS_SEND_TYPE_TENCENT.equals(smsSendType)){
            return TencentSms.sendTencentSms(phone, templateParamJson, config.getTencent(), dySmsEnum);
        }
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 代码逻辑说明: 配置类数据获取
        StaticConfig staticConfig = SpringContextUtils.getBean(StaticConfig.class);
        //logger.info("阿里大鱼短信秘钥 accessKeyId：" + staticConfig.getAccessKeyId());
        //logger.info("阿里大鱼短信秘钥 accessKeySecret："+ staticConfig.getAccessKeySecret());
        setAccessKeyId(staticConfig.getAccessKeyId());
        setAccessKeySecret(staticConfig.getAccessKeySecret());

        //初始化acsClient,暂不支持region化（保留初始化，避免后续恢复时需要重新写）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //验证json参数
        validateParam(templateParamJson,dySmsEnum);

        // 代码逻辑说明: 【QQYUN-9422】短信模板管理，阿里云---
        String templateCode = dySmsEnum.getTemplateCode();
        JeecgSmsTemplateConfig baseConfig = SpringContextUtils.getBean(JeecgSmsTemplateConfig.class);
        if(baseConfig != null && CollectionUtil.isNotEmpty(baseConfig.getTemplateCode())){
            Map<String, String> smsTemplate = baseConfig.getTemplateCode();
            if(smsTemplate.containsKey(templateCode) && StringUtils.isNotEmpty(smsTemplate.get(templateCode))){
                templateCode = smsTemplate.get(templateCode);
                logger.info("yml中读取短信code{}",templateCode);
            }
        }
        //签名名称
        String signName = dySmsEnum.getSignName();
        if(baseConfig != null && StringUtils.isNotEmpty(baseConfig.getSignature())){
            logger.info("yml中读取签名名称{}",baseConfig.getSignature());
            signName = baseConfig.getSignature();
        }

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParamJson.toJSONString());

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        boolean result = false;

        // ======================================
        // 核心修改：本地测试模式，跳过阿里云接口，控制台输出验证码
        // ======================================
        if (LOCAL_TEST_MODE) {
            // 1. 获取或生成验证码（优先从参数中取，没有则自动生成6位数字）
            String verifyCode = templateParamJson.getString("code");
            if (StringUtils.isEmpty(verifyCode)) {
                verifyCode = RandomStringUtils.randomNumeric(6);
                // 把生成的验证码回写到参数中，方便后续Redis存储（保持原有逻辑）
                templateParamJson.put("code", verifyCode);
                // 同步更新请求的模板参数（可选，不影响，因为不走阿里云）
                request.setTemplateParam(templateParamJson.toJSONString());
            }

            // 2. 控制台打印关键信息（核心需求）
            logger.info("========================================");
            logger.info("【本地测试模式：跳过阿里云短信，控制台输出验证码】");
            logger.info("手机号：{}", phone);
            logger.info("验证码：{}", verifyCode);
            logger.info("模板类型：{}", templateCode);
            logger.info("短信签名：{}", signName);
            logger.info("模板参数：{}", templateParamJson.toJSONString());
            logger.info("========================================");

            // 3. 模拟阿里云返回成功响应
            SendSmsResponse mockResponse = new SendSmsResponse();
            mockResponse.setCode("OK");
            mockResponse.setMessage("短信发送成功（本地测试模式）");
            mockResponse.setRequestId("LOCAL-" + System.currentTimeMillis());
            mockResponse.setBizId("LOCAL-BIZ-" + System.currentTimeMillis());

            // 打印模拟返回数据（保持原有日志格式）
            logger.info("短信接口返回的数据----------------");
            logger.info("{Code:" + mockResponse.getCode()+",Message:" + mockResponse.getMessage()+",RequestId:"+ mockResponse.getRequestId()+",BizId:"+mockResponse.getBizId()+"}");

            // 4. 设置结果为成功
            result = true;
        } else {
            // 原有逻辑：调用阿里云短信接口（保留，后续可通过开关恢复）
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            logger.info("短信接口返回的数据----------------");
            logger.info("{Code:" + sendSmsResponse.getCode()+",Message:" + sendSmsResponse.getMessage()+",RequestId:"+ sendSmsResponse.getRequestId()+",BizId:"+sendSmsResponse.getBizId()+"}");
            String ok = "OK";
            if (ok.equals(sendSmsResponse.getCode())) {
                result = true;
            }
        }

        return result;

    }

    private static void validateParam(JSONObject templateParamJson,DySmsEnum dySmsEnum) {
        String keys = dySmsEnum.getKeys();
        String [] keyArr = keys.split(",");
        for(String item :keyArr) {
            if(!templateParamJson.containsKey(item)) {
                throw new RuntimeException("模板缺少参数："+item);
            }
        }
    }


//    public static void main(String[] args) throws ClientException, InterruptedException {
//    	JSONObject obj = new JSONObject();
//    	obj.put("code", "1234");
//    	sendSms("13800138000", obj, DySmsEnum.FORGET_PASSWORD_TEMPLATE_CODE);
//    }
}
