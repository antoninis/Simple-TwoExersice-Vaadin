package org.test;

import java.util.ArrayList;
import java.util.Arrays;

public class WhichAreIN {
    public static String inArray(String text) {
        ArrayList<String> res = new ArrayList<>();

        String[] arr= text.split("\n");
        if (arr.length!=2) {return "Ошибка ввода";}
        else {
            String[] array1 = arr[0].split(", ");
            String[] array2 = arr[1].split(", ");

            for (int i = 0; i < array1.length; i++) {
                if (isContain(array2, array1[i])) {
                    res.add(array1[i]);
                }
            }
            return Arrays.toString(res.toArray(new String[res.size()]));
        }
    }

    public static boolean isContain(String[] arr, String wrd){
        for (int i=0; i<arr.length; i++){
            if(arr[i].contains(wrd)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]){
        String a1="arp, live, strong\nlively, alive, harp, sharp, armstrong";
//        String a1= "tarp, mice, bull\nlively, alive, harp, sharp, armstrong";

        System.out.println(a1);

        String a3 = inArray(a1);
        System.out.println(a3);
    }
}
