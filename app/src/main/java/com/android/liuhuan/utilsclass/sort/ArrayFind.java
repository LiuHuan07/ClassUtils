package com.android.liuhuan.utilsclass.sort;

public class ArrayFind {
	//顺序查找
	public static int find(int[] a,int key){
		for(int i=0;i<a.length;i++){
			if(a[i]==key){ //找到了，返回下标
				return i;
			}
		}
		return -1;
	}

	/*
	二分法查找：前提：必须数组有序
	确定中间位置：
	*/
	static  int binaryFind(int[] a,int key){
		int low = 0 ;
		int high = a.length - 1;
		int mid = 0;
		while(low<=high){
			mid = (low+high)/2;
			if(a[mid]>key){ //左边区域查找
				high = mid - 1;
			}
			else if(a[mid]<key){ //右边区域查找
				low = mid + 1;			
			}
			else{
				return mid; //找到了
			}
		}
		return  -1;  //没有找到
	}
}
