package com.android.liuhuan.utilsclass.sort;

public class ArraySort {
	// 冒泡排序：比较相邻的两个数，小的在左边，大的在右边
	public static void bubbleSort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) { // 比较的趟数
			for (int j = 0; j < a.length - 1 - i; j++) { // 每一趟比较的次数
				if (a[j] > a[j + 1]) {
					int t = a[j];
					a[j] = a[j + 1];
					a[j + 1] = t;
				}
			}
		}
	}

	// 选择排序
	public static void selectSort1(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] > a[j]) {
					int t = a[i];
					a[i] = a[j];
					a[j] = t;
				}
			}
		}
	}

	// 选择排序---增强版 效率高
	public static void selectSort(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			int minIndex = i; // 最小值的下标
			for (int j = i + 1; j < a.length; j++) {
				if (a[minIndex] > a[j]) {
					minIndex = j;
				}
			}
			// 循环结束
			if (minIndex != i) {
				int t = a[i];
				a[i] = a[minIndex];
				a[minIndex] = t;
			}
		}
	}

	// 插入排序：默认左边的数是有序的
	public static void insertSort(int[] a) {
		for (int i = 1; i < a.length; i++) {
			for (int j = i; j > 0; j--) {
				if (a[j] < a[j - 1]) {// 保证左边的为最小的数
					int t = a[j];
					a[j] = a[j - 1];
					a[j - 1] = t;
				} else {
					break;// 如果当前值比左边的值大，则不需要比较
				}
			}
		}
	}
}
