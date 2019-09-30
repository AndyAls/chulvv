package com.qlckh.chunlvv.receivier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.qlckh.chunlvv.intelligent.IntelligentLuanchActivity

/**
 * @author Andy
 * @date   2019/6/3 14:00
 * Desc:
 */
class RootBroastReceiver :BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        if (Intent.ACTION_BOOT_COMPLETED==intent!!.action){
            val startIntent = Intent(context, IntelligentLuanchActivity::class.java).apply {
                action = "android.intent.action.MAIN"
                addCategory(Intent.CATEGORY_LAUNCHER)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context!!.startActivity(startIntent)
        }

    }
}