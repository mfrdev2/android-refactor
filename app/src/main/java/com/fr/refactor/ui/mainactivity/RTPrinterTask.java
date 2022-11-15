package com.fr.refactor.ui.mainactivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.util.Log;

import com.fr.refactor.R;
import com.fr.refactor.MyApp;
import com.fr.refactor.utils.printer.BaseEnum;
import com.fr.refactor.utils.printer.LogUtils;
import com.fr.refactor.utils.printer.TempletDemo;
import com.fr.refactor.utils.printer.TimeRecordUtils;
import com.fr.refactor.utils.printer.ToastUtil;
import com.rt.printerlibrary.bean.PrinterStatusBean;
import com.rt.printerlibrary.bean.UsbConfigBean;
import com.rt.printerlibrary.connect.PrinterInterface;
import com.rt.printerlibrary.enumerate.CommonEnum;
import com.rt.printerlibrary.exception.SdkException;
import com.rt.printerlibrary.factory.connect.PIFactory;
import com.rt.printerlibrary.factory.connect.UsbFactory;
import com.rt.printerlibrary.factory.printer.PrinterFactory;
import com.rt.printerlibrary.factory.printer.UniversalPrinterFactory;
import com.rt.printerlibrary.observer.PrinterObserver;
import com.rt.printerlibrary.observer.PrinterObserverManager;
import com.rt.printerlibrary.printer.RTPrinter;
import com.rt.printerlibrary.utils.PrintListener;
import com.rt.printerlibrary.utils.PrinterStatusPareseUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RTPrinterTask implements PrinterObserver {
    private static final String TAG = RTPrinterTask.class.getSimpleName();

    private final Context context;

    @BaseEnum.ConnectType
    private int checkedConType = BaseEnum.CON_USB;
    private RTPrinter rtPrinter = null;
    private List<UsbDevice> mList;
    public UsbConfigBean usbconfigObj;

    private PrinterFactory printerFactory;


    private TempletDemo templetDemo;


    public RTPrinterTask(Context context) {
        this.context = context;
        init();
    }

    public void init() {
        MyApp.instance.setCurrentCmdType(BaseEnum.CMD_ESC);
        printerFactory = new UniversalPrinterFactory();
        rtPrinter = printerFactory.create();

        // tv_ver.setText("PrinterExample Ver: v" + TonyUtils.getVersionName(this));
        PrinterObserverManager.getInstance().add(this);//添加连接状态监听
    }

    public void printTemplet() throws UnsupportedEncodingException, SdkException {
        switch (MyApp.getInstance().getCurrentCmdType()) {
            case BaseEnum.CMD_ESC:
                escPrint();
                break;
            case BaseEnum.CMD_TSC:
                templetDemo.tscPrint();
                break;
            case BaseEnum.CMD_CPCL:
                templetDemo.cpclPrint();
                break;
            case BaseEnum.CMD_ZPL:
                //  zplPrint();
                break;
            case BaseEnum.CMD_PIN:
                //  pinPrint();
                break;
            default:
                break;
        }
    }


    private void escPrint() throws UnsupportedEncodingException, SdkException {
        if (rtPrinter == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //  templetDemo.escTemplet();
                    templetDemo.escMyTemplet();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (SdkException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public void autoConnectUsb() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getUsbCount() == 1) {
                    Log.d(TAG, " usbPrinter connect");
                    UsbDevice mUsbDevice = mList.get(0);
                    PendingIntent mPermissionIntent = PendingIntent.getBroadcast(
                            context,
                            0,
                            new Intent(context.getApplicationInfo().packageName),
                            0);
                    usbconfigObj = new UsbConfigBean(context, mUsbDevice, mPermissionIntent);
                    connectUSB(usbconfigObj);
                } else if (getUsbCount() == 0) {
                    Log.d(TAG, "not usbPrinter connect");
                    //pb_connect.setVisibility(View.GONE);
                }
                //  showUSBDeviceChooseDialog();
            }
        }, 100);
    }


    private int getUsbCount() {
        mList = new ArrayList<>();
        UsbManager mUsbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        LogUtils.d(TAG, "deviceList size = " + deviceList.size());
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            Log.d(TAG, "device getDeviceName" + device.getDeviceName());
            Log.d(TAG, "device getVendorId" + device.getVendorId());
            Log.d(TAG, "device getProductId" + device.getProductId());
            if (isPrinterDevice(device))
                mList.add(device);
        }
        return mList.size();

    }

    private static boolean isPrinterDevice(UsbDevice device) {
        if (device == null) {
            return false;
        }
        if (device.getInterfaceCount() == 0) {
            return false;
        }
        for (int i = 0; i < device.getInterfaceCount(); i++) {
            android.hardware.usb.UsbInterface usbInterface = device.getInterface(i);
            if (usbInterface.getInterfaceClass() == UsbConstants.USB_CLASS_PRINTER) {
                return true;
            }
        }
        return false;
    }


    private void connectUSB(UsbConfigBean usbConfigBean) {
        UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        PIFactory piFactory = new UsbFactory();
        PrinterInterface printerInterface = piFactory.create();
        printerInterface.setConfigObject(usbConfigBean);
        rtPrinter.setPrinterInterface(printerInterface);

        if (mUsbManager.hasPermission(usbConfigBean.usbDevice)) {
            try {
                rtPrinter.connect(usbConfigBean);
                MyApp.instance.setRtPrinter(rtPrinter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mUsbManager.requestPermission(usbConfigBean.usbDevice, usbConfigBean.pendingIntent);
        }

    }
    /**
     * setPrinterStatusListener
     */
    private void setPrinterStatusListener() {
        if (rtPrinter == null) return;
        rtPrinter.setPrintListener(new PrintListener() {
            @Override
            public void onPrinterStatus(PrinterStatusBean StatusBean) {
                //Accept printer status here
                if(StatusBean.blPrintSucc){
                    System.out.println("print complete");
                    return;
                }
                String msg = PrinterStatusPareseUtils.getPrinterStatusStr(StatusBean);
                Log.i("rongta", "onPrinterStatus: " + msg);
                if (!msg.isEmpty())
                    ToastUtil.show(context, "print status：" + msg);
            }
        });
    }

    @Override
    public void printerObserverCallback(final PrinterInterface printerInterface, final int state) {
             {
                switch (state) {
                    case CommonEnum.CONNECT_STATE_SUCCESS:
                        TimeRecordUtils.record("RT连接end：", System.currentTimeMillis());
                        Log.i(TAG,printerInterface.getConfigObject().toString() + context.getString(R.string._main_connected));
                        rtPrinter.setPrinterInterface(printerInterface);
                        templetDemo = TempletDemo.getInstance(rtPrinter, context);
                        setPrinterStatusListener();//StatusListener()
                        break;
                    case CommonEnum.CONNECT_STATE_INTERRUPTED:
                        if (printerInterface != null && printerInterface.getConfigObject() != null) {
                            Log.i(TAG,printerInterface.getConfigObject().toString() + context.getString(R.string._main_disconnect));
                        } else {
                            Log.i(TAG,context.getString(R.string._main_disconnect));
                        }
                        TimeRecordUtils.record("RT连接断开：", System.currentTimeMillis());
                        break;
                    default:
                        break;
                }
            }
        }



    @Override
    public void printerReadMsgCallback(PrinterInterface printerInterface, final byte[] bytes) {
    }

}
