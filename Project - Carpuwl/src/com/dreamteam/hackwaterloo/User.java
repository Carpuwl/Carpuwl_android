package com.dreamteam.hackwaterloo;

import android.app.Application;

public class User extends Application{
	
	private static User instance;
	private int fb_fk;
	private String name;
	private String phone;
	   
	public static void initInstance(int fb_fk, String name, String phone )
	{
		if (instance == null)
	    {
	      // Create the instance
	      instance = new User(fb_fk, name, phone);
	    }
	 }
	 
	 public static User getInstance()
	 {
	   // Return the instance
		 return instance;
	 }
	   
	 private User(int fb_fk, String name, String phone )
	 {
	   // Constructor hidden because this is a singleton
		 this.fb_fk = fb_fk;
		 this.name = name;
		 this.phone = phone;
	 }
	 
	 public int get_fb_fk(){
		 return this.fb_fk;
	 }
	   
	 public String get_name(){
		 return this.name;
	 }
	 
	 public String get_phone(){
		 return this.phone;
	 }
}
