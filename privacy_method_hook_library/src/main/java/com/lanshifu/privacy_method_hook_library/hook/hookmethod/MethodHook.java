package com.lanshifu.privacy_method_hook_library.hook.hookmethod;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.lanshifu.asm_annotation.AsmMethodOpcodes;
import com.lanshifu.asm_annotation.AsmMethodReplace;
import com.lanshifu.privacy_method_hook_library.hook.CheckCacheAndPrivacyResult;
import com.lanshifu.privacy_method_hook_library.hook.PrivacyUtilKt;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.BlueToothAdapterHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.SensorHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.SensorManagerHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.SettingsSecureHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.SettingsSystemHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.TelephonyManagerHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.WifiInfoHook;
import com.lanshifu.privacy_method_hook_library.hook.hookmethod.WifiManagerHook;
import com.lanshifu.privacy_method_hook_library.log.LogUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Objects;

/**
 * @author lanxiaobin
 * @date 2022/10/11
 * <p>
 * 对于通过反射调用隐私API，在这里进行Hook，遇到隐私API，就重定向到各个Hook类，否则放行。
 * 由于可变参数args 用kotlin写有点问题，所以这是一个Java类~
 */
@SuppressWarnings("deprecation")
public class MethodHook {

    private final static String TAG = "Method#invoke";
    private final static boolean showInvokeLog = false;

