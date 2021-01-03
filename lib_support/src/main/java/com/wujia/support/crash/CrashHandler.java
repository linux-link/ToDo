package com.wujia.support.crash;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.NonNull;

import com.wujia.support.AppGlobal;
import com.wujia.support.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrashHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();

    public String CRASH_DIR = "CrashDir";

    private static final CrashHandler CRASH_HANDLER = new CrashHandler();

    private CrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new CaughtExceptionHandler());
    }

    public static CrashHandler getInstance() {
        return CRASH_HANDLER;
    }

    public void init(String dir) {
        CRASH_DIR = dir;
    }

    public File[] crashFiles() {
        return new File(AppGlobal.getApplication().getCacheDir(), CRASH_DIR).listFiles();
    }

    private class CaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Context context = AppGlobal.getApplication();
        private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        private final String LAUNCH_TIME = formatter.format(new Date());
        private final Thread.UncaughtExceptionHandler defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

        @Override
        public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
            try {
                if (!handleException(e) && defaultExceptionHandler != null) {
                    defaultExceptionHandler.uncaughtException(t, e);
                }
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {
                nameNotFoundException.printStackTrace();
            }
            restartApp();
        }

        private void restartApp() {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }


        private boolean handleException(Throwable e) throws PackageManager.NameNotFoundException {
            if (e == null) return false;
            String log = collectDeviceInfo(e);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "handleException" + log);
            }

            saveCrashInfo2File(log);
            return true;
        }

        private void saveCrashInfo2File(String log) {
            File crashDir = new File(CRASH_DIR);
            if (!crashDir.exists()) {
                crashDir.mkdirs();
            }
            File crashFile = new File(crashDir, formatter.format(new Date()) + "-crash.txt");
            try {
                crashFile.createNewFile();
                try (FileOutputStream fos = new FileOutputStream(crashFile)) {
                    fos.write(log.getBytes());
                    fos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**
         * 设备类型、OS本版、线程名、前后台、使用时长、App版本、升级渠道
         * <p>
         * CPU架构、内存信息、存储信息、permission权限
         */
        private String collectDeviceInfo(Throwable e) throws PackageManager.NameNotFoundException {
            StringBuilder sb = new StringBuilder();
            sb.append("brand=${Build.BRAND}\n");// huawei,xiaomi
            sb.append("rom=${Build.MODEL}\n"); //sm-G9550
            sb.append("os=${Build.VERSION.RELEASE}\n");//9.0
            sb.append("sdk=${Build.VERSION.SDK_INT}\n");//28
            sb.append("launch_time=${LAUNCH_TIME}\n");//启动APP的时间
            sb.append("crash_time=${formatter.format(Date())}\n");//crash发生的时间
            sb.append("forground=${ActivityManager.instance.front}\n");//应用处于前后台
            sb.append("thread=${Thread.currentThread().name}\n");//异常线程名
            sb.append("cpu_arch=${Build.CPU_ABI}\n");//armv7 armv8

            //app 信息
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            sb.append("version_code=${packageInfo.versionCode}\n");
            sb.append("version_name=${packageInfo.versionName}\n");
            sb.append("package_name=${packageInfo.packageName}\n");
            sb.append("requested_permission=${Arrays.toString(packageInfo.requestedPermissions)}\n");//已申请到那些权限


            //统计一波 存储空间的信息，
            ActivityManager.MemoryInfo memInfo = new android.app.ActivityManager.MemoryInfo();
            ActivityManager ams = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ams.getMemoryInfo(memInfo);

            sb.append("availMem=${Formatter.formatFileSize(context, memInfo.availMem)}\n");//可用内存
            sb.append("totalMem=${Formatter.formatFileSize(context, memInfo.totalMem)}\n");//设备总内存

            File file = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(file.getPath());
            long availableSize = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
            sb.append("availStorage=")
                    .append(Formatter.formatFileSize(context, availableSize))
                    .append("\n");//存储空间

            Writer write = new StringWriter();
            PrintWriter printWriter = new PrintWriter(write);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            sb.append(write.toString());
            return sb.toString();
        }

    }
}
