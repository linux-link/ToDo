package com.wujia.support.crash;

import com.wujia.support.AppGlobal;

import org.devio.as.proj.libbreakpad.NativeCrashHandler;

import java.io.File;

public class CrashManager {

    public static final CrashManager CRASH_MANAGER = new CrashManager();

    private static final String CRASH_DIR_JAVA = "java_crash";
    private static final String CRASH_DIR_NATIVE = "native_crash";

    private CrashManager() {

    }

    public void init() {
        File javaCrashDir = getJavaCrashDir();
        File nativeCrashDir = getNativeCrashDir();

        CrashHandler.getInstance().init(javaCrashDir.getAbsolutePath());
        NativeCrashHandler.init(nativeCrashDir.getAbsolutePath());
    }

    private File getJavaCrashDir() {
        File javaCrashFile = new File(AppGlobal.getApplication().getCacheDir(), CRASH_DIR_JAVA);
        if (!javaCrashFile.exists()) {
            javaCrashFile.mkdirs();
        }
        return javaCrashFile;
    }

    private File getNativeCrashDir() {
        File nativeCrashFile = new File(AppGlobal.getApplication().getCacheDir(), CRASH_DIR_NATIVE);
        if (!nativeCrashFile.exists()) {
            nativeCrashFile.mkdirs();
        }
        return nativeCrashFile;
    }

    public File[] crashFiles() {
        File[] javaFiles = getJavaCrashDir().listFiles();
        File[] nativeFiles = getNativeCrashDir().listFiles();
        File[] result = new File[javaFiles.length + nativeFiles.length];
        for (int i = 0; i < result.length; i++) {
            if (i >= javaFiles.length - 1) {
                result[i] = nativeFiles[i];
            } else {
                result[i] = javaFiles[i];
            }
        }
        return result;
    }
}
