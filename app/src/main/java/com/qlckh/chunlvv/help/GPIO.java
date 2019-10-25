package com.qlckh.chunlvv.help;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Andy
 * @date   2019/5/30 15:59
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    发送指令的时候需要手动打开高电平，然后发送完指令马上设置回低电平才能读取数据的
 * 			需要操作
 *@see GPIO
 */
public class GPIO {

	public static final String TAG = "allecho_GPIO";

	private static String valuePath;

	static Process process = null;

	static DataOutputStream dos = null;

	public static int gpio_crtl(String pin, int level) {

		valuePath = "echo " + level + " > /sys/class/gpio_sw/" + pin + "/data";
		Log.d(TAG, "" + valuePath + "\n");

		try {

			process = Runtime.getRuntime().exec("/system/bin/sh");

			dos = new DataOutputStream(process.getOutputStream());

			dos.writeBytes(valuePath + "\n");

			dos.flush();

			dos.close();

		} catch (IOException e1) {

			// TODO Auto-generated catch block

			e1.printStackTrace();

		}

		return 0;

	}

}
