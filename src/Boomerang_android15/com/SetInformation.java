package Boomerang_android15.com;



////////////////////////////////////////////////////////////////
//
//ģ�鹦��:ʵ�ֶ�xml�����ļ��Ķ�ȡ���޸ġ���Ӳ���
//�ᱻRegisterFaceģ��ʹ�� 
//����������µ�������Ϣ�ķ�����:
//(1)�������ر�����Ϊ��ĳ�Ա����
//(2)���޸�ParseOneitem�����Ӵ������ļ��ж����¼�������Ĵ���
//(3)���޸�ConstructConfigLine() ���ڽ��¼ӵ�������д�������ļ�
//(4)��������Ӧ�Ľӿں���:Get��Set���ú���
/////////////////////////////////////////////////////////////////



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File; 
//import java.io.FileNotFoundException; 
//import java.io.FileOutputStream; 
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.IOException; 
import java.util.List;
import java.util.ArrayList;
//import java.util.Vector;


public class SetInformation
{ 
	private List <String> m_config; 
	
	
	private String m_configFilePath; //�����ļ���ȫ·��
	
	
	
	//#register
	private final String tagUserName = "username";	//�û���
	private String m_userName = "";	//��������,ͨ����������
	private final String tagPassword = "password";	//����
	private String m_passWord = "";	//��������,ͨ����������
	private final String tagEmail_1 = "email_1";	//��һ����
	private String m_email_1 = "";	//��������,ͨ����������
	private final String tagEmail_2 = "email_2";	//�ڶ�����
	private String m_email_2 = "";	//��������,ͨ����������
	
	private final String tagRoot_email = "root_email";	//��������Ա����
	private String m_root_email = "feiqulaimobile@sina.com";
	private final String tagRoot_email_2 = "root_email_2";	//��������Ա����
	private String m_root_email_2 = "1321490265@qq.com";	//��������
	
	
	//#setting
	private final String tagDataPath = "dataPath";	//���������ļ���·��
	private String m_dataPath = ""; //��RegisterFaceģ�鸺������
	
	private final String tagPhoneBookInforName = "phoneBookInforName";	//����绰���ļ�����
	private String m_phoneBookInforName = "phoneName.txt";
	
	private final String tagPhoneInforName = "phoneInforName";	//�����ֻ�������Ϣ���ļ�������
	private String m_phoneInforName = "phoneInfor.txt";
	
	private final String tagPhoneSMSInforName = "phoneSMSInforName";	//�����ֻ��ж������ݵ��ļ�������
	private String m_phoneSMSInforName = "phoneSMSInfor.txt";
	
	private final String tagFileCatalogName = "fileCatalogName";	//�����ļ�Ŀ¼���ļ�����
	private String m_fileCatalogName = "fileCalalog.txt";
	
	private final String tagSearchFilePath = "searchFilePath";	//��Ҫ�������ļ���·��
	private String [] m_searchFilePath= null; //!!!!��RegisterFaceģ������,���ڱ�ģ���޷����ϵͳĿ¼��Ϣ.
	private final String tagSendFileType = "sendFileType";		//��Ҫ���͵��ļ�����
	private String [] m_sendFileType = {".txt", ".rtf", ".doc", ".xls", ".ppt", ".wps", ".et", ".dps", ".pdf", "", "", "", "", ""};
	private final String tagCryptFileType = "cryptFileType";	//��Ҫ���ܵ��ļ�����
	private String [] m_cryptFileType = {
			".rtf", ".doc", ".xls", ".ppt", ".wps", ".et", ".dps", ".pdf", 	//�ı���ʽ
			".bmp", ".gif", ".jpeg", ".jpg", ".jpe", //ͼƬ��ʽ			 		 
			 ".mp3", ".ra", ".rm", ".ram", ".rmvb", ".wav", ".wma", ".wmv",//��Ƶ��ʽ	
			 ".avi",  ".mpg", "mpeg", ".m4p", ".m4b","", "", "", "", ""	//��Ƶ��ʽ			
			};  //��Ҫ���ܵ��ĵ�,����Ҫɾ�����ĵ�
	
