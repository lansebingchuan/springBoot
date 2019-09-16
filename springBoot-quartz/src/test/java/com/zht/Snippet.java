package com.zht;

public class Snippet {
	public static String altToSet(String name) {		
		return "set"+name.substring(0, 1).toUpperCase()+name.substring(1);
		
	}
}

