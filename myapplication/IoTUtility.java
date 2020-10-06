package com.example.myapplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException; 
import java.nio.ByteOrder; 
import java.util.Enumeration;
import java.util.List; 

import com.example.myapplication.Constants.APP_TYPE;

import android.content.Context; 
import android.net.ConnectivityManager;
import android.net.NetworkInfo; 
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager; 
import android.os.Environment; 


public class IoTUtility { 

	/***************************************************************************/
	/*************************** type casting & parser *************************/
	/***************************************************************************/
	public static String[] get_status(String _msg)
	{  
		String[] _spliter = _msg.split("\\|"); 

		if(_spliter == null || _spliter.length < 2)
			return null;

		if(Integer.parseInt(_spliter[0]) != APP_TYPE.TOY.get_points() && 
				Integer.parseInt(_spliter[1]) != Constants.GET_STATUS)
		{
			return null;
		}
		else
		{
			String[] _spliter2 = _spliter[2].split(",");
			return _spliter2;
		}
	}

	public static boolean is_get_status(String _msg)
	{
		String[] _spliter = _msg.split("\\|"); 

		if(_spliter == null || _spliter.length < 2)
			return false;

		return Integer.parseInt(_spliter[0]) == APP_TYPE.TOY.get_points() && 
				Integer.parseInt(_spliter[1]) == Constants.GET_STATUS;
	}

	public static boolean is_get_setting(String _msg)
	{
		String[] _spliter = _msg.split("\\|");

		if(_spliter == null || _spliter.length < 2)
			return false;

		return Integer.parseInt(_spliter[0]) == APP_TYPE.TOY.get_points() &&
				Integer.parseInt(_spliter[1]) == Constants.GET_SETTING;
	}

	public static boolean is_set_setting(String _msg)
	{
		String[] _spliter = _msg.split("\\|"); 

		if(_spliter == null || _spliter.length < 2)
			return false;

		return Integer.parseInt(_spliter[0]) == APP_TYPE.TOY.get_points() && 
				Integer.parseInt(_spliter[1]) == Constants.SET_SETTING;
	} 


	public static String[] get_setting(String _msg)
	{ 
		String[] _spliter = _msg.split("\\|"); 

		if(_spliter == null || _spliter.length < 2)
			return null;

		if(Integer.parseInt(_spliter[0]) != APP_TYPE.TOY.get_points() &&
				Integer.parseInt(_spliter[1]) != Constants.GET_SETTING)
		{
			return null;
		}
		else
		{
			String[] _spliter2 = _spliter[2].split(",");
			return _spliter2;
		}
	}

	public static String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder();

		for(final byte b: a)
			sb.append(String.format("0x%02X ", b&0xff));

