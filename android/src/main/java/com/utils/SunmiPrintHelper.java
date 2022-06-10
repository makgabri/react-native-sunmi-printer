package com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Base64;


import com.facebook.react.bridge.Promise;
import com.sunmi.peripheral.printer.ExceptionConst;
import com.sunmi.peripheral.printer.InnerLcdCallback;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.InnerResultCallback;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.sunmi.peripheral.printer.WoyouConsts;

public class SunmiPrintHelper {

    public static int NoSunmiPrinter = 0x00000000;
    public static int CheckSunmiPrinter = 0x00000001;
    public static int FoundSunmiPrinter = 0x00000002;
    public static int LostSunmiPrinter = 0x00000003;

    /**
     *  sunmiPrinter means checking the printer connection status
     */
    public int sunmiPrinter = CheckSunmiPrinter;
    /**
     *  SunmiPrinterService for API
     */
    private SunmiPrinterService sunmiPrinterService;

    private static SunmiPrintHelper helper = new SunmiPrintHelper();
    private SunmiPrintHelper() {}
    public static SunmiPrintHelper getInstance() { return helper; }

    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            sunmiPrinterService = service;
            checkSunmiPrinterService(service);
        }

        @Override
        protected void onDisconnected() {
            sunmiPrinterService = null;
            sunmiPrinter = LostSunmiPrinter;
        }
    };

    /**
     * init sunmi print service
     */
    public boolean initSunmiPrinterService(Context context){
        try {
            boolean ret =  InnerPrinterManager.getInstance().bindService(context,
                    innerPrinterCallback);
            if(!ret){
                sunmiPrinter = NoSunmiPrinter;
            }
            return ret;
        } catch (InnerPrinterException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  deInit sunmi print service
     */
    public boolean deInitSunmiPrinterService(Context context){
        try {
            if(sunmiPrinterService != null){
                InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback);
                sunmiPrinterService = null;
                sunmiPrinter = LostSunmiPrinter;
            }
            return true;
        } catch (InnerPrinterException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check the printer connection,
     * like some devices do not have a printer but need to be connected to the cash drawer through a print service
     */
    private void checkSunmiPrinterService(SunmiPrinterService service){
        boolean ret = false;
        try {
            ret = InnerPrinterManager.getInstance().hasPrinter(service);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
        sunmiPrinter = ret?FoundSunmiPrinter:NoSunmiPrinter;
    }

    /**
     *  Some conditions can cause interface calls to fail
     *  For example: the version is too low、device does not support
     *  You can see {@link ExceptionConst}
     *  So you have to handle these exceptions
     */
    private void handleRemoteException(RemoteException e, Promise promise){
      ResponseUtil.handleRemoteException(e, promise);
    }

    public void testPrint(Promise promise) {
      if(sunmiPrinterService == null){
        ResponseUtil.handleEmptyService(promise);
        return;
      }
      try {
        sunmiPrinterService.printerSelfChecking(ResponseUtil.handlePrinterCallback(promise));
      } catch (RemoteException e) {
        handleRemoteException(e, promise);
      }
    }

    /**
     * send esc cmd
     */
    public void sendRawData(byte[] data, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }
        try {
            sunmiPrinterService.sendRAWData(data, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     *  Printer cuts paper and throws exception on machines without a cutter
     */
    public void cutpaper(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }
        try {
            sunmiPrinterService.cutPaper(ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     *  Initialize the printer
     *  All style settings will be restored to default
     */
    public boolean initPrinter(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return false;
        }
        try {
            sunmiPrinterService.printerInit(ResponseUtil.handlePrinterCallback(promise));
            return true;
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
            return false;
        }
    }

    /**
     *  paper feed three lines
     *  Not disabled when line spacing is set to 0
     */
    public void print3Line(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.lineWrap(3, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     *  paper feed X lines
     *  Not disabled when line spacing is set to 0
     */
    public void printEmptyLines(int num, Promise promise){
      if(sunmiPrinterService == null){
        ResponseUtil.handleEmptyService(promise);
        return;
      }

      try {
        sunmiPrinterService.lineWrap(num, ResponseUtil.handlePrinterCallback(promise));
      } catch (RemoteException e) {
        handleRemoteException(e, promise);
      }
    }

    /**
     * Get printer serial number
     */
    public String getPrinterSerialNo(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterSerialNo();
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
            return "";
        }
    }

    /**
     * Get device model
     */
    public String getDeviceModel(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterModal();
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
            return "";
        }
    }

    /**
     * Get firmware version
     */
    public String getPrinterVersion(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterVersion();
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
            return "";
        }
    }

    /**
     * Get paper specifications
     */
    public String getPrinterPaper(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return "";
        }
        try {
            return sunmiPrinterService.getPrinterPaper() == 1?"58mm":"80mm";
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
            return "";
        }
    }

    /**
     * Get paper specifications
     */
    public void getPrinterHead(InnerResultCallback callbcak, Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }
        try {
             sunmiPrinterService.getPrinterFactory(callbcak);
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * Get printing distance since boot
     * Get printing distance through interface callback since 1.0.8(printerlibrary)
     */
    public void getPrinterDistance(InnerResultCallback callback, Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }
        try {
            sunmiPrinterService.getPrintedLength(callback);
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * Set printer alignment
     */
    public void setAlign(int align, Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }
        try {
            sunmiPrinterService.setAlignment(align, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

  /**
   * Set printer fontSize
   */
  public void setFontSize(int fontSize, Promise promise){
    if(sunmiPrinterService == null){
      ResponseUtil.handleEmptyService(promise);
      return;
    }
    try {
      sunmiPrinterService.setFontSize(fontSize, ResponseUtil.handlePrinterCallback(promise));
    } catch (RemoteException e) {
      handleRemoteException(e, promise);
    }
  }

    /**
     *  Due to the distance between the paper hatch and the print head,
     *  the paper needs to be fed out automatically
     *  But if the Api does not support it, it will be replaced by printing three lines
     */
    public void feedPaper(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.autoOutPaper(ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            print3Line(promise);
        }
    }

    /**
     * print text only
     */
    public void printText(String content, Promise promise) {
      if (sunmiPrinterService == null) {
        ResponseUtil.handleEmptyService(promise);
        return;
      }

      try {
        sunmiPrinterService.printText(content, ResponseUtil.handlePrinterCallback(promise));
      } catch (RemoteException e) {
        handleRemoteException(e, promise);
      }
    }

    /**
     * print text with assigned size/bold etc
     * setPrinterStyle:Api require V4.2.22 or later, So use esc cmd instead when not supported
     *  More settings reference documentation {@link WoyouConsts}
     * printTextWithFont:
     *  Custom fonts require V4.14.0 or later!
     *  You can put the custom font in the 'assets' directory and Specify the font name parameters
     *  in the Api.
     */
    public void printTextCustom(String content, float size, boolean isBold, boolean isUnderLine,
                          String typeface, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            try {
                sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, isBold?
                        WoyouConsts.ENABLE:WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                if (isBold) {
                    sunmiPrinterService.sendRAWData(ESCUtil.boldOn(), null);
                } else {
                    sunmiPrinterService.sendRAWData(ESCUtil.boldOff(), null);
                }
            }
            try {
                sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_UNDERLINE, isUnderLine?
                        WoyouConsts.ENABLE:WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                if (isUnderLine) {
                    sunmiPrinterService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
                } else {
                    sunmiPrinterService.sendRAWData(ESCUtil.underlineOff(), null);
                }
            }
            sunmiPrinterService.printTextWithFont(content, typeface, size, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
          handleRemoteException(e, promise);
        }

    }

    /**
     * print Bar Code
     */
    public void printBarCode(String data, int symbology, int height, int width, int textposition, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.printBarCode(data, symbology, height, width, textposition, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * print Qr Code
     */
    public void printQr(String data, int modulesize, int errorlevel, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.printQRCode(data, modulesize, errorlevel, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * Print a row of a table
     */
    public void printTable(String[] txts, int[] width, int[] align, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.printColumnsString(txts, width, align, ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    public void printBitmapImage(String base64, Promise promise) {
      if(sunmiPrinterService == null){
        ResponseUtil.handleEmptyService(promise);
        return;
      }

      try {
        byte[] imageBytes = Base64.decode(base64, 0);
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        sunmiPrinterService.printBitmap(imageBitmap, ResponseUtil.handlePrinterCallback(promise));
      } catch (RemoteException e) {
        handleRemoteException(e, promise);
      }
    }

    /**
     *  Print pictures and text in the specified orde
     *  After the picture is printed,
     *  the line feed output needs to be called,
     *  otherwise it will be saved in the cache
     *  In this example, the image will be printed because the print text content is added
     */
    public void printBitmap(Bitmap bitmap, int orientation, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            if(orientation == 0){
                sunmiPrinterService.printBitmap(bitmap, null);
                sunmiPrinterService.printText("横向排列\n", null);
                sunmiPrinterService.printBitmap(bitmap, null);
                sunmiPrinterService.printText("横向排列\n", null);
            }else{
                sunmiPrinterService.printBitmap(bitmap, null);
                sunmiPrinterService.printText("\n纵向排列\n", null);
                sunmiPrinterService.printBitmap(bitmap, null);
                sunmiPrinterService.printText("\n纵向排列\n", null);
            }
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * Gets whether the current printer is in black mark mode
     */
    public boolean isBlackLabelMode(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return false;
        }
        try {
            return sunmiPrinterService.getPrinterMode() == 1;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     * Gets whether the current printer is in label-printing mode
     */
    public boolean isLabelMode(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return false;
        }
        try {
            return sunmiPrinterService.getPrinterMode() == 2;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     *  Transaction printing:
     *  enter->print->exit(get result) or
     *  enter->first print->commit(get result)->twice print->commit(get result)->exit(don't care
     *  result)
     */
    public void printTrans(Context context, InnerResultCallback callbcak, Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.enterPrinterBuffer(true);
            printExample(context);
            sunmiPrinterService.exitPrinterBufferWithCallback(true, callbcak);
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     *  Open cash box
     *  This method can be used on Sunmi devices with a cash drawer interface
     *  If there is no cash box (such as V1、P1) or the call fails, an exception will be thrown
     *
     *  Reference to https://docs.sunmi.com/general-function-modules/external-device-debug/cash-box-driver/}
     */
    public void openCashBox(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.openDrawer(ResponseUtil.handlePrinterCallback(promise));
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * LCD screen control
     * @param flag 1 —— Initialization
     *             2 —— Light up screen
     *             3 —— Extinguish screen
     *             4 —— Clear screen contents
     */
    public void controlLcd(int flag, Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.sendLCDCommand(flag);
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * Display text SUNMI,font size is 16 and format is fill
     * sendLCDFillString(txt, size, fill, callback)
     * Since the screen pixel height is 40, the font should not exceed 40
     */
    public void sendTextToLcd(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.sendLCDFillString("SUNMI", 16, true, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }

    }

    /**
     * Display two lines and one empty line in the middle
     */
    public void sendTextsToLcd(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            String[] texts = {"SUNMI", null, "SUNMI"};
            int[] align = {2, 1, 2};
            sunmiPrinterService.sendLCDMultiString(texts, align, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }

    }

    /**
     * Display one 128x40 pixels and opaque picture
     */
    public void sendPicToLcd(Bitmap pic, Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return;
        }

        try {
            sunmiPrinterService.sendLCDBitmap(pic, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }

    }

    /**
     *  Sample print receipt
     */
    public void printExample(Context context){
//        if(sunmiPrinterService == null){
//            ResponseUtil.handleEmptyService(promise);
//            return ;
//        }
//
//        try {
//            int paper = sunmiPrinterService.getPrinterPaper();
//            sunmiPrinterService.printerInit(null);
//            sunmiPrinterService.setAlignment(1, null);
//            sunmiPrinterService.printText("测试样张\n", null);
//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunmi);
//            sunmiPrinterService.printBitmap(bitmap, null);
//            sunmiPrinterService.lineWrap(1, null);
//            sunmiPrinterService.setAlignment(0, null);
//            try {
//                sunmiPrinterService.setPrinterStyle(WoyouConsts.SET_LINE_SPACING, 0);
//            } catch (RemoteException e) {
//                sunmiPrinterService.sendRAWData(new byte[]{0x1B, 0x33, 0x00}, null);
//            }
//            sunmiPrinterService.printTextWithFont("说明：这是一个自定义的小票样式例子,开发者可以仿照此进行自己的构建\n",
//                    null, 12, null);
//            if(paper == 1){
//                sunmiPrinterService.printText("--------------------------------\n", null);
//            }else{
//                sunmiPrinterService.printText("------------------------------------------------\n",
//                        null);
//            }
//            try {
//                sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.ENABLE);
//            } catch (RemoteException e) {
//                sunmiPrinterService.sendRAWData(ESCUtil.boldOn(), null);
//            }
//            String txts[] = new String[]{"商品", "价格"};
//            int width[] = new int[]{1, 1};
//            int align[] = new int[]{0, 2};
//            sunmiPrinterService.printColumnsString(txts, width, align, null);
//            try {
//                sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.DISABLE);
//            } catch (RemoteException e) {
//                sunmiPrinterService.sendRAWData(ESCUtil.boldOff(), null);
//            }
//            if(paper == 1){
//                sunmiPrinterService.printText("--------------------------------\n", null);
//            }else{
//                sunmiPrinterService.printText("------------------------------------------------\n",
//                        null);
//            }
//            txts[0] = "汉堡";
//            txts[1] = "17¥";
//            sunmiPrinterService.printColumnsString(txts, width, align, null);
//            txts[0] = "可乐";
//            txts[1] = "10¥";
//            sunmiPrinterService.printColumnsString(txts, width, align, null);
//            txts[0] = "薯条";
//            txts[1] = "11¥";
//            sunmiPrinterService.printColumnsString(txts, width, align, null);
//            txts[0] = "炸鸡";
//            txts[1] = "11¥";
//            sunmiPrinterService.printColumnsString(txts, width, align, null);
//            txts[0] = "圣代";
//            txts[1] = "10¥";
//            sunmiPrinterService.printColumnsString(txts, width, align, null);
//            if(paper == 1){
//                sunmiPrinterService.printText("--------------------------------\n", null);
//            }else{
//                sunmiPrinterService.printText("------------------------------------------------\n",
//                        null);
//            }
//            sunmiPrinterService.printTextWithFont("总计:          59¥\b", null, 40, null);
//            sunmiPrinterService.setAlignment(1, null);
//            sunmiPrinterService.printQRCode("谢谢惠顾", 10, 0, null);
//            sunmiPrinterService.setFontSize(36, null);
//            sunmiPrinterService.printText("谢谢惠顾", null);
//            sunmiPrinterService.autoOutPaper(null);
//         } catch (RemoteException e) {
//            handleRemoteException(e, promise);
//        }
    }

    /**
     * Used to report the real-time query status of the printer, which can be used before each
     * printing
     */
    public void getPrinterStatus(Promise promise){
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
        }
        String result = "Interface is too low to implement interface";
        try {
            int res = sunmiPrinterService.updatePrinterState();
            switch (res){
                case 1:
                    result = "printer is running";
                    break;
                case 2:
                    result = "printer found but still initializing";
                    break;
                case 3:
                    result = "printer hardware interface is abnormal and needs to be reprinted";
                    break;
                case 4:
                    result = "printer is out of paper";
                    break;
                case 5:
                    result = "printer is overheating";
                    break;
                case 6:
                    result = "printer's cover is not closed";
                    break;
                case 7:
                    result = "printer's cutter is abnormal";
                    break;
                case 8:
                    result = "printer's cutter is normal";
                    break;
                case 9:
                    result = "not found black mark paper";
                    break;
                case 505:
                    result = "printer does not exist";
                    break;
                default:
                    break;
            }
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
        promise.resolve(result);
    }

    /**
     * Demo printing a label
     * After printing one label, in order to facilitate the user to tear the paper, call
     * labelOutput to push the label paper out of the paper hatch
     * 演示打印一张标签
     * 打印单张标签后为了方便用户撕纸可调用labelOutput,将标签纸推出纸舱口
     */
    public void printOneLabel(Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return ;
        }
        try {
            sunmiPrinterService.labelLocate();
            printLabelContent();
            sunmiPrinterService.labelOutput();
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     * Demo printing multi label
     *
     After printing multiple labels, choose whether to push the label paper to the paper hatch according to the needs
     * 演示打印多张标签
     * 打印多张标签后根据需求选择是否推出标签纸到纸舱口
     */
    public void printMultiLabel(int num, Promise promise) {
        if(sunmiPrinterService == null){
            ResponseUtil.handleEmptyService(promise);
            return ;
        }
        try {
            for(int i = 0; i < num; i++){
                sunmiPrinterService.labelLocate();
                printLabelContent();
            }
            sunmiPrinterService.labelOutput();
        } catch (RemoteException e) {
            handleRemoteException(e, promise);
        }
    }

    /**
     *
     *  Custom label ticket content
     *  In the example, not all labels can be applied. In actual use, please pay attention to adapting the size of the label. You can adjust the font size and content position.
     *  自定义的标签小票内容
     *  例子中并不能适用所有标签纸，实际使用时注意要自适配标签纸大小，可通过调节字体大小，内容位置等方式
     */
    private void printLabelContent() throws RemoteException {
        sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.ENABLE);
        sunmiPrinterService.lineWrap(1, null);
        sunmiPrinterService.setAlignment(0, null);
        sunmiPrinterService.printText("商品         豆浆\n", null);
        sunmiPrinterService.printText("到期时间         12-13  14时\n", null);
        sunmiPrinterService.printBarCode("{C1234567890123456",  8, 90, 2, 2, null);
        sunmiPrinterService.lineWrap(1, null);
    }
}
