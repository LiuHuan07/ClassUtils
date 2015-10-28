package com.android.liuhuan.utilsclass.design;

/**
 * 饿汉式单例设计模式
 *
 */
public class SingleDesignModelHungry {
	//构造方法私有化
	private SingleDesignModelHungry(){}
	//定义本类的对象
	private static SingleDesignModelHungry single=new SingleDesignModelHungry();
	//向外界提供可以访问的方法，返回当前类的对象
	public static SingleDesignModelHungry getInstance(){
		return single;
	}
}
