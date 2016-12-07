package forever.foreverandroiddemo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 说明：MD5工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/27 0:07
 * <p/>
 * 版本：verson 1.0
 */

public final class MD5 {

	private MD5() {
	}

	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHasnString(digest);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e("", content + "转MD5异常");
		}
		return null;
	}

	public static String getMD5Num(String content, int count) {
		try {
			if (count <= 0) {
				LogUtils.e("", "加密次数必须大于0");
			} else {
				String md5 = content;
				for (int i = 0; i < count; i++) {
					md5 = getMD5(md5);
				}
				return md5;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e("", content + "转MD5异常");
		}
		return null;
	}

	private static String getHasnString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}
	
	 public static String md5(String string) {
	        byte[] hash;
	        try {
	            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("Huh, MD5 should be supported?", e);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	        }

	        StringBuilder hex = new StringBuilder(hash.length * 2);
	        for (byte b : hash) {
	            if ((b & 0xFF) < 0x10)
	                hex.append("0");
	            hex.append(Integer.toHexString(b & 0xFF));
	        }
	        return hex.toString();
	    }
	    
}
