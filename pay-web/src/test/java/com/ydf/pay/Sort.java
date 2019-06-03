package com.ydf.pay;

import org.assertj.core.util.Arrays;

/**
 * @author yuandongfei
 * @date 2019/5/29
 */
public class Sort {
    Integer[] data;
    long time;

    public Sort() {
        data = new Integer[]{2, 6, 9, 4, 7, 2, 12, 8, 23, 14};
    }

    public static void main(String[] args) {
        Sort sort = new Sort();
        sort.print("排序前    ");
//        sort.bubble();
//        sort.print("冒泡排序后");
//        sort.selected();
//        sort.print("选择排序后");
//        sort.insertion();
//        sort.print("插入排序后");
        sort.binary();
        sort.print("二分法排序后");
    }

    /**
     * 打印数组
     */
    public void print(String prefix) {
        int length = data.length;
        System.out.print(prefix + " : ");
        for (int i = 0; i < length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println("。共用时 : " + time + "ms");
    }

    /**
     * 冒泡排序
     */
    public void bubble() {

        if (Arrays.isNullOrEmpty(data)) {
            return;
        }
        long l = System.currentTimeMillis();
        int len = data.length;
        int count = 0;
        boolean flag;
        // 保证数组全部遍历一次
        for (int i = 0; i < len - 1; i++) {
            flag = true;
            // 每次遍历,将排序成功一个数据
            // 遍历所有未成功排序的数据，以为i次的遍历已成功排序过了
            for (int j = 0; j < len - 1 - i; j++) {
                if (data[j + 1] < data[j]) {
                    int tmp = data[j + 1];
                    data[j + 1] = data[j];
                    data[j] = tmp;
                    flag = false;
                }
                count++;
            }
            if (flag) {
                break;
            }
        }
        time = System.currentTimeMillis() - l;
        System.out.println("共遍历" + count + "次");
    }

    /**
     * 选择排序
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，
     * 然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕
     */
    public void selected() {
        int length = data.length;
        for (int i = 0; i < length; i++) {
            // 初始一个最小值的下标
            int minIndex = i;
            for (int j = i; j < length; j++) {
                // 从i开始遍历找到最小值的下标
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换当前i和最小值下标的数据
            int tmp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = tmp;
        }
    }

    /**
     * 插入排序
     */
    public void insertion() {
        // 直接插入排序
        // 在排序之前我们需要搞清一个思路，新插入一个数据的时候，排序过后的数组都是
        // 从小到大排列好的，所以我们需要从后往前查找，直到找到比我们要插入的数字还小的值。
        // 这个时候我们需要一个变量j作为标识
        for (int i = 1; i < data.length; i++) {
            int temp = data[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                //将大于temp的数向后移动一步
                if (data[j] > temp) {
                    //记录j的值也就是temp要插入的位置
                    data[j + 1] = data[j];
                } else {
                    break;
                }
            }
            data[j + 1] = temp;
        }
    }

    /**
     * 二分法排序
     * # 二分法插入排序是在插入排序的基础上，使用二分法查找将元素插入的方法
     * # 基本原理：（升序）
     * 1.将元素依次放入有序序列中
     * 2.取出待排序元素，与有序序列的前半段进行比较
     * 3.缩小有序序列范围，进一步划分比较，直至范围内仅有1或2个数字
     * 4.将插入值与范围进行比较
     * 3.重复实现升序
     * 实现过程：外层循环控制循环次数，中层循环实现有序排列，内层循环实现查找插入
     */
    public void binary() {
        for (int i = 1; i < data.length; i++) {
            int temp = data[i];
            int low = 0;
            int high = i - 1;
            int mid = -1;
            while (low <= high) {
                mid = low + (high - low) / 2;
                System.out.println("low: " + low + ", hight: " + high + ", mid: " + mid);
                if (data[mid] > temp) {
                    high = mid - 1;
                } else { // 元素相同时，也插入在后面的位置
                    low = mid + 1;
                }
            }
            for (int j = i - 1; j >= low; j--) {
                data[j + 1] = data[j];
            }
            data[low] = temp;
        }
    }
}
