package com.example.lifecareapplication.common;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.lifecareapplication.R;

import java.util.List;

public class CommonUtility {

    private static CustomProgressDialog mProgressDialog;

    public static final String TAG = CommonUtility.class.getSimpleName();

    public static boolean isInternetAvailable(Context context) {
        if (context==null)
            return false;
        boolean isConnection = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int index = 0; index < info.length; index++) {
                    if (info[index].getState() == NetworkInfo.State.CONNECTED) {
                        isConnection = true;
                        break;
                    }
                }
            }
        }
        return isConnection;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void showCustomProgressDialog(Context context, String title,
                                                String mesg) {
        try {
            if ((isActivityRunning(context) && mProgressDialog == null)
                    || (mProgressDialog != null && mProgressDialog.isShowing() == false)) {
                Log.d(TAG, "I am in showCustomProgressDialog if");
                mProgressDialog = new CustomProgressDialog(context, R.style.MyAlertDialogStyle, mesg);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isActivityRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager
                .getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(
                    task.baseActivity.getPackageName()))
                return true;
        }

        return false;
    }

    public static void cancelProgressDialog() {

        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                Log.d(TAG, "I am in cancelProgressDialog if");
                mProgressDialog.cancel();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
