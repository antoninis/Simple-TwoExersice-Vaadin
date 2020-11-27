package org.test;

import java.util.Arrays;

public class ExpandedForm {

    public static String Expanded(String num) {

        try {
            int b = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return "Ошибка ввода";
        }

        String[] str = num.split("");
        String result = "";

        for(int i = 0; i < str.length-1; i++) {
            if(Integer.valueOf(str[i]) > 0) {
                for(int j = i; j < str.length-1; j++) {
                    str[i] += '0';
                }
            }
        }
        result = Arrays.toString(str);
        result = result.substring(1, result.length()-1).replace(",", " +").replace(" + 0","");
        return result;
    }

    public static void main(String[] args) {
        String a1="70304";
        //String[] a1={"arp", "live", "strong"};
        //String[] a2={"lively", "alive", "harp", "sharp", "armstrong"};
        System.out.println(a1 + " = " + Expanded(a1));
    }
}
