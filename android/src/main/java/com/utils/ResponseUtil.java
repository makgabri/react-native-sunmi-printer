package com.utils;

import android.os.RemoteException;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.sunmi.peripheral.printer.InnerResultCallback;

public class ResponseUtil {
  private static String emptyPrinterServiceException = "-1";
  private static String remoteException = "-2";

  public static void handleEmptyService(Promise promise) {
    promise.reject(emptyPrinterServiceException, "Printer service empty, please connect printer first");
  }

  public static void handleRemoteException(RemoteException e, Promise promise) {
    promise.reject(remoteException, e.getMessage());
  }

  public static InnerResultCallback handlePrinterCallback(Promise promise) {
    InnerResultCallback handler = new InnerResultCallback() {
      @Override
      public void onRunResult(boolean isSuccess) throws RemoteException {
        if (isSuccess) {
          WritableMap map = Arguments.createMap();
          map.putBoolean("success", true);
          promise.resolve(map);
        }
      }

      @Override
      public void onReturnString(String result) throws RemoteException {
      }

      @Override
      public void onRaiseException(int code, String msg) throws RemoteException {
        promise.reject(String.valueOf(code), msg);
      }

      @Override
      public void onPrintResult(int code, String msg) throws RemoteException {
      }
    };
    return handler;
  }
}
