package com.reactnativesunmiprinter;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.utils.SunmiPrintHelper;

@ReactModule(name = SunmiPrinterModule.NAME)
public class SunmiPrinterModule extends ReactContextBaseJavaModule {
    public static final String NAME = "SunmiPrinter";

    public SunmiPrinterModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private WritableMap generateResp(boolean success) {
        WritableMap map = Arguments.createMap();
        map.putBoolean("success", success);
        return map;
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void connect(Promise promise) {
      boolean success = SunmiPrintHelper.getInstance().initSunmiPrinterService(getReactApplicationContext());
      promise.resolve(generateResp(success));
    }

    @ReactMethod
    public void disconnect(Promise promise) {
      boolean success = SunmiPrintHelper.getInstance().deInitSunmiPrinterService(getReactApplicationContext());
      promise.resolve(generateResp(success));
    }

    @ReactMethod
    public void reset(Promise promise) {
      boolean success = SunmiPrintHelper.getInstance().initPrinter(promise);
      promise.resolve(generateResp(success));
    }

    @ReactMethod
    public void testPrint(Promise promise) {
      SunmiPrintHelper.getInstance().testPrint(promise);
    }

    @ReactMethod
    public void printerSpecs(Promise promise) {
      WritableMap map = Arguments.createMap();
      map.putString("SerialNo", SunmiPrintHelper.getInstance().getPrinterSerialNo(promise));
      map.putString("DeviceModel", SunmiPrintHelper.getInstance().getDeviceModel(promise));
      map.putString("PrinterVersion", SunmiPrintHelper.getInstance().getPrinterVersion(promise));
      map.putString("PrintPaper", SunmiPrintHelper.getInstance().getPrinterPaper(promise));
      promise.resolve(map);
    }

    @ReactMethod
    public void printerStatus(Promise promise) {
      SunmiPrintHelper.getInstance().getPrinterStatus(promise);
    }

    @ReactMethod
    public void cutPaper(Promise promise) {
      SunmiPrintHelper.getInstance().cutpaper(promise);
    }

    @ReactMethod
    public void printText(String text, Promise promise) {
      SunmiPrintHelper.getInstance().printText(text, promise);
    }

    @ReactMethod
    public void printTextCustom(String text, float size, boolean isBold, boolean isUnderline,
                          String typeface, Promise promise) {
      SunmiPrintHelper.getInstance().printTextCustom(text, size, isBold, isUnderline, typeface, promise);
    }

    @ReactMethod
    public void printEmptyLines(int num, Promise promise) {
      SunmiPrintHelper.getInstance().printEmptyLines(num, promise);
    }

    @ReactMethod
    public void setAlign(int align, Promise promise) {
      SunmiPrintHelper.getInstance().setAlign(align, promise);
    }

    @ReactMethod
    public void setFontSize(int fontSize, Promise promise) {
      SunmiPrintHelper.getInstance().setFontSize(fontSize, promise);
    }

    @ReactMethod
    public void printBarCode(String data, int symbology, int height, int width, int textPosition, Promise promise){
      SunmiPrintHelper.getInstance().printBarCode(data, symbology, height, width, textPosition, promise);
    }

    @ReactMethod
    public void printQRCode(String data, int modulesize, int errorlevel, Promise promise){
      SunmiPrintHelper.getInstance().printQr(data, modulesize, errorlevel, promise);
    }

    @ReactMethod
    public void printTable(String[] txts, int[] width, int[] align, Promise promise){
      SunmiPrintHelper.getInstance().printTable(txts, width, align, promise);
    }

    @ReactMethod
    public void printBase64Image(String base64, Promise promise){
      SunmiPrintHelper.getInstance().printBitmapImage(base64, promise);
    }

//    @ReactMethod
//    public void sendRawData(byte[] raw){
//      SunmiPrintHelper.getInstance().sendRawData(raw);
//    }

}
