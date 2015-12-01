package list;

import java.util.ArrayList;

import dialog.MainWindow;

public final class ViewList {

private static ArrayList<MainWindow> list;
	
	

	static{
		if (list==null) {
			System.out.println("启动页面成功");
			list=new ArrayList<MainWindow>();
		}
	}
	
	public static void add(MainWindow v){
		
		list.add(v);
	}
	
	public static ArrayList<MainWindow> getList() {
		return list;
	}
	
}
