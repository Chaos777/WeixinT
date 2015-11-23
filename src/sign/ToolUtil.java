package sign;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;

public class ToolUtil {
	private static final String TOKEN = "Chaos777";
	public static boolean checkSignature(String sign,String time,String nonce,String echostr){
		String [] args = new String[]{TOKEN,time,nonce};
		//排序
		Arrays.sort(args);
		//组成字符串，sha1加密
		StringBuffer sb = new StringBuffer();
		for(String s:args){
			sb.append(s);
		}
		String signTemp = DigestUtils.shaHex(sb.toString());
		if(signTemp.equals(sign)){
			return true;
		}
		//与sign对比
		return false;
	}

}
