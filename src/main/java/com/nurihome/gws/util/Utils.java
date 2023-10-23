package com.nurihome.gws.util;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Common utility class.
 */
public final class Utils {

	private static final AtomicLong timestamp = new AtomicLong((System.currentTimeMillis() / 1000L) * 1000000000L);

	private static final Pattern MAIL_PATTERN_DEFAULT = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
	private static final Pattern DATE_PATTERN_DEFAULT = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
	
	/**
	 * <p>private constructor</p>
	 */
	private Utils() {}
	
	/**
	 * <p>generate Unique ID with Timestamp.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.createLongUniqueIdByTimestamp() = 1553844847000000000;
	 * }</pre>
	 * </blockquote>
	 */
	public static long createLongUniqueIdByTimestamp() {
		return timestamp.getAndIncrement();
	}

	/**
	 * <p>generate Unique ID with Timestamp.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.createUniqueIdByTimestamp() = "1553844847000000000";
	 * }</pre>
	 * </blockquote>
	 */
	public static String createUniqueIdByTimestamp() {
		return String.format("%1$d", createLongUniqueIdByTimestamp());
	}

	/**
	 * <p>checks if a String is whitespace, empty ("") or null.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.isEmpty(null)      = true;
	 *     Utils.isEmpty("")        = true;
	 *     Utils.isEmpty(" ")       = true;
	 *     Utils.isEmpty("foo")     = false;
	 *     Utils.isEmpty("  bar  ") = false;
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean isEmpty(final String str) {
		// ------------------------------------------------------------
		// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#isBlank(java.lang.CharSequence)
		// ------------------------------------------------------------
		return StringUtils.isBlank(str);
	}

	/**
	 * <p>check if Map is empty or null.</p>
	 */
	public static boolean isEmpty(final Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}
	
	/**
	 * <p>returns either the passed in str, or if the str is whitespace, empty ("") or null, the value of defstr.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.isEmpty(null, "bar")  = "bar"
	 *     Utils.isEmpty("", "bar")    = "bar"
	 *     Utils.isEmpty(" ", "bar")   = "bar"
	 *     Utils.isEmpty("foo", "bar") = "foo"
	 * }</pre>
	 * </blockquote>
	 */
	public static String isEmpty(final String str, final String defstr) {
		// ------------------------------------------------------------
		// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#defaultIfBlank-T-T-
		// ------------------------------------------------------------
		if (isEmpty(str)) {
			return defstr;
		}else
		{
			return str;
		}
	}
	
	public static double isEmpty(final BigDecimal bigDecimal, final String defstr) {
		if (bigDecimal == null) {
			return Double.valueOf(defstr);
		}else
		{
			return bigDecimal.doubleValue();
		}
	}

	/**
	 * <p>check whether params is empty or not</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Map<String, String> params = new HashMap<String, String>();
	 *     params.put("userName",  "foo");
	 *     params.put("userId",    "bar");
	 *     params.put("userEmail", "");
	 *     
	 *     Utils.isEmpty(params, "userName", "userId")     = true;
	 *     Utils.isEmpty(params, "userName")               = true;
	 *     Utils.isEmpty(params, "userName", "userEmail")  = false;
	 *     Utils.isEmpty(params, "userName", "userCard")   = false;
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean isEmpty(final Map<String, String> params, final String... keys) {
		if ((params != null) && (keys != null)) {
			for (String key : keys) {
				if (isEmpty(params.get(key))) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * <p>fill a empty value in keys</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Map<String, String> params = new HashMap<String, String>();
	 *     
	 *     params.put("userName",  null);
	 *     params.put("userEmail", "foo");
	 *     
	 *     Utils.isEmptyFill(params, "userName", "userId", "userEmail");
	 *     
	 *     params.get("userName")  = "";
	 *     params.get("userId")    = "";
	 *     params.get("userEmail") = "foo";
	 * }</pre>
	 * </blockquote>
	 */
	public static void isEmptyFill(Map<String, String> params, final String... keys) {
		if ((params != null) && (keys != null)) {
			for (String key : keys) {
				if (isEmpty(params.get(key))) {
					params.put(key, "");
				}
			}
		}
	}

