package com.jrr.localbt;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class MainActivity extends Activity {
	
	private static final int REQUEST_OPEN_BT = 0X01;
	private static final String TAG = "MainActivity";
	Button mBtnOpenBt;
	BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mBtnOpenBt = (Button) findViewById(R.id.btn_open_bt);
        
        //在打开之前需要先获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();
        //获取之后还要判断蓝牙功能是否为空,如果为空表示该设备不支持蓝牙功能
       if(mBluetoothAdapter == null){
    	   showToast("该设备不支持蓝牙。。。");
    	return;   
       }
       //获取蓝牙的名字与mac地址
       String name = mBluetoothAdapter.getName();
       String mac = mBluetoothAdapter.getAddress();
       Log.i(TAG, "名字："+ name + "mac:" + mac);
       //获取当前蓝牙的状态
       int status = mBluetoothAdapter.getState();
       
       switch (status) {
	case BluetoothAdapter.STATE_ON://蓝牙已经打开
		showToast("蓝牙已经打开");
		break;
	case BluetoothAdapter.STATE_TURNING_ON://蓝牙正在打开
		showToast("蓝牙正在打开...");
		break;
	case BluetoothAdapter.STATE_TURNING_OFF://蓝牙正在关闭
		showToast("蓝牙正在关闭...");
		break;

	case BluetoothAdapter.STATE_OFF://蓝牙已经关闭
		showToast("蓝牙已经关闭");
		break;
	}
        mBtnOpenBt.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View arg0) {
				// 打开蓝牙设备之前 要确定蓝牙设备是关掉的
				//首先判断蓝牙功能 isEnable()是已经打开，enable是打开蓝牙
				if(mBluetoothAdapter.isEnabled()){
					showToast("蓝牙已经处于打开状态...");
					//关闭蓝牙状态
					boolean isClose = mBluetoothAdapter.disable();
					Log.i(TAG, "蓝牙是否关闭：" + isClose);
					
				}else{
//					boolean isOpen = mBluetoothAdapter.enable();
					//通过系统api打开蓝牙
					Intent open  = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(open, REQUEST_OPEN_BT);
				}
				
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if( REQUEST_OPEN_BT == requestCode){
    		if(resultCode == RESULT_CANCELED){
    			showToast("请求失败。。。");
    		}else{
    		showToast("请求成功。。。");
    		}
    	}
    }
    public void showToast(String s){
    	Toast toast = Toast.makeText(MainActivity.this , s, Toast.LENGTH_LONG);
    	toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
    	toast.show();
    } 
}
