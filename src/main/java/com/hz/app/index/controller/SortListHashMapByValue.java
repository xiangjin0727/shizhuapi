package com.hz.app.index.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SortListHashMapByValue implements Comparator<HashMap<? extends Object, ? extends Comparable>> {
	private boolean flag;

	public SortListHashMapByValue(boolean flag) {
		this.flag = flag;
	}

	public SortListHashMapByValue() {
		this(true);
	}

	public int compare(HashMap<? extends Object, ? extends Comparable> o1,
			HashMap<? extends Object, ? extends Comparable> o2) {
		Comparable c1 = o1.entrySet().iterator().next().getValue();
		Comparable c2 = o2.entrySet().iterator().next().getValue();
		if (flag) {
			return c1.compareTo(c2);
		} else {
			return c2.compareTo(c1);
		}
	}

	public static void main(String[] args) {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("1", "2018-10-08");
		map.put("2", "111111111");
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		map1.put("1", "2018-10-09");
		map1.put("2", "2222222222");
		HashMap<String, Object> map2 = new HashMap<String,Object>();
		map2.put("1", "2018-11-08");
		map2.put("2", "3333333333");
		list.add(map);
		list.add(map1);
		list.add(map2);
		
		sort(list);
		System.out.println(list);
//		Collections.sort(list, new SortListHashMapByValue(true));
//		List<HashMap<String, Object>> list1 = list;
//		System.out.println(list1);

	}
	
	private static void sort(List<HashMap<String, Object>> data) {
        Collections.sort(data, new Comparator<HashMap>() {
        public int compare(HashMap o1, HashMap o2) {
                String a = (String) o1.get("1");
                String b = (String) o2.get("1");
                //asc
                return a.compareTo(b);
                //desc
                //return b.compareTo(a);
            }
                // 升序
        });
                //return a.compareTo(b);
      }
}
