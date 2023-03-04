package com.example.mydemo1;

import java.util.HashSet;

public class test {
    //  我的评价是虽然性能慢点 但是确实能用  代码也少。
    public static HashSet<Integer> randomSet(int min, int max, int n) {
        if ( n<=0 )return  null;
        HashSet<Integer> set = new HashSet<Integer>();
        while (set.size() < n){
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max+1 - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        return set;
    }

    public static void main(String[] args){
        //参数依次是   最小数  最大数  生成数量
        HashSet<Integer> integers = randomSet(10000000, 99999999, 1000);
        for (Integer integer : integers) {
            System.out.println("ete" + integer);
            System.out.println("ete" + integer);
            System.out.println("08" + integer);
            System.out.println(integer + "@gmail.com");

        }
    }

}
