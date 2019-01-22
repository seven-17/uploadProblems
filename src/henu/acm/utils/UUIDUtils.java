package henu.acm.utils;

import java.util.UUID;

//随机字符串  32位
public class UUIDUtils {

	public static String getUUID() {
		String str = UUID.randomUUID().toString();
		String ans = "";
		for(int i=0;i<str.length();i++) {
			char ch = str.charAt(i);
			if(ch=='-') {
				continue;
			}
			ans+=ch;
		}
		return ans;
	}
	
}
