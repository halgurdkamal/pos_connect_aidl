package com.bytewises.pos_connect_aidl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.iposprinter.iposprinterservice.IPosPrinterCallback;
import com.iposprinter.iposprinterservice.IPosPrinterService;

import java.util.Map;
import java.util.Random;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** PosConnectAidlPlugin */
public class PosConnectAidlPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {
   private MethodChannel channel;
  private MethodCall call;
  private Context context;
  private Activity activity;
  private Result mResult;


  private IPosPrinterService mIPosPrinterService;
  private IPosPrinterCallback callback = null;



  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "pos_connect_aidl");
     context = flutterPluginBinding.getApplicationContext();
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {


    mResult = result;
    this.call = call;
//    if (call.method.equals("getPlatformVersion")) {
      int data = 0;

      callback = new IPosPrinterCallback.Stub() {
        @Override
        public void onRunResult(final boolean isSuccess) throws RemoteException {
          Log.i("Debug", "onReturnString: isSuccess "+ isSuccess);

        }

        @Override
        public void onReturnString(final String value) throws RemoteException {
          Log.i("Debug", "onReturnString: value "+ value);
        }
      };

      // connect pos to printer by service
      Intent intent=new Intent();
      intent.setPackage("com.iposprinter.iposprinterservice");
      intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
      context.startService(intent);
      context. bindService(intent, connectService, context.BIND_AUTO_CREATE);

      try {
        int state = mIPosPrinterService.getPrinterStatus();
        if (call.method.equals("printImage")) {

          // get image from byte then add to printer
          final Map<String, Object> getData = call.arguments();
          byte[] path = (byte[]) getData.get("pathImage");
          Bitmap bitmap = BitmapFactory.decodeByteArray(path, 0, path.length);
          // 1 is center
          // 1-16 to large data
          mIPosPrinterService.printBitmap(1, 16, bitmap, callback);
          mIPosPrinterService.printBlankLines(1, 2, callback);
          mIPosPrinterService.printerPerformPrint(100, callback);
          mResult.success(true);
        }



      } catch (Exception e){

      }

  }


  // when service connect to printer call this function then you can print
  private ServiceConnection connectService = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

      try {


        mIPosPrinterService = IPosPrinterService.Stub.asInterface(service);
        printerInit();

        if (call.method.equals("printImage")) {

          // get image from byte then add to printer
          final Map<String, Object> getData = call.arguments();
          byte[] path = (byte[]) getData.get("pathImage");
          Bitmap bitmap = BitmapFactory.decodeByteArray(path, 0, path.length);
          // 1 is center
          // 1-16 to large data
          mIPosPrinterService.printBitmap(1, 16, bitmap, callback);
          mIPosPrinterService.printBlankLines(1, 2, callback);
          mIPosPrinterService.printerPerformPrint(100, callback);
          mResult.success(true);
        } else {
          mResult.success(false);

        }
      } catch (Exception e) {
         mResult.success(false);
      }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      mIPosPrinterService = null;
      mResult.success(false);
    }
  };


  public void printerInit(){

    try {
      mIPosPrinterService.printerInit(callback);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }



  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    return false;
  }
}
