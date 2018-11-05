package me.blog.njw1204.studypartner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

public class CUtils {

    public static int DP(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static boolean IsEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static boolean IsPseudoEmpty(String str) { return (str == null || str.length() == 0 || str.equals("null")); }

    public static String NNull(String str) {
        if (str == null) return "";
        else return str;
    }

    public static String NNNull(String str) {
        if (str == null || str.equals("null")) return "";
        else return str;
    }

    public static void SimpleDialogShow(Context context, String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.show();
    }

    public static void SimpleTitleDialogShow(Context context, String title, String msg, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(cancelable);
        builder.show();
    }

    public static void DefaultNetworkFailDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.setTitle("오류");
        builder.setMessage("네트워크 상태가 원활하지 않습니다.");
        builder.setCancelable(false);
        builder.show();
    }

    public static void DefaultNetworkFailDialog(final Activity activity, final boolean afterFinish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (afterFinish) activity.finish();
            }
        });
        builder.setTitle("오류");
        builder.setMessage("네트워크 상태가 원활하지 않습니다.");
        builder.setCancelable(false);
        builder.show();
    }

    public static ProgressDialog showProgress(final Context context, final String msg, boolean cancelable) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(cancelable);
        pd.setMessage(msg);
        pd.getWindow().setDimAmount(0.15f);
        pd.show();
        return pd;
    }

    public static void hideProgress(final ProgressDialog pd) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        }, 160);
    }
}