	/**
	 * <p>checks whether the given String is a parsable number.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.isNumber(null)    = false
	 *     Utils.isNumber("")      = false
	 *     Utils.isNumber("foo")   = false
	 *     Utils.isNumber("0")     = true
	 *     Utils.isNumber("0.1")   = true
	 *     Utils.isNumber("-1")    = true
	 *     Utils.isNumber("1")     = true
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean isNumber(final String str) {
		return NumberUtils.isParsable(str);
	}

	/**
	 * <p>checks whether the given String is a valid date.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.isValidDate("2019-01-01") = true
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean isValidDate(final String str) {
		return DATE_PATTERN_DEFAULT.matcher(str).matches();
	}

	/**
	 * <p>checks whether the given String is a valid email.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.isValidEmail("user@domain.com") = true
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean isValidEmail(final String str) {
		return MAIL_PATTERN_DEFAULT.matcher(str).matches();
	}

	public static boolean isContains(final String str, final String find) {
		return StringUtils.contains(str, find);
	}

	/**
	 * checks if a String starts with a specified prefix.
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.startsWith(null, null)      = true
	 *     Utils.startsWith(null, "abc")     = false
	 *     Utils.startsWith("abcdef", null)  = false
	 *     Utils.startsWith("abcdef", "abc") = true
	 *     Utils.startsWith("ABCDEF", "abc") = true
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean startsWith(final String str, final String prefix) {
		// ------------------------------------------------------------
		// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#startsWithIgnoreCase-java.lang.CharSequence-java.lang.CharSequence-
		// ------------------------------------------------------------
		return StringUtils.startsWithIgnoreCase(str, prefix);
	}

	/**
	 * check if a String ends with a specified suffix.
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.endsWidh(null, null)      = true
	 *     Utils.endsWidh(null, "def")     = false
	 *     Utils.endsWidh("abcdef", null)  = false
	 *     Utils.endsWidh("abcdef", "def") = true
	 *     Utils.endsWidh("ABCDEF", "def") = true
	 *     Utils.endsWidh("ABCDEF", "cde") = false
	 * }</pre>
	 * </blockquote>
	 */
	public static boolean endsWith(final String str, final String suffix) {
		// ------------------------------------------------------------
		// https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#endsWithIgnoreCase-java.lang.CharSequence-java.lang.CharSequence-
		// ------------------------------------------------------------
		return StringUtils.endsWithIgnoreCase(str, suffix);
	}

	/**
	 * <p>removes leading and trailing whitespace.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.trim(" abc ") = "abc"
	 * }</pre>
	 * </blockquote>
	 */
	public static String trim(final String str) {
		return StringUtils.trim(str);
	}

	/**
	 * <p>replaces all occurrences of a String within another String.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.replace(null, *, *)        = null
	 *     Utils.replace("", *, *)          = ""
	 *     Utils.replace("abc", null, *)    = "abc"
	 *     Utils.replace("abc", *, null)    = "abc"
	 *     Utils.replace("abc", "", *)      = "abc"
	 *     Utils.replace("abc", "a", null)  = "abc"
	 *     Utils.replace("abc", "a", "")    = "bc"
	 *     Utils.replace("aba", "a", "z")   = "zbz"
	 * }</pre>
	 * </blockquote>
	 */
	public static String replace(final String text, final String search, final String replacement) {
		return StringUtils.replace(text, search, replacement);
	}

	/**
	 * <p>returns the string that is nested in between two strings. Only the first match is returned..</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.substringBetween("wx[b]yz", "[", "]")    = "b"
	 *     Utils.substringBetween(*, null, *)             = null
	 *     Utils.substringBetween(*, *, null)             = null
	 *     Utils.substringBetween("", "", "")             = ""
	 *     Utils.substringBetween("", "", "]")            = null
	 *     Utils.substringBetween("", "[", "]")           = null
	 *     Utils.substringBetween("yabcz", "", "")        = ""
	 *     Utils.substringBetween("yabcz", "y", "z")      = "abc"
	 *     Utils.substringBetween("yabczyabcz", "y", "z") = "abc"
	 * }</pre>
	 * </blockquote>
	 */
	public static String substringBetween(final String str, final String open, final String close) {
		return StringUtils.substringBetween(str, open, close);
	}	

	/**
	 * <p>returns a escapes the characters in a String using Json String rules.<p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.escapeJson("JSON string")
	 * }</pre>
	 * </blockquote>
	 */
	public static String escapeJson(final String str) {
		if (!Utils.isEmpty(str)) {
			return StringEscapeUtils.escapeJson(str);
		}
		
		return "";
	}

	/**
	 * <p>returns unescapes a string containing entity escapes to a string containing the actual Unicode characters corresponding to the escapes.<p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.unescapeHtml("escaped HTML string")
	 * }</pre>
	 * </blockquote>
	 */
	public static String unescapeHtml(final String str) {
		if (!Utils.isEmpty(str)) {
			return StringEscapeUtils.unescapeHtml4(str);
		}
		
		return "";
	}

	/**
	 * <p>returns a hexadecimal encoded SHA-256 hash for the input String.<p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     Utils.convertSHA256Hash("1")  = "6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"
	 *     Utils.convertSHA256Hash(null) = ""
	 *     Utils.convertSHA256Hash("")   = "abc"
	 * }</pre>
	 * </blockquote>
	 */
	public static String convertSHA256Hash(final String str) {
		String sha256hex = "";
		
		try {
			//--- convert String to SHA-256 Hash
			if (!Utils.isEmpty(str)) {
				sha256hex = DigestUtils.sha256Hex(str);				
			}
		}catch (Exception ignore)
		{
			//--- nothing
		}

		return sha256hex;
	}

