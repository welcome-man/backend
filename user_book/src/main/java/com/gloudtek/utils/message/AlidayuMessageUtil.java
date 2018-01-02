package com.gloudtek.utils.message;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.json.JSONWriter;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AlidayuMessageUtil {
	
	//服务器的IP需要添加进白名单"http://101.37.84.120:8090/cost-manager/message/test"
	private static final String SERVER_URL = "http://gw.api.taobao.com/router/rest";
	
	private static final String APP_KEY = "23739897";
	
	private static final String APP_SECUET = "7726691cb9136c2f077826128cf45d8f";
	
	/** 可以自定义,但是需要审核 start */
	private static final String SMS_FREE_SIGN_NAME = "注册验证";
	
	private static final String SMS_TEMPLATE_CODE = "SMS_61905002";
	/** 可以自定义,但是需要审核 end */
	
	/**
	 * 短信息发送
	 * @param sendPhoneNum 发送目标的手机号码
	 * @param verifyCode   验证码
	 */
	public static String messageSend(String sendPhoneNum,SmsParam smsParam) throws Exception {
		TaobaoClient client = new DefaultTaobaoClient(SERVER_URL,APP_KEY,APP_SECUET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(SMS_FREE_SIGN_NAME);
		req.setSmsParamString(new JSONWriter().write(smsParam));
		req.setRecNum(sendPhoneNum);
		req.setSmsTemplateCode(SMS_TEMPLATE_CODE);
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		return rsp.getBody();
	}
	
	//生成6位随机数字验证码（000000-999999）
	public static String getGenerateCode(){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<6;i++){
			sb.append((int)(Math.random()*10));
		}
		return sb.toString();
	}

}
