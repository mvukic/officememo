package ruazosa.hr.fer.officememo.Messaging

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.auth.FirebaseAuth
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import ruazosa.hr.fer.officememo.View.MainActivity
import android.content.Intent
import android.support.v7.app.NotificationCompat
import io.reactivex.rxkotlin.toSingle
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import ruazosa.hr.fer.officememo.R
import ruazosa.hr.fer.officememo.Utils.GlobalData


class OMFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "FB_MESSSAGE"
    private var showNotification = true
    lateinit var map:Map<String,Any>

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if(remoteMessage == null) return;
        Log.d(TAG, "From: " + remoteMessage.from)

        if (remoteMessage.getData().isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData())
            map = remoteMessage.getData()

            val postId = map["pid"]
            val departmentId = map["did"]
            val userId = map["uid"]

            // Comment below for enabling notifications from myself
            showNotification = GlobalData.user.uid == userId
        }
        if (!showNotification) {
            val resultIntent = intentFor<MainActivity>(
                    "pid" to map["pid"],
                    "did" to map["did"],
                    "uid" to map["uid"]
            ).singleTop()

            val resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val mBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_sms_black_24dp)
                    .setContentTitle(remoteMessage.notification.title)
                    .setContentText(remoteMessage.notification.body)
                    .setContentIntent(resultPendingIntent)

            val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotifyMgr.notify(1889, mBuilder.build())
        }
    }
}