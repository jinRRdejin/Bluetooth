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
        
        //�ڴ�֮ǰ��Ҫ�Ȼ�ȡ��������������
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();
        //��ȡ֮��Ҫ�ж����������Ƿ�Ϊ��,���Ϊ�ձ�ʾ���豸��֧����������
       if(mBluetoothAdapter == null){
    	   showToast("���豸��֧������������");
    	return;   
       }
       //��ȡ������������mac��ַ
       String name = mBluetoothAdapter.getName();
       String mac = mBluetoothAdapter.getAddress();
       Log.i(TAG, "���֣�"+ name + "mac:" + mac);
       //��ȡ��ǰ������״̬
       int status = mBluetoothAdapter.getState();
       
       switch (status) {
	case BluetoothAdapter.STATE_ON://�����Ѿ���
		showToast("�����Ѿ���");
		break;
	case BluetoothAdapter.STATE_TURNING_ON://�������ڴ�
		showToast("�������ڴ�...");
		break;
	case BluetoothAdapter.STATE_TURNING_OFF://�������ڹر�
		showToast("�������ڹر�...");
		break;

	case BluetoothAdapter.STATE_OFF://�����Ѿ��ر�
		showToast("�����Ѿ��ر�");
		break;
	}
        mBtnOpenBt.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View arg0) {
				// �������豸֮ǰ Ҫȷ�������豸�ǹص���
				//�����ж��������� isEnable()���Ѿ��򿪣�enable�Ǵ�����
				if(mBluetoothAdapter.isEnabled()){
					showToast("�����Ѿ����ڴ�״̬...");
					//�ر�����״̬
					boolean isClose = mBluetoothAdapter.disable();
					Log.i(TAG, "�����Ƿ�رգ�" + isClose);
					
				}else{
//					boolean isOpen = mBluetoothAdapter.enable();
					//ͨ��ϵͳapi������
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
    			showToast("����ʧ�ܡ�����");
    		}else{
    		showToast("����ɹ�������");
    		}
    	}
    }
    public void showToast(String s){
    	Toast toast = Toast.makeText(MainActivity.this , s, Toast.LENGTH_LONG);
    	toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
    	toast.show();
    } 
}
