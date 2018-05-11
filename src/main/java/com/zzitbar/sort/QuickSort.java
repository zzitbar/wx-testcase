package com.zzitbar.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * 快速排序
 * Created By Administrator
 * Date:2018/5/11
 * Time:10:15
 */
public class QuickSort {

    private static int COUNT1 = 0;
    private static int COUNT2 = 0;

    @Test
    public void test() {
        sort(new Comparable[]{4, 2, 8, 6, 9, 1, 20, 4, 8, 7});
    }


    public static void sort(Comparable[] a) {
        a = QuickSort.shuffle(a);
//        Comparable[] a2 = QuickSort.shuffle(a);
//        a = new Comparable[]{1, 5, 1, 3, 1, 2};
        Comparable[] a1 = Arrays.copyOf(a, a.length);
        Comparable[] a2 = Arrays.copyOf(a, a.length);
        System.out.println("---------sort1 before ----------");
        QuickSort.print(a1);
        sort1(a1, 0, a1.length - 1);
        System.out.println("---------sort1 after ---------- count:"+COUNT1);
        QuickSort.print(a1);

        System.out.println("---------sort2 before ----------");
        QuickSort.print(a2);
        sort2(a2, 0, a2.length - 1);
        System.out.println("---------sort2 after -------- count:"+COUNT2);
        QuickSort.print(a2);

    }

    /**
     * 1）设置两个变量i、j，排序开始的时候：i=0，j=N-1；
     * 2）以第一个数组元素作为关键数据，赋值给key，即key=A[0]；
     * 3）从j开始向前搜索，即由后开始向前搜索(j--)，找到第一个小于key的值A[j]，而后j停止搜索；
     * 4）从i开始向后搜索，即由前开始向后搜索(i++)，找到第一个大于key的A[i]，将A[i]和A[j]互换；
     * 5）重复第3、4步，直到i=j； (3,4步中，没找到符合条件的值，即3中A[j]不小于key,
     * 4中A[i]不大于key的时候改变j、i的值，使得j=j-1，i=i+1，直至找到为止。
     * 找到符合条件的值，进行交换的时候i， j指针位置不变。
     * 另外，i==j这一过程一定正好是i+或j-完成的时候，此时令循环结束）。
     *
     * @param a
     * @param i
     * @param j
     */
    private static void sort1(Comparable[] a, int i, int j) {
        if (i >= j) {
            return;
        }
        int k = partition1(a, i, j);
        sort1(a, i, k - 1);
        sort1(a, k + 1, j);
    }

    /**
     * 方法1：
     * j: 从后向前找到第一个小于等于key的元素，此时j为该元素的下标，停止搜索；
     * i: 从前向后找到第一个大于等于key的元素，此时i为该元素的下标，停止搜索；
     * 如果i>=j，说明双向搜索已经完成，而且i，j的顺序也是正确的了，需要将key和a[j]交换，
     * 否则说明i，j尚未"碰头"，只需将a[i]和a[j]交换即可。
     * 循环上述步骤，直至j<=i；
     *
     * 关键点：每次交换的是a[i]和a[j]
     * @param a
     * @param start
     * @param end
     * @return
     */
    private static int partition1(Comparable[] a, int start, int end) {
        Comparable key = a[start];
        int i = start, j = end+1;
        while (i < j) {
            while (--j > i && a[j].compareTo(key) >= 0);
            while (++i < j && a[i].compareTo(key) <= 0);
            if (i >= j) {
                QuickSort.swap(a, start, j);
                COUNT1++;
            } else {
                QuickSort.swap(a, i, j);
                COUNT1++;
            }
        }
        return j;
    }

    private static void sort2(Comparable[] a, int i, int j) {
        if (i >= j) {
            return;
        }
        int k = partition2(a, i, j);
        sort2(a, i, k - 1);
        sort2(a, k + 1, j);
    }
    /**
     * 方法 2:
     * 首先将i设置为key的下标，
     * j: 从后向前找到第一个小于key的元素，此时j为该元素的下标，将a[i]（即key）和a[j]进行交换(交换后j为key的下标)，停止；
     * i: 从前向后找到第一个大于key的元素，此时i为该元素的下标，将a[i]和a[j]（即key）进行交换(交换后i为key的下标)，停止；
     *
     * 关键点：每次交换a[x]与key
     * @param a
     * @param start
     * @param end
     * @return
     */
    private static int partition2(Comparable[] a, int start, int end) {
        Comparable key = a[start];
        int i = start, j = end;
        while (j >= i) {
            for (; j > i; j--) {
                if (a[j].compareTo(key) < 0) {
                    QuickSort.swap(a, i, j);
                    COUNT2++;
                    break;
                }
            }
            for (; i <= j; i++) {
                if (a[i].compareTo(key) > 0) {
                    QuickSort.swap(a, i, j);
                    COUNT2++;
                    break;
                }
            }
        }
        return j;
    }

    /**
     * 洗牌（打乱顺序）算法
     * 1. 生成一个原数组长度内的随机数index
     * 2. 将原数组index位置上的元素赋给新数组的第K个位置
     * 3. 原数组最后一个元素赋给将原数组index位置
     * @param arr
     * @return
     */
    public static Comparable[] shuffle(Comparable[] arr) {
        Comparable[] arr2 = new Comparable[arr.length];
        int count = arr.length;
        int index = 0;// 随机位置
        int k = 0;
        Random rand = new Random();
        while (count>0) {
            index = rand.nextInt(count);
            arr2[k++] = arr[index];
            arr[index] = arr[--count];
        }
        return arr2;
    }

    public static void swap(Comparable[] a, int i, int j) {
        Comparable temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void print(Comparable[] a) {
        for (Comparable comparable : a) {
            System.out.print(comparable+",");
        }
        System.out.println();
    }
}