package com.jl.crawl.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author hu
 */
public class RegexRule {
    
    public RegexRule(){
        
    }
    public RegexRule(String rule){
        addRule(rule);
    }
    
    public RegexRule(ArrayList<String> rules){
        for (String rule : rules) {
            addRule(rule);
        }
    }
    
    public boolean isEmpty(){
        return positive.isEmpty();
    }

    private ArrayList<String> positive = new ArrayList<String>();
    private ArrayList<String> negative = new ArrayList<String>();

  
    
    /**
     * ���һ��������� ������������֣�������ͷ����� 
     * URL�������������Ҫ�������������� 1.������ƥ��һ�������� 2.���ܺ��κη�����ƥ��
     * ������ʾ����+a.*c��һ�����������������Ϊa.*c����ʼ�Ӻű�ʾ������
     * ������ʾ����-a.*cʱһ�����������������Ϊa.*c����ʼ���ű�ʾ������
     * ���һ���������ʼ�ַ���Ϊ�Ӻ��Ҳ�Ϊ���ţ��������Ϊ���������������Ϊ����
     * ����a.*c��һ�����������������Ϊa.*c
     * @param rule �������
     * @return ����
     */
    public RegexRule addRule(String rule) {
        if (rule.length() == 0) {
            return this;
        }
        char pn = rule.charAt(0);
        String realrule = rule.substring(1);
        if (pn == '+') {
            addPositive(realrule);
        } else if (pn == '-') {
            addNegative(realrule);
        } else {
            addPositive(rule);
        }
        return this;
    }

   
    
    /**
     * ���һ�����������
     * @param positiveregex
     * @return ����
     */
    public RegexRule addPositive(String positiveregex) {
        positive.add(positiveregex);
        return this;
    }

  
    /**
     * ���һ�����������
     * @param negativeregex
     * @return ����
     */
    public RegexRule addNegative(String negativeregex) {
        negative.add(negativeregex);
        return this;
    }

   
    /**
     * �ж������ַ����Ƿ�����������
     * @param str ������ַ���
     * @return �����ַ����Ƿ�����������
     */
    public boolean satisfy(String str) {

        int state = 0;
        for (String nregex : negative) {
            if (Pattern.matches(nregex, str)) {
                return false;
            }
        }

        int count = 0;
        for (String pregex : positive) {
            if (Pattern.matches(pregex, str)) {
                count++;
            }
        }
        if (count == 0) {
            return false;
        } else {
            return true;
        }

    }
}