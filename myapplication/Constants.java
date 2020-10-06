package com.example.myapplication;

public class Constants {
	public static final String RECEIVED_DATA	= "received data";
	public static final String RECEIVED_DATA_SIZE = "received data size";
	public static final String RECEIVED_LOG =  "received log";
	public static final int RECEIVE	= 2;
	public static final int UB_LOG	= 3;
	
	public static final int GET_STATUS									=  0x101;
	public static final int GET_SETTING									=  0x102;
	public static final int SET_SETTING									=  0x103;
	public static final int GET_HISTORY                                 =  0x104;

	public static final int TOY_LEDBAR                              =   0x111;
	public static final int TOY_DCMOTOR								=  0x112;
	public static final int TOY_RGBLED								=  0x113;

	
	public static final int DELAY_TIME									= 1000;
	
	/*********BoardCaast InstantFilter**********/ 
	public static final String INTENTFILTER_CONNECTION					= "intentfilter_Connection";
	public static final String INTENTEXTRA_CONNECT						= "intentextra_Connect";
	public static final String INTENTEXTRA_DISCONNECT					= "intentextra_Disconnect";
	
	public static final String INTENTFILTER_DATA						= "intentfilter_Data";
	public static final String INTENTEXTRA_RECEIVED_DATA				= "intentextra_Received_Data";
	 
	public static enum APP_TYPE {
		FARM("farm", (byte)0),
		SECRET("secret", (byte)1),
		FIRE("fire", (byte)2),
		SMART_HOME("smart_home", (byte)3),
		TOY("toy", (byte)4),
		PET("pet", (byte)5);
 
	    private final String name;
	    private final byte point;

	    private APP_TYPE(String name, byte point)
	    {
	        this.name = name;
	        this.point = point;
	    }
	    
	    byte get_points()
	    {
	    	return this.point;
	    }
	    
	    String get_name()
	    {
	    	return this.name;
	    }
	}
 
}