		return sb.toString();
	}

	public static String ByteArrayToHex(Byte[] a) {

		StringBuilder sb = new StringBuilder();
		for(final byte b: a)
			sb.append(String.format("0x%02X ", b&0xff));

		return sb.toString();
	}

	public static String charArrayToHex(char[] a) {
		StringBuilder sb = new StringBuilder(); 

		for(final char b: a)
			sb.append(String.format("0x%02X ", b&0xff));    

		return sb.toString();
	}
	public static String byteArrayToString(byte[] a) {
		StringBuilder sb = new StringBuilder();

		for(final byte b: a)
			sb.append(String.format("%c", b&0xff));

		return sb.toString();
	}

	public static String byteArrayToString(byte[] a, int offset, int length) {
		StringBuilder sb = new StringBuilder();

		for (int i = offset; i < length + offset && i <= a.length; i++) {
			sb.append(String.format("%c", a[i]&0xff));
		}

		return sb.toString();
	}

	public static String charArrayToString(char[] a) {
		StringBuilder sb = new StringBuilder(); 

		for(final char b: a)
			sb.append(String.format("%c", b&0xff));     

		return sb.toString();
	}

	public static byte[] toByteArray(List<Byte> in) {
		final int n = in.size();
		byte ret[] = new byte[n];

		for (int i = 0; i < n; i++) {
			ret[i] = in.get(i);
		}

		return ret;
	}

	/***************************************************************************/
	/********************** IoTUtility Received Data ***************************/
	/***************************************************************************/


	/***************************************************************************/
	/*************************** IoTUtility Commands ***************************/
	/***************************************************************************/

	public static byte[] mkcommand_get_status()
	{ 
		String _message = APP_TYPE.TOY.get_points() + "|" + Constants.GET_STATUS;

		return _message.getBytes();
	}

	public static byte[] mkcommand_get_setting()
	{   
		String _message = APP_TYPE.TOY.get_points() + "|" + Constants.GET_SETTING;

		return _message.getBytes();
	}  

	public static byte[] mkcommand_set_dcmotor (boolean _param)
	{
		String _str = APP_TYPE.TOY.get_points() + "|" + Constants.TOY_DCMOTOR + "|" + (_param ? "1" : "0");
		return _str.getBytes();
	} 

	public static byte[] mkcommand_set_rgbled (boolean _param)
	{
		String _str = APP_TYPE.TOY.get_points() + "|" + Constants.TOY_RGBLED + "|" + (_param ? "1" : "0");
		return _str.getBytes();
	}

	public static byte[] mkcommand_set_light (boolean _param)
	{
		String _str = APP_TYPE.TOY.get_points() + "|" + Constants.TOY_LEDBAR + "|" + (_param ? "1" : "0");
		return _str.getBytes();
	}

	public static byte[] mkcommand_set_feeding_time (String param1, int param2, int param3)
	{
		String _str = APP_TYPE.TOY.get_points() + "|" + Constants.SET_SETTING + "|" + param1+ "," + param2 + "," + param3;
		return _str.getBytes();
	} 

	/***************************************************************************/
	/*************************** file IO *****************************/
	/***************************************************************************/
	public static byte[] readFile(String a_sParentPath, String a_sFileName)
	{
		byte[] bArData = null;
		if(a_sParentPath != null && a_sParentPath.length() > 0)
		{
			File oDatabFolder = new File(a_sParentPath);
			if(oDatabFolder != null && oDatabFolder.exists() == true && oDatabFolder.isDirectory() == true)
			{
				String sFile = a_sParentPath + a_sFileName;

				try {
					FileInputStream oInputStream = new FileInputStream(sFile);
					int nCount = oInputStream.available();
					if(nCount > 0)
					{
						bArData = new byte[nCount];
						oInputStream.read(bArData);
					}

					if(oInputStream != null)
					{
						oInputStream.close();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bArData;
	} 

	public static void writedata(String _filename , String _message)
	{
		FileWriter _filewriter;

		try
		{
			_filewriter = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/" + _filename + ".txt", true);

			BufferedWriter _bufferedwriter = new BufferedWriter(_filewriter);

			_bufferedwriter.write(_message);
			_bufferedwriter.write("\n");

			_bufferedwriter.close();
			_filewriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	public static void writeLog(String _class, String _message)
	{
		FileWriter _filewriter;
		long now = System.currentTimeMillis();
		int miliseconds  = (int) (now % 1000);
		int seconds = (int) ((now/1000)%60);
		int minutes = (int) ((now/60000)%60);
		int hours = (int) ((now/3600000)%24);

		try
		{
			_filewriter = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/Log.txt", true);

			BufferedWriter _bufferedwriter = new BufferedWriter(_filewriter);

			_bufferedwriter.write("===" + String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds) + "." + String.valueOf(miliseconds) + " ["+ _class + "] " + _message + "     "); 
			_bufferedwriter.write("\n");

			_bufferedwriter.close();
			_filewriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	public static void writeFile(String a_sParentPath, String a_sFileName, byte[] _data)
	{ 
		FileOutputStream oOutputStream = null;
		if(a_sParentPath != null && a_sParentPath.length() > 0)
		{
			File oDatabFolder = new File(a_sParentPath);
			if(oDatabFolder != null && oDatabFolder.exists() == true && oDatabFolder.isDirectory() == true)
			{
				String sFile = a_sParentPath + a_sFileName;

				try {
					oOutputStream = new FileOutputStream(sFile);
					oOutputStream.write(_data);
					if(oOutputStream != null)
					{
						oOutputStream.close();
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		} 
	} 

	/***************************************************************************/
	/*************************** network *****************************/
	/***************************************************************************/
	public final static int INET4ADDRESS = 1;
	public final static int INET6ADDRESS = 2;

	public static String getLocalIpAddress(int type) {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();en.hasMoreElements();) {
				NetworkInterface intf = ( NetworkInterface ) en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = ( InetAddress ) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						switch (type) {
						case INET6ADDRESS:
							if (inetAddress instanceof Inet6Address) {
								return inetAddress.getHostAddress().toString();
							}
							break;
						case INET4ADDRESS:
							if (inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress().toString();
							}
							break;
						} 
					} 
				} 
			} 
		} catch (SocketException ex) { 
		} 
		return null;
	}

	//only support IPv4
	public static String wifiIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		if(!wifiManager.isWifiEnabled())
			return null;

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();

		if(wifiInfo == null)
			return null;

		int ipAddress = wifiInfo.getIpAddress();

		if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
			ipAddress = Integer.reverseBytes(ipAddress);
		}

		byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

		String ipAddressString;
		try {
			ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
		} catch (UnknownHostException ex) { 
			ipAddressString = null;
		}

		return ipAddressString;
	}

	public static boolean is_wifi_on(Context context)
	{
		ConnectivityManager wManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 

		NetworkInfo nInfo = wManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		if(nInfo == null)
			return false;

		return nInfo.isConnectedOrConnecting(); 
	}

	public static String get_ssid(Context context)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		if(!wifiManager.isWifiEnabled())
			return null;

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();

		if(wifiInfo == null)
			return null;

		return wifiInfo.getSSID(); 
	}

	public static String get_bssid(Context context)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		if(!wifiManager.isWifiEnabled())
			return null;

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();

		if(wifiInfo == null)
			return null;

		return wifiInfo.getBSSID(); 
	} 
}