    @AsmMethodReplace(
            oriClass = Method.class,
            oriAccess = AsmMethodOpcodes.INVOKEVIRTUAL
    )
    public static Object invoke(Method method, Object obj, Object[] args, String callerClassName) throws InvocationTargetException, IllegalAccessException {

        String className = method.getDeclaringClass().getName();
        if (obj != null) {
            // 如果是Class对象，直接getName，否则getClass().getName
            if (obj instanceof Class) {
                className = ((Class) obj).getName();
            } else {
                className = obj.getClass().getName();
            }
        }

        if(showInvokeLog){
            StringBuilder methodName = new StringBuilder();
            methodName.append(className)
                    .append("#")
                    .append(method.getName())
                    .append("(");
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    methodName.append(args[i]);
                    if (i != args.length - 1) {
                        methodName.append(",");
                    }
                }
            }
            methodName.append(")")
                    .append(",callerClassName=")
                    .append(callerClassName);
            LogUtil.INSTANCE.d(TAG, methodName.toString());
        }


        //保存一下2个参数，方便使用
        Object param1 = null;
        Object param2 = null;
        if (args != null) {
            if (args.length > 0) {
                param1 = args[0];
            }
            if (args.length > 1) {
                param2 = args[1];
            }
        }
        //隐私API重定向
        switch (className) {
            case "android.app.ActivityManager":
                switch (method.getName()) {
                    case "getRunningAppProcesses":
                        return ActivityManagerHook.getRunningAppProcesses((ActivityManager) Objects.requireNonNull(obj), callerClassName);
                    case "getRunningServices":
                        if (param1 instanceof Integer) {
                            return ActivityManagerHook.getRunningServices((ActivityManager) Objects.requireNonNull(obj), (int) param1, callerClassName);
                        }
                    case "getRecentTasks":
                        if (param1 instanceof Integer && param2 instanceof Integer) {
                            return ActivityManagerHook.getRecentTasks((ActivityManager) Objects.requireNonNull(obj), (int) param1, (int) param2, callerClassName);
                        }
                    case "getRunningTasks":
                        if (param1 instanceof Integer) {
                            return ActivityManagerHook.getRunningTasks((ActivityManager) Objects.requireNonNull(obj), (int) param1, callerClassName);
                        }
                }
                break;

            case "android.bluetooth.BluetoothAdapter":
                switch (method.getName()) {
                    case "getAddress":
                        return BlueToothAdapterHook.getAddress((BluetoothAdapter) Objects.requireNonNull(obj), callerClassName);
                    case "getName":
                        return BlueToothAdapterHook.getName((BluetoothAdapter) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.bluetooth.BluetoothDevice":
                switch (method.getName()) {
                    case "getAddress":
                        return BlueToothDeviceHook.getAddress((BluetoothDevice) Objects.requireNonNull(obj), callerClassName);
                    case "getName":
                        return BlueToothDeviceHook.getName((BluetoothDevice) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.content.ClipboardManager":
                if ("getPrimaryClip".equals(method.getName())) {
                    return ClipboardManagerHook.getPrimaryClip((ClipboardManager) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.location.Location":
                switch (method.getName()) {
                    case "getLongitude":
                        return LocationHook.getLongitude((Location) Objects.requireNonNull(obj), callerClassName);
                    case "getLatitude":
                        return LocationHook.getLatitude((Location) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.location.LocationManager":
                if ("getLastKnownLocation".equals(method.getName())) {
                    if (param1 instanceof String) {
                        return LocationManagerHook.getLastKnownLocation((LocationManager) Objects.requireNonNull(obj), (String) param1, callerClassName);
                    }
                }
                break;

            case "java.net.NetworkInterface":
                if ("getHardwareAddress".equals(method.getName())) {
                    return NetworkInterfaceHook.getHardwareAddress((NetworkInterface) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.app.ApplicationPackageManager":
                switch (method.getName()) {
                    case "getInstalledPackages":
                        if (param1 instanceof Integer) {
                            return PackageManagerHook.getInstalledPackages((PackageManager) Objects.requireNonNull(obj), (int) param1, callerClassName);
                        }
                        break;
                    case "getInstalledApplications":
                        if (param1 instanceof Integer) {
                            return PackageManagerHook.getInstalledApplications((PackageManager) Objects.requireNonNull(obj), (int) param1, callerClassName);
                        }
                        break;
                }
                break;

            case "java.lang.Runtime":
                // 5个重载方法
                if ("exec".equals(method.getName())) {
                    //3个参数的处理
                    switch (Objects.requireNonNull(args).length) {
                        case 1:
                            if (param1 instanceof String) {
                                return RuntimeHook.exec((Runtime) Objects.requireNonNull(obj), (String) param1, callerClassName);
                            } else if (param1 instanceof String[]) {
                                return RuntimeHook.exec((Runtime) Objects.requireNonNull(obj), (String[]) param1, callerClassName);
                            }
                            break;

                        case 2:
                            if (param1 instanceof String && param2 instanceof String[]) {
                                return RuntimeHook.exec((Runtime) Objects.requireNonNull(obj), (String) param1, (String[]) param2, callerClassName);
                            }
                            break;

                        case 3:
                            Object param3 = args[2];
                            if (param1 instanceof String && param2 instanceof String[] && param3 instanceof File) {
                                return RuntimeHook.exec((Runtime) Objects.requireNonNull(obj), (String) param1, (String[]) param2, (File) param3, callerClassName);
                            } else if (param1 instanceof String[] && param2 instanceof String[] && param3 instanceof File) {
                                return RuntimeHook.exec((Runtime) Objects.requireNonNull(obj), (String[]) param1, (String[]) param2, (File) param3, callerClassName);
                            }
                            break;

                    }

                }
                break;

            case "android.hardware.Sensor":
                switch (method.getName()) {
                    case "getName":
                        return SensorHook.getName((Sensor) Objects.requireNonNull(obj), callerClassName);
                    case "getType":
                        return SensorHook.getType((Sensor) Objects.requireNonNull(obj), callerClassName);
                    case "getVersion":
                        return SensorHook.getVersion((Sensor) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.hardware.SensorManager":
                if ("getSensorList".equals(method.getName())) {
                    if (param1 instanceof Integer) {
                        return SensorManagerHook.getSensorList((SensorManager) Objects.requireNonNull(obj), (int) param1, callerClassName);
                    }
                }
                break;

            case "android.provider.Settings$Secure":
                if ("getString".equals(method.getName())) {
                    if (param1 instanceof ContentResolver && param2 instanceof String) {
                        return SettingsSecureHook.getString((ContentResolver) param1, (String) param2, callerClassName);
                    }
                }
                break;

            case "android.provider.Settings$System":
                if ("getString".equals(method.getName())) {
                    if (param1 instanceof ContentResolver && param2 instanceof String) {
                        return SettingsSystemHook.getString((ContentResolver) param1, (String) param2, callerClassName);
                    }
                }
                break;

            case "android.telephony.TelephonyManager":
                switch (method.getName()) {
                    case "getImei":
                        return TelephonyManagerHook.getImei((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getDeviceId":
                        return TelephonyManagerHook.getDeviceId((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getSubscriberId":
                        return TelephonyManagerHook.getSubscriberId((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getSimSerialNumber":
                        return TelephonyManagerHook.getSimSerialNumber((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getMeid":
                        return TelephonyManagerHook.getMeid((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getLine1Number":
                        return TelephonyManagerHook.getLine1Number((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getCellLocation":
                        return TelephonyManagerHook.getCellLocation((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getNeighboringCellInfo":
                        return TelephonyManagerHook.getNeighboringCellInfo((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getSimOperator":
                        return TelephonyManagerHook.getSimOperator((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getSimOperatorName":
                        return TelephonyManagerHook.getSimOperatorName((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getSimCountryIso":
                        return TelephonyManagerHook.getSimCountryIso((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getNetworkOperator":
                        return TelephonyManagerHook.getNetworkOperator((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getNetworkOperatorName":
                        return TelephonyManagerHook.getNetworkOperatorName((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getNetworkCountryIso":
                        return TelephonyManagerHook.getNetworkCountryIso((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                    case "getAllCellInfo":
                        return TelephonyManagerHook.getAllCellInfo((TelephonyManager) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.net.wifi.WifiInfo":
                switch (method.getName()) {
                    case "getMacAddress":
                        return WifiInfoHook.getMacAddress((WifiInfo) Objects.requireNonNull(obj), callerClassName);
                    case "getIpAddress":
                        return WifiInfoHook.getIpAddress((WifiInfo) Objects.requireNonNull(obj), callerClassName);
                    case "getSSID":
                        return WifiInfoHook.getSSID((WifiInfo) Objects.requireNonNull(obj), callerClassName);
                    case "getBSSID":
                        return WifiInfoHook.getBSSID((WifiInfo) Objects.requireNonNull(obj), callerClassName);
                }
                break;


            case "android.net.wifi.WifiManager":
                switch (method.getName()) {
                    case "getScanResults":
                        return WifiManagerHook.getScanResults((WifiManager) Objects.requireNonNull(obj), callerClassName);
                    case "getDhcpInfo":
                        return WifiManagerHook.getDhcpInfo((WifiManager) Objects.requireNonNull(obj), callerClassName);
                    case "getConnectionInfo":
                        return WifiManagerHook.getConnectionInfo((WifiManager) Objects.requireNonNull(obj), callerClassName);
                }
                break;

            case "android.os.SystemProperties":
                //第一个参数是key
                String param = "";
                if (param1 instanceof String) {
                    param = (String) param1;
                }
                String key = "SystemProperties#" + method.getName() + "(" + param + "）";
                CheckCacheAndPrivacyResult<String> checkResult = PrivacyUtilKt.checkCacheAndPrivacy(key, callerClassName);
                if (checkResult.shouldReturn()) {
                    return checkResult.getCacheData();
                }
                String result = "";
                // 获取序列号需要权限
                if(method.getName().equals("get") && param.equals("ro.serialno")){
                    if(!PrivacyUtilKt.checkReadPhoneStatePermission(key)){
                        return "";
                    }
                }

                try {
                    result = (String) method.invoke(obj, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(showInvokeLog){
                    LogUtil.INSTANCE.d(TAG, key + ",result=" + result);
                }
                //属性拿不到就返回空字符串
                if (result == null) {
                    result = "";
                }
                return PrivacyUtilKt.savePrivacyMethodResult(key, result, callerClassName);


        }

        return method.invoke(obj, args);
    }
}
