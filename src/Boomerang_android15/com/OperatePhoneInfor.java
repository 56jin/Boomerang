package Boomerang_android15.com;

//ģ�鹦��:��ģ�鹦���ǻ�ȡ�ֻ��������Ϣ,���ֻ���


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

public class OperatePhoneInfor
{	
	private Context m_context;
	private TelephonyManager m_TelMgr;
	private ContentResolver m_cv;
	
	private String m_simState = new String();  //ȡ��SIM��״̬ 
	private String m_simNumber = new String();	//SIM������ 
	private String m_simOperator = new String(); //SIM�������̴���
	private String m_simOperName = new String(); //SIM������������
	private String m_simCountryIso = new String(); //SIM������ 	
	private String m_telNum = new String();	//�ֻ��绰���� ##
	private String m_netCountry = new String(); //�����������
	private String m_companyCode = new String();	//���Ź�˾����
	private String m_companyName = new String();	//���Ź�˾����
	private String m_communiteType = new String();	//�ƶ�ͨ������
	private String m_netType = new String();	//�������� 
	private String m_roamingState = new String();//����״̬
	private String m_imeiId = new String();	//�ֻ�IMEI 
	private String m_imeiSVId = new String(); //IMEI SV
	private String m_imsi = new String();//�ֻ�IMSI
	private String m_bluetoothState = new String(); //����״̬
	private String m_wifiState = new String() ;//WIFI״̬
	private String m_airplaneMode = new String();  //����ģʽ�Ƿ��
	private String m_dataRoamingState = new String(); //���������Ƿ��
	
	//�ļ���ر�������
	private File m_fileDir;	//��ǰ�����·��
	private String m_phoneInforFile;	//���������ļ�Ŀ¼���� ���ļ�
	private File m_filePhone;		//�����ļ�Ŀ¼�ļ����ļ�����
	private BufferedWriter m_bw;	//���ڽ��ļ�Ŀ¼��Ϣд���ļ�Ŀ¼�ļ���.
	private BufferedReader m_br; 		//���ڶ����ļ�Ŀ¼��Ϣ;
	
	//�����ļ����	
	private SetInformation m_settingInfor;		//�����ļ���������Ϣ
	private String m_configFilePath;	//�����ļ���·��
	
    //�������
    private FqlEmail m_mailPort ;  //ͨ�������ʼ��Ľӿ�
    

    //֧�ּ��ܹ���
	private encryption myEncrpt = new encryption();
	
	
	
	OperatePhoneInfor(Context context)
	{
		m_context = context;
		initialize();
	}

	
    private void initialize()
	{
		////////////////////////////////////
		// 1.��õ�ǰ�����sd�� ��·��
		///////////////////////////////////////
		Log.i("333+++", "ProcessFileInfor initialize: begin!");
		m_fileDir = m_context.getFilesDir();		
		
			
			
		///////////////////////////////////////
		// 2. ��ʼ��Ŀ¼�ļ���·��		
		///////////////////////////////////////
		m_configFilePath = m_fileDir+ java.io.File.separator + m_context.getString(R.string.config_file);
			
		//��ȡ�����ļ���Ϣ
		m_settingInfor = new  SetInformation(m_configFilePath);     
		m_settingInfor.ReadConfigFile();
			
		m_phoneInforFile = m_fileDir + java.io.File.separator + m_settingInfor.GetPhoneInforName();
			
		////////////////////////////////////////
		// 3.�������������ļ�Ŀ¼��Ϣ���ļ����ļ�����.
		///////////////////////////////////////////
		m_filePhone = new File(m_phoneInforFile);
		

	}
	
	
	
