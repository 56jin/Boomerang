package Boomerang_android15.com;

///////////////////////////////////////////////////////////////////////
//
//ģ�鹦��:������յ��Ķ���ָ��
//ע:�����ж���ʵ��,ʶ��ָ������,�������ģ��
//
//////////////////////////////////////////////////////////////////////




import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent; 
//import android.os.Bundle;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;
import android.util.Log;

//import android.util.Log;


public class LaunchService extends Service
{	
	SetInformation m_settingInfor;		//�����ļ���������Ϣ
	String m_configFilePath;	//�����ļ���·��
	String m_messageText;		//�յ��Ķ�Ϣ����
	
	//!!!!!!!!��Ҫ���뷢�Ͷ��ŵ��ֻ���
	String m_messageAddress = "";	//�յ���Ϣ���ֻ���, ������.
	
	
	String [] elementStr;
			
	public void onCreate()
	{
		super.onCreate();	
		
		System.setProperty("file.encoding", "GBK"); //���ñ��뷽ʽ
		Log.i("333+++", "LaunchService onCreate begin ");
		//��������ļ���·��
		m_configFilePath = this.getFilesDir()+ java.io.File.separator + this.getString(R.string.config_file);
		
		//��ȡ�����ļ���Ϣ
		m_settingInfor = new  SetInformation(m_configFilePath);     
		m_settingInfor.ReadConfigFile();
		Log.i("333+++", m_configFilePath);
		
		
		
	}
	 
	public void onStart (Intent intent, int startId )
	{
		super.onStart(intent, startId);
		
		//����FqlSMSReceiver�������Ĳ���,���в�������,���ƶ�������,��ָ����ߵ��ֻ���
		Log.i("333+++", "LaunchService onCreate begin ");
		Bundle bundle = intent.getExtras();
		m_messageText = bundle.getString("MESSAGE");
		m_messageAddress = bundle.getString("PHONE_NUM");
		elementStr = m_messageText.split(" ");
					
		if (elementStr.length <3)
		{
			Log.i("333+++", "LaunchService onStart: lenght is too short ");
			return;
		}

		//�ж�ָ�����Ƿ�����ؼ���
		if(0 != elementStr[0].compareTo("fql"))
		{
			//�����Ϲؼ���ֱ�ӷ���
			Log.i("333+++", "LaunchService onStart: can't find fql in messaging ");
			return;
		}

		if (elementStr[2].compareTo(m_settingInfor.GetPassWord())!= 0)
		{
			//���벻��
			Log.i("333+++", "LaunchService onStart: password wrong! ");
			Log.i("333+++", m_settingInfor.GetPassWord());		
			return;
		}
		

		if(elementStr.length == 3)
		{
			//�������κ�����ʹ��Ĭ������,�����ֻ����ұ�������
			Log.i("333+++", "LaunchService onStart: call  DoDefaultAct function!");
			DoDefaultAct();  //�����ֻ���������
			
		}
		
		if (elementStr[2].compareTo("command") == 0)
		{
			//������������
		}


	}

	public IBinder onBind(Intent intent)
	{

		return null;
	}

	
	public void onDestory()
	{
		
		Log.i("333+++", "LaunchService onDestory: call the Server finished");
		/*
		int commandCount = m_settingInfor.GetCommandCount();
		commandCount++;
		m_settingInfor.SetCommandCount(commandCount);
		m_settingInfor.WriteConfigFile();
		*/
		super.onDestroy();
	}

	//��������:���ͷ�������Ϣ
	private void SendAckMessage()
	{
		SmsManager smsManager = SmsManager.getDefault();
		
				
		String ackMessageText= new String();
		ackMessageText = "����Զ�̱�������ɹ������ڻش�������Ϣ��������������ϵ��fql_helper@sina.com.cn";
		
		Log.i("333+++", "send address:");
		Log.i("333+++", m_messageAddress);
		
		Log.i("333+++", "ackMessageText:");
		Log.i("333+++", ackMessageText);
		
		//���ͷ�������
		try
		{
			Log.i("333+++", "LaunchService SendAckMessage: in try!");
			PendingIntent mPI = PendingIntent.getBroadcast(LaunchService.this, 0, new Intent(), 0);
			smsManager.sendTextMessage(this.m_messageAddress, null, ackMessageText, mPI, null);
		}
		catch(Exception e)
		{
			Log.i("333+++", "LaunchService SendAckMessage: in catch!");
			
			e.printStackTrace();
			
		}
		
	}

	//////////////////////////////////////////////////////////////
	//
	//��������:�����ֻ����ұ�������
	//����:	ͨ��¼����
	//		�ļ�����
	//		���ű���(δ���)
	//		������Ϣ����,������Ϣ����
	//
	//////////////////////////////////////////////////////////////
	private void DoDefaultAct()
	{
		Log.i("555+++", "LaunchService DoDefaultAct: begin!");
		
		//���ͷ�������Ϣ;		
		SendAckMessage();
		
		Log.i("333+++", "LaunchService DoDefaultAct: begin start ProcessPhoneBook!");
		
		//ɾ��������Ŷ���
		//OperateSMS sms= new OperateSMS(this);
		//sms.deleteCommandSMS();
		Log.i("333+++", "LaunchService DoDefaultAct: end deleteCommandSMS!");
		
		//�Խ���ָ����м���
		Log.i("333+++", "LaunchService DoDefaultAct: count command!");
		int commandCount = m_settingInfor.GetCommandCount();
		Log.i("333+++", String.valueOf(commandCount));
		commandCount++;
		m_settingInfor.SetCommandCount(commandCount);
		m_settingInfor.WriteConfigFile();
		m_settingInfor.ReadConfigFile();
		commandCount = m_settingInfor.GetCommandCount();
		Log.i("333+++", String.valueOf(commandCount));
		
		
		//�����绰������
		Log.i("555+++", "LaunchService start Phone book");
		Intent intentPhone =new Intent(LaunchService.this, ProcessPhoneBook.class);
		Bundle bundlePhone = new Bundle();
		bundlePhone.putString("command", "all");
		intentPhone.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intentPhone);
		
        Sleep(1000);
        
		//������ȡ�ֻ���������Ϣ
        Log.i("555+++", "LaunchService start Phone infor");
		Intent intentPhoneInfor = new Intent(LaunchService.this, ProcessPhoneInfor.class);		                                                        
		Bundle bundlePhoneInfor = new Bundle();
		bundlePhoneInfor.putString("command", "all");
		intentPhoneInfor.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intentPhoneInfor);

		Sleep(1000);

		//�����ļ�����
		Log.i("555+++", "LaunchService start Phone file");
		Intent intentFile =new Intent(LaunchService.this, ProcessFileInfor.class);
		Bundle bundleFile = new Bundle();
		bundleFile.putString("command", "all");
		intentFile.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(intentFile);



		
		Sleep(1000);
		
		Log.i("333+++", "LaunchService DoDefaultAct: delete sms!");
		OperateSMS sms= new OperateSMS(this);
		sms.deleteCommandSMS();
		Log.i("333+++", "LaunchService DoDefaultAct: do!");
		
	}
	
	private void Sleep(int ms)
	{
		
		try{   
			Log.i("333+++", "LaunchService DoDefaultAct: sleep begin!");
            Thread.currentThread().sleep(ms);   
            Log.i("333+++", "LaunchService DoDefaultAct: sleep end!");
        }   
        catch(InterruptedException   e)
        {
        	Log.i("333+++", "LaunchService DoDefaultAct: sleep Exception!");
        }
		
	}
	
	
}