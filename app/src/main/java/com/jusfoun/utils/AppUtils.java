package com.jusfoun.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.google.gson.Gson;
import com.jusfoun.constants.CommonConstant;
import com.jusfoun.constants.PreferenceConstant;
import com.jusfoun.giftexchange.R;
import com.jusfoun.ui.activity.BaseActivity;
import com.jusfoun.ui.activity.LoginActivity;
import com.jusfoun.ui.activity.SelectGiftActivity;
import com.jusfoun.ui.activity.WebActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cn.qqtheme.framework.picker.DatePicker;

import static android.app.Activity.RESULT_OK;
import static com.jusfoun.app.MyApplication.context;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE;
import static com.jusfoun.constants.CommonConstant.GIFT_TYPE_QRCODE;
import static com.jusfoun.constants.CommonConstant.REQ_CAPTURE;

public class AppUtils {

    /**
     * 新浪微博包名
     */
    public static final String PACKAGE_NAME_SINA_WEIBO = "com.sina.weibo";
    /**
     * qq包名
     */
    public static final String PACKAGE_NAME_TENCENT_QQ = "com.tencent.mobileqq";
    /**
     * 人人网包名
     */
    public static final String PACKAGE_NAME_RENREN = "com.renren.mobile.android";
    /**
     * 微信包名
     */
    public static final String PACKAGE_NAME_WEIXIN = "com.tencent.mm";
    /**
     * 开心网包名
     */
    public static final String PACKAGE_NAME_KAIXIN = "com.kaixin001.activity";

    /**
     * ScrollView和ListView冲突解决方法 在ListView.setAdapter()后调用该方法就能解决冲突
     */
    public static void dealWithScrolListView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // adapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 打开拨号界面
     */
    public static void intentDial(Context context, String phone) {
        intentAction(context, "tel:" + phone, Intent.ACTION_DIAL);
    }

    /**
     * 不打开拨号界面，直接拨号
     */
    public static void intentCall(Context context, String phone) {
        intentAction(context, "tel:" + phone, Intent.ACTION_CALL);
    }

    /**
     * 打开一个网页
     */
    public static void intentWebUrl(Context context, String url) {
        intentAction(context, url, Intent.ACTION_VIEW);
    }

    /**
     * 发送短信，不会进入短信编辑页面，需要android.permission.SEND_SMS权限
     */
    public static void sendMsg(String phone, String text) {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(phone, null, text, null, null);
    }