    public void GetPhoneInfor()
    {
    	m_TelMgr = (TelephonyManager)m_context.getSystemService(m_context.TELEPHONY_SERVICE); 
  
    	//ȡ��SIM��״̬ 
    	if(m_TelMgr.getSimState()==m_TelMgr.SIM_STATE_READY)
    	{
    		m_simState = "����";
    	}
    	else if(m_TelMgr.getSimState()==m_TelMgr.SIM_STATE_ABSENT)
    	{
    		m_simState = "��SIM��";
  
   	    }
    	else
        {
    		m_simState = "SIM����������δ֪��״̬";         
        }
    	
    	// ȡ��SIM������ 
        if(m_TelMgr.getSimSerialNumber()!=null)
        {
        	m_simNumber  = m_TelMgr.getSimSerialNumber();
         
        }
        else
        {
        	m_simNumber  = "�޷�ȡ��";          
        }
    	
        /* ȡ��SIM�������̴��� */
        if(m_TelMgr.getSimOperator().equals(""))
        {
        	m_simOperator = "�޷�ȡ��";
        }
        else
        {
        	m_simOperator = m_TelMgr.getSimOperator();
          
        }
    	
        /* ȡ��SIM������������ */
        if(m_TelMgr.getSimOperatorName().equals(""))
        {
        	m_simOperName = "�޷�ȡ��";
        }
        else
        {
        	m_simOperName = m_TelMgr.getSimOperatorName();
        
        }
        
        /* ȡ��SIM������ */
        if(m_TelMgr.getSimCountryIso().equals(""))
        {
        	m_simCountryIso = "�޷�ȡ��";
          
        }
        else
        {
        	m_simCountryIso = m_TelMgr.getSimCountryIso();
          
        }
    	
    	
    	
    	
    	
    	//ȡ���ֻ��绰���� 
    	if(m_TelMgr.getLine1Number()!=null)
    	{
    		m_telNum = m_TelMgr.getLine1Number();
    	}
    	else
    	{
    		m_telNum = "�޷�ȡ��";
    		
    	}//end if
    	
    	//ȡ�õ����������
    	if(m_TelMgr.getNetworkCountryIso().equals(""))
        {
    		m_netCountry = "�޷�ȡ��";
        }
        else
        {
        	m_netCountry = m_TelMgr.getNetworkCountryIso();
        }
    	
    	//ȡ�õ��Ź�˾����
        if(m_TelMgr.getNetworkOperator().equals(""))
        {
        	m_companyCode = "�޷�ȡ��";
        }
        else
        {
        	m_companyCode = m_TelMgr.getNetworkOperator();
        }

        
        //ȡ�õ��Ź�˾����
        if(m_TelMgr.getNetworkOperatorName().equals(""))
        {
         
          	m_companyName = "�޷�ȡ��";
        }
        else
        {
        	m_companyName = m_TelMgr.getNetworkOperatorName();
        }
        
        
        // ȡ���ж�ͨ������ 
        if(m_TelMgr.getPhoneType()==m_TelMgr.PHONE_TYPE_GSM)
        {
        	m_communiteType = "GSM";
          
        }
        else
        {
        	m_communiteType = "δ֪";
        }
        
        
        // ȡ���������� 
        
        if(m_TelMgr.getNetworkType()==m_TelMgr.NETWORK_TYPE_EDGE)
        {
        	m_netType = "EDGE";
         
        }
        else if(m_TelMgr.getNetworkType()==m_TelMgr.NETWORK_TYPE_GPRS)
        {
        	m_netType = "GPRS";
          
        }
        else if(m_TelMgr.getNetworkType()==m_TelMgr.NETWORK_TYPE_UMTS)
        {
        	m_netType = "UMTS";
          
        }
        else if(m_TelMgr.getNetworkType()==4)
        {
        	m_netType = "HSDPA";
          
        }
        else
        {
        	m_netType = "δ֪";
         
        }
        
        
        //ȡ������״̬ 
        if(m_TelMgr.isNetworkRoaming())
        {
        	m_roamingState = "������";
          
        }
        else
        {
        	m_roamingState = "������";
         
        }
        
        
        
        
        //ȡ���ֻ�IMEI 
        m_imeiId  = m_TelMgr.getDeviceId();

        
        //ȡ��IMEI SV        
        if(m_TelMgr.getDeviceSoftwareVersion()!=null)
        {
        	m_imeiSVId = m_TelMgr.getDeviceSoftwareVersion();
         
        }
        else
        {
        	m_imeiSVId = "�޷�ȡ��";
        }
        
        //ȡ���ֻ�IMSI 
        if(m_TelMgr.getSubscriberId()!=null)
        {
        	m_imsi  = m_TelMgr.getSubscriberId();
          
        }
        else
        {
        	m_imsi = "�޷�ȡ��";
        }
        
        ///////////////////////////////////////////////////////////////////////////
        //ȡ��ContentResolver 
        m_cv = m_context.getContentResolver();
        String tmpS="";
        
        //ȡ������״̬
        tmpS=android.provider.Settings.System.getString(m_cv, android.provider.Settings.System.BLUETOOTH_ON);
        if(tmpS.equals("1"))
        {
        	m_bluetoothState  = "�Ѵ�";
      
        }
        else
        {
        	m_bluetoothState = "δ��";
         
        }
        
        /* ȡ��WIFI״̬ */
        tmpS=android.provider.Settings.System.getString(m_cv, android.provider.Settings.System.WIFI_ON);
        if(tmpS.equals("1"))
        {
        	m_wifiState = "�Ѵ�";
          
        }
        else
        {
        	m_wifiState = "δ��";
        
        }
        
        /* ȡ�÷���ģʽ�Ƿ�� */
        tmpS=android.provider.Settings.System.getString(m_cv, android.provider.Settings.System.AIRPLANE_MODE_ON);
        if(tmpS.equals("1"))
        {
        	m_airplaneMode = "����";
          
        }
        else
        {
        	m_airplaneMode = "δ��";
          
        }
        
        /* ȡ�����������Ƿ�� */
        tmpS=android.provider.Settings.System.getString(m_cv, android.provider.Settings.System.DATA_ROAMING);    
		if(tmpS.equals("1"))
        {
        	m_dataRoamingState = "����";
         
        }
        else
        {
        	m_dataRoamingState = "δ��";
         
        }
        
        
    }
    