	/**
	 * <p>convert a uri to a byte array.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     byte[] content = Utils.convertUri2Bytes(new URL("http://foo.bar.com/a/b/c/foot.bar"));
	 * }</pre>
	 * </blockquote>
	 */
	public static byte[] convertUri2Bytes(final URL downloadUrl) {
		byte[] content = null;
		
		if (downloadUrl != null) {
			try {
				content = IOUtils.toByteArray(downloadUrl.openStream());
			}catch (Exception ignore)
			{
				//--- nothing
			}
		}
		
		return content;
	}

	/**
	 * <p>convert byte array to Base64 encoded string.</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     String content = Utils.convertByte2Base64("byte array");
	 * }</pre>
	 * </blockquote>
	 */
	public static String convertByte2Base64(final byte[] src) {
		String str = "";
		
		if ((src != null) && (src.length != 0)) {
			str = new String(Base64.encodeBase64(src));
		}
		
		return str;
	}

	/**
	 * <p>convert Base64 encoded string to byte array</p>
	 * 
	 * <blockquote>
	 * <pre>{@code
	 * Usage:
	 *     byte[] content = Utils.convertBase64String2Byte("Base64 encoded string");
	 * }</pre>
	 * </blockquote>
	 */
	public static byte[] convertBase64String2Byte(final String str) {
		byte[] content = null;
		
		if (str != null) {
			content = Base64.decodeBase64(str);
		}
		
		return content;
	}

	/**
	 * <p>check if a String ends with a specified suffix.</p>
	 * 
	 * <pre>
	 * Utils.endsWidh(null, null)      = true
	 * Utils.endsWidh(null, "def")     = false
	 * Utils.endsWidh("abcdef", null)  = false
	 * Utils.endsWidh("abcdef", "def") = true
	 * Utils.endsWidh("ABCDEF", "def") = true
	 * Utils.endsWidh("ABCDEF", "cde") = false
	 * </pre>
	 * 
	 * @see https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html#endsWithIgnoreCase-java.lang.CharSequence-java.lang.CharSequence-
	 */
	public static boolean endsWidh(final String str, final String suffix) {
		return StringUtils.endsWithIgnoreCase(str, suffix);
	}

	public static String getDateUnDashFormat(String dateValue) {
    	if(dateValue == null) return dateValue;

        if (dateValue.trim().length() == 10 && dateValue.indexOf("-") > 0) {
        	return dateValue.replaceAll("-", "");
        }else
        {
            return dateValue;
        }
    }
	
	/**
	* 실행중인 함수를 취득。
	* @return 함수명
	*/
	public static String getMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
	public static String getNumberType(double number, String format){
        String result = "";
        DecimalFormat decimalformat = new DecimalFormat(format);
        result = decimalformat.format(number);

        return result;
    }

    public static String getNumberType(String strNumber, String format){
        String result = "";

        try{
            double number = Double.parseDouble(strNumber);
            result = getNumberType(number, format);
        }catch (NumberFormatException numberformatexception)
        {
        	result = strNumber;
        }

        return result;
    }
    
	public static String setNumberType(String strNumber){
    	String result = "";
    	
    	if(!"".equals(strNumber)) {
    		if(strNumber.indexOf(".") > -1){
        		result = getNumberType(strNumber, "###,###,###,###,##0.00");
        	}else{
        		result = getNumberType(strNumber, "###,###,###,###,##0");
        	}
    	}
    	
    	return result;
    }
	
	public static String setNumberTypeWithCurrency(String strNumber, String currency){
    	String result = "";
    	
    	if(!"".equals(strNumber)) {
    		if(strNumber.indexOf(".") > -1){
        		result = getNumberType(strNumber, "###,###,###,###,##0.00");
        	}else{
        		result = getNumberType(strNumber, "###,###,###,###,##0");
        	}
    	}
    	
    	if(!"".equals(result)) {
    		if(result.substring(0, 1).equals("-")) {
        		result = "-" + currency + result.substring(1);
        	}else {
        		result = currency + result;
        	}
    	}
    	
    	return result;
    }
	
	//바이트 제한
	public static String byteLimit(String inputStr, int limit) throws Exception {
		byte[] newByte = null;
		
		try {
			if(inputStr == null) {
				return "";
			}
	            
	        if (limit <= 0) {
	        	return inputStr;
	        }
	            
	        byte[] strbyte = null;
	        strbyte = inputStr.getBytes("UTF-8");
	        if (strbyte.length <= limit) {
	            return inputStr;
	        }
	        
	        char[] charArray = inputStr.toCharArray();
	        int checkLimit = limit;
	        for (int i=0; i<charArray.length; i++ ) {
	            if(charArray[i] < 256) {
	                checkLimit -= 1;
	            }
	            else {
	                checkLimit -= 2;
	            }
	            if(checkLimit <= 0) {
	                break;
	            }
	        }
	        
	        //대상 문자열 마지막 자리가 2바이트의 중간일 경우 제거함
	        newByte = new byte[limit + checkLimit];
	        for (int i=0 ; i<newByte.length ; i++ ) {
	            newByte[i] = strbyte[i];
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new String(newByte, "UTF-8");
    }
}
