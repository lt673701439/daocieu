package com.liketry.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created pengyy on 2017/9/21.
 * 短信API
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 */
@Slf4j
public class SmsUtils {

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";
    
    private static PropertiesUtils pro = PropertiesUtils.getInstance();
    
    private static SmsUtils instance = null;

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private static final String accessKeyId = pro.getValue("accessKeyId");
    private static final String accessKeySecret = pro.getValue("accessKeySecret");

    public static synchronized SmsUtils getInstance(){

		if (instance == null){
			instance = new SmsUtils();
		}
		return instance;
	}

    /**
     * 发送短信
     * @param phoneNumber  接收人手机号
     * @param signName  短信签名
     * @param templateCode  短信模板code
     * @param templateParam 模板中的变量替换JSON串,如模板内容为"欢迎注册,您的验证码为${code}"时,此处的值为 {\"code\":\"1234\"}
     * @return
     * @throws ClientException
     */
    public SendSmsResponse sendSms(String phoneNumber,String signName,String templateCode,
    		String templateParam) throws Exception {

    	log.info("<======发送短信通知，手机号：{}，签名：{}，模板：{}===============>",phoneNumber,signName,templateCode);
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("liketry");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
        	sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (Exception e) {
			log.error("<======发送短信错误，手机号：{}，签名：{}，模板：{}，错误信息：{}===============>",phoneNumber,signName,templateCode,e);
			e.printStackTrace();
		}

        return sendSmsResponse;
    }

    /**
     * 查询发送短信记录
     * @param bizId 业务流水号（发送回执中获取）
     * @param phoneNumber 接收人手机号
     * @param date 查询日期
     * @return
     * @throws ClientException
     */
    public QuerySendDetailsResponse querySendDetails(String bizId,String phoneNumber,Date date) throws Exception {

    	log.info("<======查询发送短信记录，手机号：{}，流水号：{}===============>",phoneNumber,bizId);
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumber);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(date));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
        	querySendDetailsResponse = acsClient.getAcsResponse(request);
		} catch (Exception e) {
			log.error("<======查询发送短信记录错误，手机号：{}，流水号：{}，错误信息：{}===============>",phoneNumber,bizId,e);
			e.printStackTrace();
		}
        
        return querySendDetailsResponse;
    }

    /**
     * 测试
     * @param args
     * @throws ClientException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws Exception {

        //发短信
    	JSONObject smsJson = new JSONObject();
    	smsJson.put("code",new Random().nextInt(8999) + 1000);
        SendSmsResponse response = SmsUtils.getInstance().sendSms("1760040154", "阿里云短信测试专用", "SMS_95605478", smsJson.toString());
        log.info("短信接口返回的数据----------------");
        log.info("Code=" + response.getCode());
        log.info("Message=" + response.getMessage());
        log.info("RequestId=" + response.getRequestId());
        log.info("BizId=" + response.getBizId());

        Thread.sleep(3000L);

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = SmsUtils.getInstance().querySendDetails(response.getBizId(),"1760040154",new Date());
            log.info("短信明细查询接口返回数据----------------");
            log.info("Code=" + querySendDetailsResponse.getCode());
            log.info("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                log.info("SmsSendDetailDTO["+i+"]:");
                log.info("Content=" + smsSendDetailDTO.getContent());
                log.info("ErrCode=" + smsSendDetailDTO.getErrCode());
                log.info("OutId=" + smsSendDetailDTO.getOutId());
                log.info("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                log.info("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                log.info("SendDate=" + smsSendDetailDTO.getSendDate());
                log.info("SendStatus=" + smsSendDetailDTO.getSendStatus());
                log.info("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            log.info("TotalCount=" + querySendDetailsResponse.getTotalCount());
            log.info("RequestId=" + querySendDetailsResponse.getRequestId());
        }

    }
}