    /**
     * 发送短信，不会进入短信编辑页面，需要android.permission.SEND_SMS权限
     */
    public static void sendMsg(Context context, String phone, String text) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        /** 用于附带短信内容，可不加 */
        sendIntent.putExtra("sms_body", text);
        context.startActivity(sendIntent);
    }

    /**
     * 发送彩信
     *
     * @param context     附件的uri
     * @param subject     彩信的主题
     * @param phoneNumber 彩信发送目的号码
     * @param text        彩信中文字内容
     */
    public static void sendMMS(Context context, String imagePath, String subject, String phoneNumber, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
        intent.putExtra("subject", subject);
        intent.putExtra("address", phoneNumber);
        intent.putExtra("sms_body", text);
        intent.putExtra(Intent.EXTRA_TEXT, "it's EXTRA_TEXT");
        intent.setType("image/*");
        intent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
        context.startActivity(intent);
    }

    private static void intentAction(Context context, String value, String action) {
        try {
            Uri uri = Uri.parse(value);
            Intent it = new Intent(action, uri);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本名
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取应用包名
     */
    public static String getPackageName(Context context) {
        String packageName = "";
        try {
            packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    /**
     * 自动弹出然键盘<br>
     */
    public static void showSoftInput(final Context context, final View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isSoftboardShowing(Activity context) {
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            return true;
        return false;
    }

    /**
     * 获取经纬度 两点之间的距离
     */
    public static double getDistatce(double lat1, double lat2, double lon1, double lon2) {
        double R = 6371;
        double distance = 0.0;
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R;
        return distance;
    }

    /**
     * 需要android.permission.WRITE_EXTERNAL_STORAGE权限
     *
     * @return Uri 在onActivityResult中，如果uri不为null,则表示拍照的图片Uri
     */
    public static void takePhoto(Activity activity, int requestCode, String imagePaths) {
        activity.startActivityForResult(getTakePhotoIntent(imagePaths), requestCode);
    }

    /**
     * 需要android.permission.WRITE_EXTERNAL_STORAGE权限
     *
     * @return Uri 在onActivityResult中，如果uri不为null,则表示拍照的图片Uri
     */
    public static void takePhoto(Fragment fragment, int requestCode, String imagePaths) {
        fragment.startActivityForResult(getTakePhotoIntent(imagePaths), requestCode);
    }

    private static Intent getTakePhotoIntent(String imagePaths) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imagePaths)));
        return intent;
    }

    /**
     * 从相册中选择图片
     *
     * @param requestCode 选择的图片Uri，在onActivityResult中使用data.getData();获取
     */
    public static void pickPhoto(Activity activity, int requestCode) {
        activity.startActivityForResult(getPickPhotoIntent(), requestCode);
    }

    public static void pickPhoto(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getPickPhotoIntent(), requestCode);
    }

    private static Intent getPickPhotoIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    /**
     * 保留有效位数
     *
     * @param value
     * @param length 有效位长度, 6位以内是保证精度不损失的.
     * @return
     */
    public static String parseDouble(double value, int length) {
        return String.format("%." + length + "f", value);
    }

    /**
     * 是否已经创建快捷方式
     *
     * @param context
     * @param name
     * @param authority 主机名（或叫Authority） 需要通过getAuthorityFromPermission(context,
     *                  "com.android.launcher.permission.READ_SETTINGS"
     *                  );获取返回值，并保存起来，下次判断从文件、数据库中读取<br>
     *                  循环遍历了已经安装的应用，而后找出你本机对应的launcher的包名
     */
    public static boolean hasShortcut(Context context, String name, String authority) {
        String url = "";
        if (authority == null) {
            if (getSystemVersion() < 8) {
                url = "content://com.android.launcher.settings/favorites?notify=true";
            } else {
                url = "content://com.android.launcher2.settings/favorites?notify=true";
            }
        } else
            url = "content://" + authority + "/favorites?notify=true";

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{name}, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }

        return false;
    }

    /**
     * 创建快捷方式
     *
     * @param activity
     * @param iconRes  快捷方式显示的图片资源ID
     * @param name     快捷方式上显示的内容
     *                 <p/>
     *                 <b>注意</b><br>
     *                 需要权限：<br>
     *                 com.android.launcher.permission.INSTALL_SHORTCUT<br>
     *                 com.android.launcher.permission.READ_SETTINGS
     */
    public static void createShortcut(Activity activity, int iconRes, String name) {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        shortcut.putExtra("duplicate", false); // 不允许重复创建

        // 写在程序后自动删除快捷方式
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.addCategory("android.intent.category.LAUNCHER");
        shortcutIntent.setClassName(activity, activity.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        // 快捷方式的图标
        ShortcutIconResource shortcutIconResource = ShortcutIconResource.fromContext(activity, iconRes);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIconResource);

        activity.sendBroadcast(shortcut);
    }

    /**
     * 创建快捷方式,以应用名称作为快捷方式的名称
     *
     * @param activity
     * @param iconRes  快捷方式显示的图片资源ID
     *                 <p/>
     *                 <b>注意</b><br>
     *                 需要权限：<br>
     *                 com.android.launcher.permission.INSTALL_SHORTCUT<br>
     *                 com.android.launcher.permission.READ_SETTINGS
     */
    public static void createShortcut(Activity activity, int iconRes) {
        createShortcut(activity, iconRes, getApplicationName(activity));
    }

    /**
     * 删除快捷方式
     *
     * @param activity
     * @param name     快捷方式的名称 <b>注意</b><br>
     *                 需要权限：<br>
     *                 com.android.launcher.permission.UNINSTALL_SHORTCUT
     */
    public static void removeShortcut(Activity activity, String name) {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        String appClass = activity.getPackageName() + "." + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

        activity.sendBroadcast(shortcut);

    }

    /**
     * 获取系统的SDK版本号<br>
     * 1 ---> The original, first, version of Android.<br>
     * 2 ---> First Android update, officially called 1.1.<br>
     * 3 ---> Android 1.5.<br>
     * 5 ---> Android 1.6.<br>
     * 6 ---> Android 2.0.1.<br>
     * 7 ---> Android 2.1.<br>
     * 8 ---> Android 2.2.<br>
     * 9 ---> Android 2.3.<br>
     * 10 ---> Android 2.3.3.<br>
     * 11 ---> Android 3.0.<br>
     * 12 ---> Android 3.1.<br>
     * 13 ---> Android 3.2.<br>
     * 14 ---> Android 4.0.<br>
     * 15 ---> Android 4.0.3.<br>
     * 16 ---> Android 4.1.<br>
     */
    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断当前系统版本是不是低于6
     *
     * @return
     */
    public static boolean isAppVersionBelowM() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    public static String getSystemRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 将域名解析成IP
     */
    public static String getIpByHost(String host) {
        String IPAddress = null;
        InetAddress ReturnStr1 = null;
        try {
            ReturnStr1 = InetAddress.getByName(host);
            IPAddress = ReturnStr1.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return IPAddress;
        }
        return IPAddress;
    }

    /**
     * 获取Key Hash
     */
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            String packageName = context.getApplicationContext().getPackageName();
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 通过其他应用的包名启动该应用
     *
     * @param context
     * @param packageName
     */
    public static boolean openAppByPackageName(Context context, String packageName) {
        try {
            if (!TextUtils.isEmpty(packageName)) {
                if (isAppInstalled(context, packageName)) {
                    PackageManager pm = context.getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(packageName);
                    if (intent != null) {
                        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
                        if (list != null) {
                            // 如果这个Intent有1个及以上应用可以匹配处理，则选择第一个匹配的处理，防止选择处理类ResolverActivity缺失导致异常崩溃
                            if (list.size() > 0) {
                                ResolveInfo ri = list.iterator().next();
                                if (ri != null) {
                                    ComponentName cn = new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name);
                                    Intent launchIntent = new Intent();
                                    launchIntent.setComponent(cn);
                                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(launchIntent);
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    // 该应用已卸载
                }
            }
        } catch (Exception e) {
            // 该应用已卸载
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 对参数值做md5加密
     */
    public static String md5Encrypt(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes("utf8"));

            byte[] bytes = md.digest();
            StringBuilder ret = new StringBuilder(bytes.length << 1);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
                ret.append(Character.forDigit(bytes[i] & 0xf, 16));
            }
            return ret.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定apk路径进行安装
     *
     * @param apkPath apk的路径
     */
    public static void installAPK(Context context, String apkPath) {
        File apkfile = new File(apkPath);
        if (!apkfile.exists()) {
            LogUtils.e("Log", apkPath + " not exist.");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 禁止换行
     */
    public static void forbidEditEnter(EditText editText) {
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }

    /**
     * 在后台运行
     */
    public static void runInBack(Context context) {
        PackageManager pm = context.getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);
        ActivityInfo ai = homeInfo.activityInfo;
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(startIntent);
        } catch (Exception e) {

        }
    }

    /**
     * 输入框设置明文，密文
     *
     * @param input
     * @param visible true:明文； false：密文
     */
    public static void setPasswordVisible(EditText input, boolean visible) {
        int position = input.getSelectionStart();
        if (visible)
            input.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        else
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        input.setSelection(position);
    }

    public static String getAssetDirectory() {
        return "file:///android_asset/";
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


    /**
     * @param content
     * @return
     */

    /**
     * 绑定手机输入框，不能输入以0开头的数据
     */
    public static void bindEditForbidStartZero(final EditText text) {
        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence val, int arg1, int arg2, int arg3) {
                if (val.toString().length() == 1 && "0".equals(val.toString()))
                    text.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }


    /* 判断应用是否在前台 */
    public static boolean isForeground(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (topActivity.getPackageName().equals(context.getPackageName())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LogUtils.e("判断应用在前端、后台运行时报错了");
            e.printStackTrace();
        }
        return true;
    }

    /**
     * unicode 转换成 utf-8
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }


    /**
     * 判断app在前端还是在后台运行。true：后台；false：前端
     */
    public static boolean isAppRunInBack(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Activity最外层的控件
     *
     * @param activity
     * @return
     */
    public static View getActivityRootView(Activity activity) {
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (rootView == null)
            rootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        return rootView;
    }


    /**
     * 获取后缀，不包括.符号
     */
    public static String getSuffix(String uri) {
        try {
            return uri.substring(uri.lastIndexOf(".") + 1, uri.length());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 流量格式转化
     *
     * @param size
     * @return
     */
    public static String byteToMB(long size) {
        long kb = 1024;
        long mb = kb << 10;
        long gb = mb << 10;
        if (size >= gb)
            return parseDouble((float) size / gb, 2) + "G";
        else if (size >= mb)
            return parseDouble((float) size / mb, 2) + "M";
        else if (size > kb)
            return parseDouble((float) size / kb, 2) + "K";
        else
            return parseDouble((float) size, 2) + "B";
    }

    /**
     * @param context
     * @param text
     */
    public static void shareText(Context context, String text) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    public static void shareImage(Context context, String path) {
        Uri imageUri = Uri.fromFile(new File(path));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    public static String[] list2Array(List<String> list) {
        if (list != null && list.size() > 0) {
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i);
            }
            return arr;
        }
        return null;
    }


    public static List<String> array2List(String[] arr) {
        if (arr != null && arr.length > 0) {
            List<String> list = new ArrayList<>();
            for (String value : arr) {
                list.add(value);
            }
            return list;
        }
        return null;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static List getTestList(Class<?> clazz, int size) {
        try {
            List list = new ArrayList<>();
            for (int i = 0; i < size; i++)
                list.add(clazz.newInstance());
            return list;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置下划线
     *
     * @param textView
     */
    public static void setTextViewUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    /**
     * 设置中划线
     *
     * @param textView
     */
    public static void setTextViewCenterLine(TextView textView) {
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
    }

    private static boolean isInView(EditText v, MotionEvent event) {
        int[] leftTop = {0, 0};
        //获取输入框当前的location位置
        v.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();
        // 点击的是输入框区域，保留点击EditText的事件
        if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom)
            return true;
        return false;
    }

    /**
     * 通过WifiManager获取的ip地址是整形的，需要转换成我们通常的ip格式
     *
     * @param ip
     */
    public static String parseIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }

    /**
     * 判断当前设备是手机还是平板，
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        int height = 0;
        // 方法一
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        height = frame.top;

        // 方法二
        if (height == 0) {
            Class<?> c = null;
            Object obj = null;
            Field field = null;
            int x = 0, sbar = 38;//默认为38，貌似大部分是这样的

            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                sbar = activity.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
            height = sbar;
        }

        // 方法三
        if (height == 0) {
            try {
                Class c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                int y = activity.getResources().getDimensionPixelSize(x);
                height = y;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return height;
    }

    /**
     * 跳转到权限管理页面
     */
    public static void goPrivilegePage(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 是否有虚拟键盘
     *
     * @param context
     * @return
     */
    public static boolean isHaveVertualKeyboard(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;

    }

    /**
     * 获取CPU架构类型
     *
     * @return
     */
    public static String getCPU() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.SUPPORTED_ABIS != null && Build.SUPPORTED_ABIS.length > 0) {
            return Build.SUPPORTED_ABIS[0];
        }
        return Build.CPU_ABI;
    }

    /**
     * 设置虚拟键盘是否显示
     *
     * @param activity
     * @param show
     */
    public static void setVertualKeyboardStatus(Activity activity, boolean show) {
        if (!show) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取AndroidManifest.xml中，<meta-data>元素，
     *
     * @param name
     * @return
     */
    public static String getMetaDataValue(Activity activity, String name) {
        try {
            ApplicationInfo info = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void goWebActivityForFlow(String no) {
        WebActivity.startActivity(context, "查看物流", context.getResources().getString(R.string.host) + "html/html/logistics.html?nu=" + no);
    }

    public static void goWebActivityForGift(String url) {
        WebActivity.startActivity(context, "礼品详情", url);
    }

    public static void goWebActivityRule() {
        WebActivity.startActivity(context, "兑奖规则", context.getResources().getString(R.string.host) + "html/html/rules.html");
    }

    public static void goWebActivity(Context mContext, String title, String url) {
        WebActivity.startActivity(mContext, title, url);
    }


    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 解析扫码得到的内容
     *
     * @param data
     * @return
     */
    public static String getCaptureResult(Intent data) {
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    return bundle.getString(CodeUtils.RESULT_STRING);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.show("二维码解析失败");
                }
            }
        }
        return null;
    }

    public static void logout(Activity activity) {
        PreferenceUtils.setString(activity, PreferenceConstant.PHONE, null);
        PreferenceUtils.setInt(activity, PreferenceConstant.TYPE, CommonConstant.TYPE_USER);
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        ((BaseActivity) activity).delayFinish();
    }

    public static boolean isLogin() {
        String phone = PreferenceUtils.getString(context, PreferenceConstant.PHONE);
        return !TextUtils.isEmpty(phone);
    }

    public static <T> List<T> randomList(List<T> sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return sourceList;
        }

        List<T> randomList = new ArrayList<>(sourceList.size());
        do {
            int randomIndex = Math.abs(new Random().nextInt(sourceList.size()));
            randomList.add(sourceList.remove(randomIndex));
        } while (sourceList.size() > 0);

        return randomList;
    }

    public static DatePicker showDateDialog(Activity activity, DatePicker.OnYearMonthPickListener listener) {
        DatePicker picker = new DatePicker(activity, DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 1.0));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        picker.setRangeStart(2000, 1, 1);
        picker.setRangeEnd(year, month, 1);
        picker.setSelectedItem(year, month);
        picker.setOnDatePickListener(listener);
        return picker;
    }

    /**
     * 格式化卡号， 4位加空格
     *
     * @param no
     * @return
     */
    public static String formatCardNo(String no) {
        if (!TextUtils.isEmpty(no) && no.length() > 4) {
            StringBuffer soure = new StringBuffer(no);

            StringBuffer sb = new StringBuffer();
            do {
                sb.append(soure.substring(0, 4) + " ");
                soure.delete(0, 4);
            } while (soure.length() >= 4);

            if (soure.length() > 0)
                sb.append(soure.toString());
            return sb.toString();
        }
        return no;
    }

    public static List<?> getList(JSONObject obj, String listKey, Class<?> clazz) {
        List list = new ArrayList<>();
        try {
            if (obj.has(listKey)) {
                JSONArray arr = obj.getJSONArray(listKey);
                if (arr != null && arr.length() > 0) {
                    Gson gson = new Gson();
                    for (int i = 0; i < arr.length(); i++) {
                        list.add(gson.fromJson(arr.getString(i), clazz));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void onActivityResultForCard(Context context, int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQ_CAPTURE && data != null) {
            String result = data.getStringExtra(CodeUtils.RESULT_STRING);
            Intent intent = new Intent(context, SelectGiftActivity.class);
            intent.putExtra("qrcode", result);
            intent.putExtra(GIFT_TYPE, GIFT_TYPE_QRCODE);
            context.startActivity(intent);
        }
    }

    /**
     * @param date 格式为 2017-02
     * @return
     */
    public static String getLastDayOfMonth(String date) {
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    public static String parseCode(String code) {
        code = URLDecoder.decode(code);
        try {
            String deurl = URLDecoder.decode(code, "UTF-8");
            Uri uri = Uri.parse(deurl);
            return uri.getQueryParameter("card");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return code;
    }

}