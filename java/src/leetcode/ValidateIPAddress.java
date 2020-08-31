package leetcode;

/**
 * leetcode 468. Validate IP Address
 * Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.
 *
 *   IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, each
 * ranging from 0 to 255, separated by dots ("."), e.g.,172.16.254.1;
 *
 * Besides, leading zeros in the IPv4 is invalid. For example, the address 172.16.254.01 is invalid.
 *
 *   IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. The
 * groups are separated by colons (":"). For example, the address 2001:0db8:85a3:0000:0000:8a2e:0370:7334 is a valid
 * one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters in the
 * address to upper-case ones, so 2001:db8:85a3:0:0:8A2E:0370:7334 is also a valid
 * IPv6 address(Omit leading zeros and using upper cases).
 *
 *   However, we don't replace a consecutive group of zero value with a single empty group using two consecutive
 * colons (::) to pursue simplicity. For example, 2001:0db8:85a3::8A2E:0370:7334 is an invalid IPv6 address.
 *
 *   Besides, extra leading zeros in the IPv6 is also invalid. For example,
 * the address 02001:0db8:85a3:0000:0000:8a2e:0370:7334 is invalid.
 *
 * Note: You may assume there is no extra space or special characters in the input string.
 *
 * Example 1:
 *   Input: "172.16.254.1"
 *   Output: "IPv4"
 *   Explanation: This is a valid IPv4 address, return "IPv4".
 * Example 2:
 *   Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
 *   Output: "IPv6"
 *   Explanation: This is a valid IPv6 address, return "IPv6".
 * Example 3:
 *   Input: "256.256.256.256"
 *   Output: "Neither"
 *   Explanation: This is neither a IPv4 address nor a IPv6 address.
 */
public class ValidateIPAddress {
	private static final String IPv4 = "IPv4";
	private static final String IPv6 = "IPv6";
	private static final String NEITHER = "Neither";
	private static final int IPv4_SEPARATOR_COUNT = 3;
	private static final int IPv6_SEPARATOR_COUNT = 7;

	public static String validIPAddress(String IP) {
		String result = NEITHER;

		if(IP.length() < 5) return NEITHER;

		for(int i = 0; i < 5; i ++) {
			if(IP.charAt(i) == '.') {
				result = IPv4; break;
			} else if(IP.charAt(i) == ':'){
				result = IPv6; break;
			}
		}

		if(result.equals(IPv4)) {
			result = validateIPv4(IP);
		} else if (result.equals(IPv6)) {
			result = validateIPv6(IP);
		}

		return result;
	}

	private static String validateIPv4(String str) {
		String result = IPv4;

		// "172.16.254.1.".split("\\.")得到的结果数组长度为4,所以需要优先排除首尾为'.'的字符串
		if(str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.') return NEITHER;

		String[] array = str.split("\\.");

		if(array.length != 4) return NEITHER;

		int sum;
		char c;
		for(String s : array) {
			sum = 0;
			if(s.length() > 3 || s.length() < 1 || (s.charAt(0) == '0' && s.length() > 1)) return NEITHER;

			for(int i = 0; i < s.length(); i ++) {
				c = s.charAt(i);
				if(c >= '0' && c <= '9') {
					sum = sum * 10 + (c - '0');
				} else {
					return NEITHER;
				}
				if(sum > 255) return NEITHER;
			}
		}

		return result;
	}

	// 不使用内置的split方法
	private static String validateIPv4_2(String str) {
		int n = str.length();
		if(str.charAt(0) == '.' || str.charAt(n - 1) == '.') return NEITHER;

		int i = 0, sum = 0, count = 0, l = 0;
		char c;
		for(; i < n && count <= IPv4_SEPARATOR_COUNT; i ++) {
			c = str.charAt(i);
			if(c == '.') {
				if(l < 1) return NEITHER;
				count ++;
				l = sum = 0;
				continue;
			} else if(c >= '0' && c <= '9') {
				if(l == 0 && c == '0' && i + 1 < n && str.charAt(i + 1) != '.') return NEITHER;
				l ++;
				sum = sum * 10 + (c - '0');
				if(sum > 255) return NEITHER;
			} else {
				return NEITHER;
			}
		}

		return i == n && count == IPv4_SEPARATOR_COUNT ? IPv4 : NEITHER;
	}

	private static String validateIPv6(String str) {
		String result = IPv6;

		if(str.charAt(0) == ':' || str.charAt(str.length() - 1) == ':') return NEITHER;

		String[] array = str.split(":");

		if(array.length != 8) return NEITHER;

		for(String s : array) {
			if(s.length() > 4 || s.length() < 1) return NEITHER;

			char c;
			for(int i = 0; i < s.length(); i ++) {
				c = s.charAt(i);
				if(!(c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F'))
					return NEITHER;
			}
		}

		return result;
	}

	// 不使用内置的split方法
	private static String validateIPv6_2(String str) {
		int n = str.length();

		if(str.charAt(0) == ':' || str.charAt(n - 1) == ':') return NEITHER;

		int count = 0, l = 0, i = 0;
		char c;
		for(; i < n && count <= IPv6_SEPARATOR_COUNT; i ++) {
			c = str.charAt(i);
			if(c == ':') {
				if(l == 0) return NEITHER;
				count ++;
				l = 0;
			} else if(c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F') {
				if(l == 4) return NEITHER;
				l ++;
			} else {
				return NEITHER;
			}
		}

		return i == n && count == IPv6_SEPARATOR_COUNT ? IPv6 : NEITHER;
	}

	public static void main(String[] args) {
		System.out.println(validIPAddress("172.16.254.1"));
		System.out.println(validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"));
		System.out.println(validIPAddress("256.256.256.256"));
		System.out.println(validIPAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334:"));
		System.out.println(validIPAddress("172.16.254.1."));
		System.out.println(validIPAddress("12.12.12"));
		System.out.println(validIPAddress("01.01.01.01"));
		System.out.println(validIPAddress("0.0.0.256"));
		System.out.println(validIPAddress("12..33.4"));
	}
}