	//��֧�ִ�����̬������չ���Ĳ���,�����.
	/*
	{
			"txt", "rtf", "doc", "xls", "ppt", "wps", "et", "dps", "pdf", 	//�ı���ʽ
			"bmp", "dib", "rle", "rmf", "gif", "jpeg", "jpg", "jpe", "jif", "jfif", "thm", //ͼƬ��ʽ
			"dcx", "pcx", "pic", "gng", "tga", "tiff", "tif", "xif", "wbmp", "wbm",
			"aac", "aa", "ac3", "a52", "aif", "aifc", "aiff", "au", "snd", "cda", "cue", "dts", //��Ƶ��ʽ
			"dtswav", "flac", "fla", "midi", "mid", "rmi", "mod", "far", "it", "s3m", "stm", 
			"mtm", "umx", "xm", "mp3", "mp2", "mp1", "mpa", "mp3pro", "m4a", "mp4", "ape", 
			"mac", "mpc", "mpt", "ra", "rm", "ram", "rmvb", "tta", "oog", "wav", "wma", "wmv",
			"asf",  
			"avi",  "wmp", "wm", "rpm",  "rt", "rp", "smi",	"smil", "scm", "mpg", "mpeg", "mpe",	//��Ƶ��ʽ
			"mlv", "m2v", "mpv2", "mp2v", "dat", "m4v", "m4p", "m4b", "ts", "tp", "tpr", "pva",
			"pss", "wv", "vob", "ifo", "mov", "qt", "mr", "3gp", "3gpp", "3g2", "3gp2"
			};  


	*/
	
	
	
	private final String tagTime = "time";	//ʱ�䷧ֵ,��λ:��
	private int m_time = 1000;
	private final String tagDistance = "distance";	//ʱ�䷧ֵ,��λ:��
	private int m_distance=1000;
	
	private final String tagCommandCount = "conmmand_count";	//�յ�ָ��Ĵ���
	private int m_commandCount=0;
	
	//#action	
	//�绰���Ĳ���
	private final String tagIsSendPhoneBook = "IsSendPhoneBook";	//�Ƿ��͵绰��,Ĭ�Ϸ���
	private boolean m_isSendPhoneBook = true ;
	private final String tagIsCryptPhoneBook = "IsCryptPhoneBook";	//�Ƿ���ܱ��ص绰��,���ܴӵ绰�����ɵ��ļ�
	private boolean m_isCryptPhoneBook = true;
	private final String tagIsDelPhoneBook = "IsDelPhoneBook";	//�Ƿ�ɾ�����ص绰��,Ĭ��ɾ��
	private boolean m_isDelPhoneBook = true;
	//�ļ��Ĳ���
	private final String tagIsSendFileCatalog = "IsSendFileCatalog";	//�Ƿ��ͱ����ļ�Ŀ¼,Ĭ�Ϸ���
	private boolean m_isSendFileCatalog = true;
	private final String tagIsCryptFile = "IsCryptFile";	//�Ƿ���ļ�����,�ص����ļ���,���ļ�ͷ,1.0Ĭ�ϲ�����
	private boolean m_isCryptFile = true;
	private final String tagIsDelFile = "IsDelFile";	//�Ƿ�ɾ�������ļ�,Ĭ��ɾ��,����ָ���Ǽ��ܺ�Ĳ�ɾ��
	private boolean m_isDelFile = false;
	private final String tagIsSendFile = "IsSendFile";	//�Ƿ������ļ�,Ĭ�Ϸ���
	private boolean m_isSendFile = true;
	//�ֻ��ŵļ��
	private final String tagIsMonitorNum = "IsMonitorNum";	//�Ƿ����ֻ��ŵı��,!!!!��ʱ���ṩ�ù���
	private boolean m_isMonitorNum = false;
	private final String tagIsMonitorPosition ="IsMonitorPosition";	//�Ƿ����ֻ�λ�õı��
	private boolean m_isMonitorPosition = true;
	//���ż��
	

	
	public SetInformation(String configFilePath) 
	{ 
		//List <String> m_config;
		//List<String> fl = new ArrayList<String>();
		m_configFilePath = configFilePath;
		m_config = new ArrayList<String>();    
		
	}
	
