package com.android.liuhuan.utilsclass.design;

/**
 * 懒汉式单例设计模式
 *
 */
public class SingleDesignModelLazy {
	//构造方法私有化
	private SingleDesignModelLazy(){}
	//定义本类的对象
	private static SingleDesignModelLazy single;
	//向外界提供可以访问的方法，返回当前类的对象
	public static SingleDesignModelLazy getInstance(){
		if(single==null)
			single=new SingleDesignModelLazy();
		return single;
	}
}
