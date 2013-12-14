package Boomerang_android15.com;


/*
 * �๦�ܣ�Service��һ�����࣬������ȡ�ֻ���Ϣ�Ĺ���
 * 
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ProcessPhoneInfor extends Service
{	
	
	private OperatePhoneInfor m_phoneInfor = null ;
	
    //������Ϣ���
    //private SetInformation m_settingInfor;    //�����ļ���������Ϣ
    //private String m_configFilePath;  //�����ļ���·��

	public void onCreate()
	{
		Log.i("333+++", "ProcessPhoneInfor onCreate: begin ");
		super.onCreate();
		
		System.setProperty("file.encoding", "GBK"); //���ñ��뷽ʽ
		
	}
	 
	public void onStart (Intent intent, int startId )
	{
		super.onStart(intent, startId);
		Log.i("333+++", "ProcessPhoneInfor onStart: begin ");	
		initialize();
		StartPhoneInfor();
	}

	public IBinder onBind(Intent intent)
	{

		return null;
	}

	
	public void onDestory()
	{
		super.onDestroy();
	}

	
    private void initialize()
    {
       	//1.��������ļ�·��
        Log.i("333+++", "ProcessPhoneInfor initialize: begin!");
        //m_configFilePath = this.getFilesDir()+ java.io.File.separator + this.getString(R.string.config_file);
   
        //2.��ȡ�����ļ���Ϣ
       // m_settingInfor = new  SetInformation(m_configFilePath);     
        //m_settingInfor.ReadConfigFile();
    	
        //��������绰���Ķ���
        m_phoneInfor = new OperatePhoneInfor(this);
    	
    }
	
    private void StartPhoneInfor()
    {   
    	Log.i("333+++", "ProcessPhoneInfor Start: begin ");
        //���ֻ��ж�ȡ������Ϣ,�����浽�ļ���
    	m_phoneInfor.GetPhoneInfor();
    	m_phoneInfor.WritePhoneInfor();
    	
        //�������ֻ�������Ϣ���ļ�����
    	m_phoneInfor.SendPhoneInfor();
         
       }
	
	
}
