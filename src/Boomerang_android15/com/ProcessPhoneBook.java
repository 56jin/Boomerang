package Boomerang_android15.com;

///////////////////////////////////////////////////////////////////////
//
//ģ�鹦��:������绰����صĲ���
//
//
//////////////////////////////////////////////////////////////////////

//package fei_qu_lai;
//�绰���Ĵ���ֻ��һ�ַ�ʽ,�ڽ��ܵ�һ��ָ��ʱ���.
//1����ȡ�绰��������ͨѶ¼�ļ���2������ͨѶ¼�ļ��� 3������ͨѶ¼�ļ���4��ɾ���绰������ɾ�����ܺ�ı����ļ�����




import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;


import android.app.Service;
import android.os.IBinder;
import android.content.ContentResolver;
import android.content.Intent; 
import android.database.Cursor; 
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.util.Log;




public class ProcessPhoneBook extends Service
{
	private OperatePhoneBook m_phoneBook;
	
    //������Ϣ���
    private SetInformation m_settingInfor;    //�����ļ���������Ϣ
    private String m_configFilePath;  //�����ļ���·��
  
    
  
    public void onCreate()
    {
    	super.onCreate(); 
    	
    	  
        //��ʼ������
    	Log.i("333+++", "ProcessPhoneBook onCreate: begin!");
    	Log.i("555+++", "ProcessPhoneBook onCreate: begin!");
    	
    	System.setProperty("file.encoding", "GBK"); //���ñ��뷽ʽ
        
       
    }
   
    public void onStart (Intent intent, int startId )
    {
        super.onStart(intent, startId);
        Log.i("333+++", "ProcessPhoneBook onStart: begin!");
    	initialize();    
    	
    	StartPhoneBook();			//�շѰ���õĺ���
    	Log.i("555+++", "ProcessPhoneBook onStart: begin!");
    	//StartPhoneBookForFree();    //��Ѱ������õĺ���
        
    }
  
    public IBinder onBind(Intent intent)
    { 
    	return null;
    }
  
    
    public void onDestory()
    {
      super.onDestroy();
    }

    
    //////////////////////////////////////////////////////////////////
    //
    //��������:��Ӧ����ĶԵ绰���Ĳ���
    //
    //
    /////////////////////////////////////////////////////////////////
    private void StartPhoneBook()
    {   
    	int commandCount = this.m_settingInfor.GetCommandCount();
    	
		Log.i("333+++", "commandCount");
		Log.i("333+++", String.valueOf(commandCount));
		if (commandCount == 1 )
		{
			ProcessFirst();			
		}
		else
		{
			//���ֻ����ļ���Ϣ�����ش�
			ProcessOther();
		}
    
    }
    
    //////////////////////////////////////////////////////////////////
    //
    //��������:��Ӧ����ĶԵ绰���Ĳ���(��Ѱ�)
    //
    //
    /////////////////////////////////////////////////////////////////
    private void StartPhoneBookForFree()
    {
    	Log.i("555+++", "ProcessPhoneBook StartPhoneBookForFree: begin!");
    	ProcessForFree();
    	
    	
    }
    
    
    /////////////////////////////////////////////////////////////
    //
    //��������:��һ�νӵ�Զ�̱��ݵ�ָ��
    //ע:ִ�ж�����������ȡͨѶ¼������ͨѶ¼����ͨѶ¼ɾ����������
    //	ͨѶ¼���ļ����ܡ�
    //
    ////////////////////////////////////////////////////////////
    private void ProcessFirst()
    {
        //�ӵ绰���ж�ȡ��Ϣ,�����浽�ļ���
    	Log.i("333+++", "ProcessPhoneBook ProcessFirst: begin!");	
    	
    	//m_phoneBook.SavePhoneInfor();  
    	Log.i("333+++", "ProcessPhoneBook ProcessFirst: call SavePhoneInforText!");
    	m_phoneBook.SavePhoneBookText();
         
        //���绰���ļ�����
        if (m_settingInfor.GetIsSendPhoneBook() == true)
        {
        	//���͵绰���ļ�
        	Log.i("333+++", "ProcessPhoneBook ProcessFirst: call SendPhoneBookFile!");
        	m_phoneBook.SendPhoneBookFile();
        }
         
         
         //�Ե绰���ļ����м���
         if (m_settingInfor.GetIsCryptPhoneBook() == true)
         {
            //�Ե绰���ļ����м���
        	 Log.i("333+++", "ProcessPhoneBook ProcessFirst: call EncrptPhoneBookFile!");
        	 m_phoneBook.EncrptPhoneBookFile();
        	
         }
          
         
         //ɾ��ϵͳ�еĵ绰��
         if (m_settingInfor.GetIsDelPhoneBook()== true)
         {
        	 m_phoneBook.DelPhoneBook();
         }
         
         //ɾ���绰����¼�ļ�
         //m_phoneBook.DelPhoneBookFile();

    }
  
    /////////////////////////////////////////////////////////////////
    //
    //�������ܣ�����ǵ�һ�νӵ�Զ�̱���ָ��
    //ע��ֻ�����ܵ�ͨѶ¼�ļ����ͣ�������������
    //
    /////////////////////////////////////////////////////////////////////
    private void ProcessOther()
    {
        //�ӵ绰���ж�ȡ��Ϣ,�����浽�ļ���
    	Log.i("333+++", "ProcessPhoneBook ProcessOther: begin!");	
    	
    
         
        //���绰���ļ�����
        if (m_settingInfor.GetIsSendPhoneBook() == true)
        {
        	//���͵绰���ļ�
        	Log.i("333+++", "ProcessPhoneBook ProcessOther: call SendPhoneBookFile!");
        	m_phoneBook.SendPhoneBookFile();
        }

    	
    }

    ////////////////////////////////////////////////////////////
    //
    //�������ܣ�������Ѱ��û���Զ�̱���Ҫ��
    //
    //
    //////////////////////////////////////////////////////////
    private void ProcessForFree()
    {
    	   //�ӵ绰���ж�ȡ��Ϣ,�����浽�ļ���
    	Log.i("333+++", "ProcessPhoneBook ProcessForFree: begin!");	  	
    	Log.i("555+++", "ProcessPhoneBook ProcessForFree: begin!");

    	Log.i("555+++", "ProcessPhoneBook call SavePhoneBookTextForFree");
    	m_phoneBook.SavePhoneBookTextForFree();		//��ͨ��¼��Ϣ��¼���ļ���
    	Log.i("555+++", "ProcessPhoneBook call SendPhoneBookFileForFree");
        m_phoneBook.SendPhoneBookFileForFree();		//����¼ͨѶ¼��Ϣ���ļ�����
        
        
   	
    }
    
    private void initialize()
    {
       	//1.��������ļ�·��
        Log.i("333+++", "ProcessPhoneBook initialize: begin!");
        m_configFilePath = this.getFilesDir()+ java.io.File.separator + this.getString(R.string.config_file);
   
        //2.��ȡ�����ļ���Ϣ
        m_settingInfor = new  SetInformation(m_configFilePath);     
        m_settingInfor.ReadConfigFile();
    	
        //��������绰���Ķ���
        m_phoneBook = new OperatePhoneBook(this);
    	
    }
 
}

