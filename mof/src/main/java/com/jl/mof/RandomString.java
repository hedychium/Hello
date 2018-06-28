package com.jl.mof;

import java.util.Random;

/**
 * ��������ַ������ɵķ���
 *
 * @author Administrator
 *
 */
public class RandomString {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";

	/**
	 * ����һ������������ַ���(������Сд��ĸ������)
	 *
	 * @param length
	 *            ����ַ�������
	 * @return ����ַ���
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * ����һ���������������ĸ�ַ���(ֻ������Сд��ĸ)
	 *
	 * @param length
	 *            ����ַ�������
	 * @return ����ַ���
	 */
	public static String generateUpperLowerString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(LETTERCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * ����һ�������������Сд��ĸ�ַ���(ֻ����Сд��ĸ)
	 *
	 * @param length
	 *            ����ַ�������
	 * @return ����ַ���
	 */
	public static String generateLowerString(int length) {
		return generateUpperLowerString(length).toLowerCase();
	}

	/**
	 * ����һ���������������д��ĸ�ַ���(ֻ������д��ĸ)
	 *
	 * @param length
	 *            ����ַ�������
	 * @return ����ַ���
	 */
	public static String generateUpperString(int length) {
		return generateUpperLowerString(length).toUpperCase();
	}

	/**
	 * ����һ�������Ĵ�0�ַ���
	 *
	 * @param length
	 *            �ַ�������
	 * @return ��0�ַ���
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * ������������һ���������ַ��������Ȳ���ǰ�油0
	 *
	 * @param num
	 *            ����
	 * @param fixdlenth
	 *            �ַ�������
	 * @return �������ַ���
	 */
	public static String generateFixdLengthString(long num, int fixedlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixedlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixedlenth - strNum.length()));
		} else {
			throw new RuntimeException("������" + num + "ת��Ϊ����Ϊ" + fixedlenth
					+ "���ַ��������쳣��");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * ÿ�����ɵ�lenλ��������ͬ(���������������ÿ��Ԫ�ض�0-9��ʮ���������֣���len��ֵ�����ܴ�������ĳ���)
	 *
	 * @param param
	 * @return ����������
	 */
	public static int generateDifferentNum(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}
}