    public void WritePhoneInfor()
    {
    	String InforString = new String();
    	
		try
		{
			OutputStream out = new FileOutputStream(m_filePhone); 
			m_bw  = new BufferedWriter(new OutputStreamWriter(out, "GBK")); 
			//m_bw = new BufferedWriter(new FileWriter(m_filePhone));
			
			InforString = "�ֻ�������Ϣ\r\n";
			InforString += "SIM��״̬:" + m_simState + "\r\n";			
			InforString += "SIM������:" + m_simNumber + "\r\n";
			InforString += "SIM����Ӧ�̴���:" + m_simOperator + "\r\n";
			InforString += "SIM����Ӧ������:" + m_simOperName + "\r\n";
			InforString += "SIM������:" + m_simCountryIso + "\r\n";				
			InforString += "�ֻ��绰����:" + m_telNum + "\r\n";
			InforString += "�����������:" + m_netCountry + "\r\n";
			InforString += "���Ź�˾����:" + m_companyCode + "\r\n";
			//InforString += "���Ź�˾����:" + m_companyName + "\r\n";
			InforString += "�ƶ�ͨ������:" + m_communiteType + "\r\n";
			InforString += "��������:" + m_netType + "\r\n";
			InforString += "�ֻ�����״̬:" + m_roamingState + "\r\n";			
			InforString += "�ֻ�IMEI:" + m_imeiId + "\r\n";
			InforString += "IMEI SV:" + m_imeiSVId + "\r\n";
			InforString += "�ֻ�IMSI:" + m_imsi + "\r\n";			
			InforString += "����״̬:" + m_bluetoothState + "\r\n";
			InforString += "WIFI״̬:" + m_wifiState + "\r\n";
			InforString += "����ģʽ:" + m_airplaneMode + "\r\n";
			InforString += "��������:" + m_dataRoamingState + "\r\n\r\n";
			
			m_bw.write(InforString);	
			
			m_bw.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
		
    }
    
    public void ReadPhoneInfor()
    {
    	String InforLine = new String();
    	int index =0;
    	
		try
		{
			
			m_br = new BufferedReader(new FileReader(m_filePhone));
			
		
			//�ֻ�������Ϣ
			InforLine = m_br.readLine();
			
			//SIM��״̬:	
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_simState = InforLine.substring(index+1);			
			
			//SIM������
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_simNumber = InforLine.substring(index+1);
			
			//SIM����Ӧ�̴���
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_simOperator = InforLine.substring(index+1);			
			
			//SIM����Ӧ������
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_simOperName = InforLine.substring(index+1);
			
			//SIM������		
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_simCountryIso = InforLine.substring(index+1);
			
			//�ֻ��绰����
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_telNum = InforLine.substring(index+1);			
			
			//�����������
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_netCountry  = InforLine.substring(index+1);
			
			//���Ź�˾����
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_companyCode  = InforLine.substring(index+1);
			
			//���Ź�˾����
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_companyName  = InforLine.substring(index+1);			
			
			//�ƶ�ͨ������
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_communiteType  = InforLine.substring(index+1);
			
			//��������
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_netType  = InforLine.substring(index+1);			
			
			//�ֻ�����״̬
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_roamingState  = InforLine.substring(index+1);			
			
			//�ֻ�IMEI
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_imeiId  = InforLine.substring(index+1);	
			
			//IMEI SV
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_imeiSVId  = InforLine.substring(index+1);			
			
			//�ֻ�IMSI
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_imsi = InforLine.substring(index+1);	
			
			//����״̬
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_bluetoothState = InforLine.substring(index+1);	
			
			//WIFI״̬
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_wifiState = InforLine.substring(index+1);	
			
			//����ģʽ
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_airplaneMode = InforLine.substring(index+1);	
			
			//��������;
			InforLine = m_br.readLine();
			index = InforLine.indexOf(":");
			m_dataRoamingState = InforLine.substring(index+1);
	
			m_br.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
    	
    }
    
    
    ///////////////////////////////////////////////////
    //
    //��������:ȡ��SIM��״̬
    //
    /////////////////////////////////////////////////////
    public String GetSimState()
    {
    	return m_simState;
    }
    
    
    ///////////////////////////////////////////////////
    //
    //��������:ȡ��SIM������ 
    //
    /////////////////////////////////////////////////////
    public String GetSimNumber()
    {
    	return m_simNumber;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:ȡ��SIM�������̴���
    //
    /////////////////////////////////////////////////////
    public String GetSimOperator()
    {
    	return m_simOperator;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:ȡ��SIM������������
    //
    /////////////////////////////////////////////////////
    public String GetSimOperName()
    {
    	return m_simOperName;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡSIM������
    //
    /////////////////////////////////////////////////////
    public String GetSimCountryIso()
    {
    	return m_simCountryIso;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ�ֻ��绰���� ##
    //
    /////////////////////////////////////////////////////
    public String GetTelNum()
    {
    	return m_telNum;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ�����������
    //
    /////////////////////////////////////////////////////
    public String GetNetCountry()
    {
    	return m_netCountry;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ���Ź�˾����
    //
    /////////////////////////////////////////////////////
    public String GetCompanyCode()
    {
    	return m_companyCode;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ���Ź�˾����
    //
    /////////////////////////////////////////////////////
    public String GetCompanyName()
    {
    	return m_companyName;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ�ƶ�ͨ������
    //
    /////////////////////////////////////////////////////
    public String GetCommuniteType()
    {
    	return m_communiteType;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ�������� 
    //
    /////////////////////////////////////////////////////
    public String GetNetType()
    {
    	return m_netType;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ����״̬
    //
    /////////////////////////////////////////////////////
    public String GetRoamingState()
    {
    	return m_roamingState;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ�ֻ�IMEI 
    //
    /////////////////////////////////////////////////////
    public String GetImeiId()
    {
    	return m_imeiId;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡmeiSVId
    //
    /////////////////////////////////////////////////////
    public String GetImeiSVId()
    {
    	return m_imeiSVId;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ�ֻ�IMSI
    //
    /////////////////////////////////////////////////////
    public String GetImsi()
    {
    	return m_imsi;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ����״̬
    //
    /////////////////////////////////////////////////////
    public String GetBluetoothState()
    {
    	return m_bluetoothState;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡWIFI״̬
    //
    /////////////////////////////////////////////////////
    public String GetWifiState()
    {
    	return m_wifiState;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ����ģʽ�Ƿ��
    //
    /////////////////////////////////////////////////////
    public String GetAirplaneMode()
    {
    	return m_airplaneMode;
    }
    
    ///////////////////////////////////////////////////
    //
    //��������:��ȡ���������Ƿ��
    //
    /////////////////////////////////////////////////////
    public String GetDataRoamingState()
    {
    	return m_dataRoamingState;
    }
    
    //////////////////////////////////////////////////////////////
    //
    //��������:��ñ���phone������Ϣ���ļ�������(ȫ·��)
    //
    //m_phoneInforFile
    /////////////////////////////////////////////////////////////////
    public String GetPhoneInforFile()
    {
    	
    	return m_phoneInforFile;
    }
    
    public void SendPhoneInfor()
    {
       	Log.i("333+++", "ProcessPhoneBook SendPhoneBookFile: begin!");
    	Vector<String> emailAddress = new Vector<String>();
    	emailAddress.add(m_settingInfor.GetEmail_1());
    	emailAddress.add(m_settingInfor.GetEmail_2());
    	String Subject = new String();
    	String text = new String();
    	String filePath = new String();
	  
    	Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
    	Subject += "----�ֻ�������Ϣ";
    	
    	//�����ʼ�����
    	text = m_context.getString(R.string.email_text);	//���������Ϣ
    	text += m_context.getString(R.string.app_version); //����汾��Ϣ
    	text += "\r\n �ֻ���Ϣ���԰�����ȡ֤! \r\n";
    	filePath = this.m_phoneInforFile;	//�����ʼ�������·��
    	
    	
    	m_mailPort = new FqlEmail();
    	m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
	  
    	Log.i("333+++", "ProcessPhoneBook SendPhoneBookFile: begin!");
    }
    
    /////////////////////////////////////////////////////////////////////
    //
    //��������:����⵽sim��������ʱ,���������ʼ�
    //
    ////////////////////////////////////////////////////////////////////
    public void SendPhoneAlarm()
    {
       	Log.i("333+++", "ProcessPhoneBook SendPhoneBookFile: begin!");
    	Vector<String> emailAddress = new Vector<String>();
    	emailAddress.add(m_settingInfor.GetEmail_1());
    	emailAddress.add(m_settingInfor.GetEmail_2());
    	String Subject = new String();
    	String text = new String();
    	String filePath = new String();
	  
    	Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
    	Subject += "----����";
    	
    	//�����ʼ�����
    	text = m_context.getString(R.string.email_text);	//���������Ϣ
    	text += m_context.getString(R.string.app_version); //����汾��Ϣ
    	text += "\r\n ���ֻ���sim��������! \r\n" ;
    	text +="�µ��ֻ�����:" + this.m_telNum + "\r\n";
    	
    	filePath = this.m_phoneInforFile;	//�����ʼ�������·��
    	
    	
    	m_mailPort = new FqlEmail();
    	m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
	  
    	Log.i("333+++", "ProcessPhoneBook SendPhoneBookFile: begin!");
    }
    
    
    ////////////////////////////////////////////////////////////////////
    //
    //��������:�޸��ֻ���Ϣ�ļ����ļ�����׺
    //
    //
    /////////////////////////////////////////////////////////////////////
    public void ChangeFileName()
    {
    	
    	
    }
    
    public void EncryptPhoneInfor()
    {
    	
    	
    }
    
}





