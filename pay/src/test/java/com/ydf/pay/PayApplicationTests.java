package com.ydf.pay;

import java.util.LinkedList;

public class PayApplicationTests {
    public static void main(String[] args) {
        LinkedList<Integer> nums = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            nums.add(i);
        }

        for (int i = nums.size() - 1; i >= 0; i--) {
            System.out.print(nums.get(i));
        }
//        for (; nums.size() > 0; ) {
//            System.out.print(nums.pollLast());
//        }
    }

}