	//���ļ��ж���������Ϣ
	public boolean ReadConfigFile()
	{
		File configFile = new File(m_configFilePath);
		BufferedReader br; 		//���ڶ����ļ�Ŀ¼��Ϣ;
		String oneConfigLine;		//�����ļ��е�һ��
		
		if (!configFile.exists())
		{
			//����ļ�������,����False;
			return false;
		}
		
		long len = configFile.length();
		
		if(configFile.length()== 0)
		{
			return false;
		}
		
		//���������ļ�����������д��List��ȥ
		try
		{
			br = new BufferedReader(new FileReader(configFile));
			
			oneConfigLine = br.readLine();
			
			while (oneConfigLine != null)
			{
				m_config.add(oneConfigLine);
				oneConfigLine = br.readLine();
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();			
				
		}
		
		ParseConfigLine();
		
		//����������,��list�е��������
		m_config.clear();
		return true;
	

	}
	


	////////////////////////////////////////////////////////
	//
	//��������:���޸ĺ��������Ϣд�������ļ�
	//����ֵ:����ɹ�����true
	//
	/////////////////////////////////////////////////////////
	public void WriteConfigFile()
	{
		//���浽��һ���ļ���,ɾ��ԭ�ļ�,������
		
		File newConfigFile= new File(m_configFilePath+".bat"); //�����ļ�
		BufferedWriter bw; 		//���ڶ����ļ�Ŀ¼��Ϣ;
		
		
		if (newConfigFile.exists())
		{
			//����ļ�������,����False;
			newConfigFile.delete();
		}
		
		try
		{
			newConfigFile.createNewFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
		
		//���������ļ���
		ConstructConfigLine();
		
		try 
		{
			bw = new BufferedWriter(new FileWriter(newConfigFile));
						
			for (int i=0; i<m_config.size(); i++)
			{
				bw.write(m_config.get(i));
				bw.newLine();
				
			}//end for
			bw.flush();
			bw.close();	
		}
		catch (Exception e)
		{
			e.printStackTrace();		
			
		}//end try
		
		//long len = newConfigFile.length();
		//ɾ��ԭ�����ļ�,�������ļ�����
		File oldConfigFile= new File(m_configFilePath);
		oldConfigFile.delete();
		newConfigFile.renameTo(oldConfigFile);
		
		
		
	}//end WriteConfigFile
	
	
	
	////////////////////////////////////////////////////
	//
	//����ע����Ϣ
	//
	////////////////////////////////////////////////////
	public void SetUserName(String userName)
	{
		
		m_userName = userName;
	}
	
	public String GetUserName()
	{
		
		return m_userName;
	}
	
	
	public void SetPassWord(String password)
	{
		this.m_passWord = password;
	}
	
	public String GetPassWord()
	{
		
		return this.m_passWord;
	}
	
	
	public void SetEmail_1(String email_1)
	{
		this.m_email_1 = email_1;
	}
	
	public String GetEmail_1()
	{		
		return m_email_1;
	}
	
	
	
	public void SetEmail_2(String email_2)
	{
		this.m_email_2 = email_2;
	}
	
	public String GetEmail_2()
	{
		
		return m_email_2;
	}
	
	
	public void SetRootEmail(String rootEmail)
	{
		this.m_root_email = rootEmail;
		
	}
	
	public String GetRootEmail()
	{
		
		return m_root_email;
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	//
	//����������Ϣ
	//
	////////////////////////////////////////////////////////////////////////////
	//���ñ����ļ���·��
	public void SetDataPath(String dataPath)
	{
		this.m_dataPath = dataPath;		
	}
	
	public String GetDataPath()
	{		
		return m_dataPath;
		
	}
	
	public void SetPhoneBookInforName(String phoneBookInforName)
	{
		this.m_phoneBookInforName = phoneBookInforName;
		
	}
	
	public String GetPhoneBookInforName()
	{		
		return m_phoneBookInforName;
	}
	
	/*
	 	private final String tagPhoneInforName = "phoneInforName";	//�����ֻ�������Ϣ���ļ�������
	private String m_phoneInforName = "phoneInfor.fql";
	
	private final String tagPhoneSMSInforName = "phoneSMSInforName";	//�����ֻ��ж������ݵ��ļ�������
	private String m_phoneSMSInforName = "phoneSMSInfor.fql";
	 * */
	
	////////////////////////////////////////////////////////////
	//
	//��������:�����ֻ�������Ϣ���ļ�������
	//
	/////////////////////////////////////////////////////////////
	public void SetPhoneInforName(String phoneInforName)
	{
		m_phoneInforName = phoneInforName;
	}
	public String GetPhoneInforName()
	{
		return m_phoneInforName;
	}

	////////////////////////////////////////////////////////////
	//
	//��������:�����ֻ��ж������ݵ��ļ�������
	//
	/////////////////////////////////////////////////////////////
	public void SetPhoneSMSInforName(String phoneSMSInforName)
	{
		m_phoneSMSInforName = phoneSMSInforName;
	}
	public String GetPhoneSMSInforName()
	{
		return m_phoneSMSInforName;
	}
	
	
	public void SetFileCatalogName(String fileCatalogName)
	{
		this.m_fileCatalogName = fileCatalogName;
	}
	public String GetFileCatalogName()
	{
		return m_fileCatalogName;
	}
	
	
	
	public void SetSearchFilePath(String [] searchFilePaths)
	{
		this.m_searchFilePath = searchFilePaths;
	}
	public void AddSearchFilePath(String searchFilePath)
	{
		int len = m_searchFilePath.length;
		m_searchFilePath[len] = searchFilePath;
	}
	public String[] GetSearchFilePath()
	{
		return m_searchFilePath;
	}
	
	
	
	public void SetSendFileType(String []sendFileTypes)
	{
		
		this.m_sendFileType =  sendFileTypes;
	}
	public void AddSendFileType(String sendFileType)
	{
		int len = m_sendFileType.length;
		m_sendFileType[len] = sendFileType;
	}
	public String[] GetSendFileType()
	{
		return m_sendFileType;
	}
	public boolean IsSendFileType(String filePath)
	{
		
	
		String end = filePath.substring(filePath.lastIndexOf(".")+1, filePath.length()).toLowerCase();
		if (end.length()>5 || end.length()<=0)
		{
			//��õĺ�׺���쳣����false
			return false;
		}//end
		
		for(int i=0; i<m_searchFilePath.length; i++)
		{
			if(m_searchFilePath[i]==end)
			{
				return true;
			}
		}//end
		
		return false;
	}
	
	public void SetCryptFileType(String [] cryptFileTypes)
	{
		this.m_cryptFileType = cryptFileTypes;
	}
	
	public void AddCryptFileType(String cryptFileType)
	{
		int len = this.m_cryptFileType.length;
		m_cryptFileType[len] = cryptFileType;
	}
	
	public String []GetCryptFileType()
	{
		return m_cryptFileType;
	}
	
	public boolean IsCryptFileType(String filePath)
	{	
		String end = filePath.substring(filePath.lastIndexOf(".")+1, filePath.length()).toLowerCase();
		if (end.length()>5 || end.length()<=0)
		{
			//��õĺ�׺���쳣����false
			return false;
		}//end if
		
		for(int i=0; i<m_cryptFileType.length; i++)
		{
			if(m_searchFilePath[i]==end)
			{
				return true;
			}
		}//end for		
		return false;
	}
	
	
	public void SetTime(int time)
	{
		this.m_time = time;
	}
	
	public int GetTime()
	{
		return m_time;
	}
	
	public void SetDistance(int distance)
	{
		this.m_distance = distance;
	}
	
	public int GetDistance()
	{
		return m_distance;
	}
	
	public void SetCommandCount(int commandCount)
	{
		this.m_commandCount = commandCount;
	}
	
	public int GetCommandCount()
	{
		return this.m_commandCount;
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	//
	//���Ϣ
	//
	/////////////////////////////////////////////////////////////////
	public void SetIsSendPhoneBook(boolean isSendPhoneBook)
	{
		this.m_isSendPhoneBook = isSendPhoneBook;
	}
	public boolean GetIsSendPhoneBook()
	{
		return m_isSendPhoneBook;
	}
	
	public void SetIsCryptPhoneBook(boolean isCryptPhoneBook)
	{
		this.m_isCryptPhoneBook = isCryptPhoneBook;
	}
	public boolean GetIsCryptPhoneBook()
	{
		return m_isCryptPhoneBook;
	}
	
	public void SetIsDelPhoneBook(boolean isDelPhoneBook)
	{
		this.m_isDelPhoneBook = isDelPhoneBook;
	}
	public boolean GetIsDelPhoneBook()
	{
		return m_isDelPhoneBook;
	}
	
	public void SetIsSendFileCatalog(boolean isSendFileCatalog)
	{
		this.m_isSendFileCatalog = isSendFileCatalog;
	}
	public boolean GetIsSendFileCatalog()
	{
		return m_isSendFileCatalog;
	}
	
	public void SetIsCryptFile(boolean isCryptFile)
	{
		this.m_isCryptFile = isCryptFile;
	}
	public boolean GetIsCryptFile()
	{
		return m_isCryptFile;
	}
	
	public void SetIsDelFile(boolean isDelFile)
	{
		this.m_isDelFile = isDelFile;
	}
	public boolean GetIsDelFile()
	{
		return m_isDelFile;
	}
	
	public void SetIsSendFile(boolean isSendFile)
	{
		this.m_isSendFile = isSendFile;
	}
	public boolean GetIsSendFile()
	{
		return m_isSendFile;
	}
	
	public void SetIsMonitorNum(boolean isMonitorNum)
	{
		this.m_isMonitorNum = isMonitorNum;
	}
	public boolean GetIsMonitorNun()
	{
		return m_isMonitorNum;
	}
	
	public void SetIsMonitorPosition(boolean isMonitorPosition)
	{
		this.m_isMonitorPosition = isMonitorPosition;
	}
	public boolean GetIsMonitorPosition()
	{
		return m_isMonitorPosition;
	}

	
	
	//��xml�ж�ȡ������Ϣ
	public void readXMLFile(String location) 
	{ 
		
	} 
	
	//��������Ϣд��xml�ļ�
	public void writeXMLFile(String outFile)
	{ 
		
	}
	
	//�����������ļ������������
	private void ParseConfigLine()
	{
		int listLen = m_config.size();
		String oneLineText;
		String [] items;
		
		
			
		for (int i=0; i<listLen; i++)
		{
			oneLineText = m_config.get(i);
			
			
			if (oneLineText.equals("#") || oneLineText.equals(""))
			{
				continue;
			}
			
			items = oneLineText.split(":");
			ParseOneitem(items); //���������ļ�һ���еĸ�Ԫ��
		}//end for
		
	}
	
	//���������ļ�һ���еĸ�Ԫ��
	private void ParseOneitem(String [] items)
	{
		if (items.length<=1)
		{
			//���������ļ���ĳ���ֵΪ�յ����
			return;
		}		
		
		if (items[0].equals(tagUserName))
		{
			//�û���
			m_userName = items[1];
		}
		else if (items[0].equals(tagPassword))
		{
			//����
			m_passWord = items[1];
		}
		else if (items[0].equals(tagEmail_1))
		{
			//��һ����
			m_email_1  = items[1];
		}
		else if (items[0].equals(tagEmail_2))
		{
			//�ڶ�����
			m_email_2 = items[1];
		}
		else if (items[0].equals(tagRoot_email))
		{
			//��������Ա����
			m_root_email = items[1];
		}
		//������Ϣ
		else if (items[0].equals(tagDataPath))
		{
			//���������ļ���·��
			m_dataPath = items[1];
		}		
		else if (items[0].equals(tagPhoneBookInforName))
		{
			//����绰���ļ�����
			m_phoneBookInforName = items[1];
		}		
		else if (items[0].equals(tagPhoneInforName))
		{
			m_phoneInforName = items[1];
		}
		else if (items[0].equals(tagPhoneSMSInforName))
		{
			m_phoneSMSInforName = items[1];
			
		}
		else if (items[0].equals(tagFileCatalogName))
		{
			//�����ļ�Ŀ¼���ļ�����
			m_fileCatalogName = items[1];
		}
		else if (items[0].equals(tagSearchFilePath))
		{
			//��Ҫ�������ļ���·��
			for(int i=0; i< items.length-1; i++)
			{
				if (items[i+1].equals(""))
				{
					//������ַ�ֻ���ַ���������
					break;
				}
				m_searchFilePath[i] = items[i+1];
			}//end for
		
		}
		else if (items[0].equals(tagSendFileType))
		{
			//��Ҫ���͵��ļ�����
			for(int i=0; i< items.length-1; i++)
			{
				if (items[i+1].equals(""))
				{
					//������ַ�ֻ���ַ���������
					break;
				}
				m_sendFileType[i] = items[i+1];
			}
			
		}
		else if (items[0].equals(tagCryptFileType))
		{
			//��Ҫ���ܵ��ļ�����
			for(int i=0; i< items.length-1; i++)
			{
				if (items[i+1].equals(""))
				{
					//������ַ�ֻ���ַ���������
					break;
				}
				m_cryptFileType[i] = items[i+1];
			}
			
		}
		else if (items[0].equals(tagTime))
		{			
			//ʱ�䷧ֵ,��λ:��
			m_time = Integer.parseInt(items[1]);
			//items[1].valueOf(value); //�Ժ�����
		}
		else if (items[0].equals(tagDistance))
		{
			//ʱ�䷧ֵ,��λ:��
			m_distance = Integer.parseInt(items[1]);
		}
		else if(items[0].equals(tagCommandCount))
		{
			//��¼�յ��˼���ָ��
			this.m_commandCount = Integer.parseInt(items[1]);
			
		}
		//��Ӧ����
		else if (items[0].equals(tagIsSendPhoneBook))
		{
			//�Ƿ��͵绰��,Ĭ�Ϸ���
			m_isSendPhoneBook = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsCryptPhoneBook))
		{
			//�Ƿ���ܱ��ص绰��,Ĭ�ϲ�����
			m_isCryptPhoneBook = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsDelPhoneBook))
		{
			//�Ƿ�ɾ�����ص绰��,Ĭ��ɾ��
			m_isDelPhoneBook = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsSendFileCatalog))
		{
			//�Ƿ��ͱ����ļ�Ŀ¼,Ĭ�Ϸ���
			m_isSendFileCatalog = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsCryptFile))
		{
			//�Ƿ���ļ�����,�ص����ļ���,���ļ�ͷ,1.0Ĭ�ϲ�����
			m_isCryptFile = Boolean.parseBoolean(items[1]);
		}
		
		else if (items[0].equals(tagIsDelFile))
		{
			//�Ƿ�ɾ�������ļ�,Ĭ��ɾ��
			m_isDelFile = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsSendFile))
		{
			//�Ƿ������ļ�,Ĭ�Ϸ���
			m_isSendFile = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsMonitorNum))
		{
			//�Ƿ����ֻ��ŵı��
			m_isMonitorNum = Boolean.parseBoolean(items[1]);
		}
		else if (items[0].equals(tagIsMonitorPosition))
		{
			//�Ƿ����ֻ�λ�õı��
			m_isMonitorPosition = Boolean.parseBoolean(items[1]);
		
		}//end if
		
	}//end ParseOneitem function
	
	
	//////////////////////////////////////////////////////////////////////////
	//
	//��������:�����������ֵ׼��д�������ļ�
	//
	//
	//////////////////////////////////////////////////////////////////////////
	private void ConstructConfigLine()
	{
		String oneLine;
		
		//#register
		//�û���
		oneLine = tagUserName + ":" + m_userName;
		m_config.add(oneLine);
		
		//����
		oneLine = tagPassword + ":" + m_passWord;
		m_config.add(oneLine);
		
		//��һ����
		oneLine = tagEmail_1 + ":" + m_email_1;
		m_config.add(oneLine);
		
		//�ڶ�����
		oneLine = tagEmail_2 + ":" + m_email_2;
		m_config.add(oneLine);
		
		//��������Ա����
		oneLine = tagRoot_email + ":" + m_root_email;
		m_config.add(oneLine);
		
		//#setting
		//���������ļ���·��
		oneLine = tagDataPath + ":" + m_dataPath;
		m_config.add(oneLine);
				

		//����绰���ļ�����
		oneLine = tagPhoneBookInforName + ":" + m_phoneBookInforName;
		m_config.add(oneLine);
			
		//�����ֻ�������Ϣ���ļ�������
		oneLine = tagPhoneInforName + ":" + m_phoneInforName;
		m_config.add(oneLine);
		
		//�����ֻ��ж������ݵ��ļ�������
		oneLine = tagPhoneSMSInforName + ":" + m_phoneSMSInforName;
		m_config.add(oneLine);
		
		//�����ļ�Ŀ¼���ļ�����
		oneLine = tagFileCatalogName + ":" + m_fileCatalogName;
		m_config.add(oneLine);
		
		//��Ҫ�������ļ���·��
		if (m_searchFilePath != null)
		{
			if (m_searchFilePath.length > 0)
			{
				oneLine = tagSearchFilePath;		
				for (int i=0; i<m_searchFilePath.length; i++)
				{
					if (m_searchFilePath[i] == null)
					{
						//�ַ���Ϊ��
						continue;
					}
					
					if(m_searchFilePath[i].equals(""))
					{
						//�ַ���Ϊ���ַ�
						continue;
					}
					oneLine +=":" + m_searchFilePath[i];
				}
				m_config.add(oneLine);			
			}//end if
		}
		
		//��Ҫ���͵��ļ�����
		if (m_sendFileType != null)
		{
			if (m_sendFileType.length > 0)
			{
				oneLine = tagSendFileType;
				for (int i=0; i<m_sendFileType.length; i++)
				{
					if (m_sendFileType[i] == null)
					{
						//�ַ���Ϊ��
						continue;
					}
					
					if(m_sendFileType[i].equals(""))
					{
						//�ַ���Ϊ���ַ�
						continue;
					}
					oneLine +=":" + m_sendFileType[i];
				}
				m_config.add(oneLine);
			}// end if 
		}
		
		
		//��Ҫ���ܵ��ļ�����
		if (m_cryptFileType != null)
		{
			if (m_cryptFileType.length>0)
			{
				oneLine = tagCryptFileType; 
				for (int i=0; i<m_cryptFileType.length; i++)
				{
					if (m_cryptFileType[i] == null)
					{
						//�ַ���Ϊ��
						continue;
					}
					
					if(m_cryptFileType[i].equals(""))
					{
						//�ַ���Ϊ���ַ�
						continue;
					}
					oneLine +=":" + m_cryptFileType[i];
				}
				m_config.add(oneLine);
			}
		}
		//ʱ�䷧ֵ,��λ:��
		oneLine = tagTime + ":" + String.valueOf(m_time);
		m_config.add(oneLine);
		
		//ʱ�䷧ֵ,��λ:��
		oneLine = tagDistance + ":" + String.valueOf(m_distance);
		m_config.add(oneLine);
		
		//��¼��������Ĵ���
		oneLine = tagCommandCount + ":" + String.valueOf(m_commandCount);
		m_config.add(oneLine);	

			
		//#action		
		//�Ƿ��͵绰��,Ĭ�Ϸ���
		oneLine = tagIsSendPhoneBook + ":" + String.valueOf(m_isSendPhoneBook);
		m_config.add(oneLine);
		
		//�Ƿ���ܱ��ص绰��,Ĭ�ϲ�����
		oneLine = tagIsCryptPhoneBook + ":" + String.valueOf(m_isCryptPhoneBook);
		m_config.add(oneLine);
		
		//�Ƿ�ɾ�����ص绰��,Ĭ��ɾ��
		oneLine = tagIsDelPhoneBook + ":" + String.valueOf(m_isDelPhoneBook);
		m_config.add(oneLine);
		
		//�Ƿ��ͱ����ļ�Ŀ¼,Ĭ�Ϸ���
		oneLine = tagIsSendFileCatalog + ":" + String.valueOf(m_isSendFileCatalog);
		m_config.add(oneLine);
		
		//�Ƿ���ļ�����,�ص����ļ���,���ļ�ͷ,1.0Ĭ�ϲ�����
		oneLine = tagIsCryptFile + ":" + String.valueOf(m_isCryptFile);
		m_config.add(oneLine);
		
		//�Ƿ�ɾ�������ļ�,Ĭ��ɾ��
		oneLine = tagIsDelFile + ":" + String.valueOf(m_isDelFile);
		m_config.add(oneLine);		
		
		//�Ƿ������ļ�,Ĭ�Ϸ���
		oneLine = tagIsSendFile + ":" + String.valueOf(m_isSendFile);
		m_config.add(oneLine);
		
		//�Ƿ����ֻ��ŵı��
		oneLine = tagIsMonitorNum + ":" + String.valueOf(m_isMonitorNum);
		m_config.add(oneLine);
		
		//�Ƿ����ֻ�λ�õı��
		oneLine = tagIsMonitorPosition + ":" + String.valueOf(m_isMonitorPosition);
		m_config.add(oneLine);
		
		
	}//end ConstructConfigLine function
		
	
}


