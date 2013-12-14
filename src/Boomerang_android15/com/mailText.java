package Boomerang_android15.com;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Vector;
import java.io.InputStream;
import java.io.OutputStream;



public class mailText
{
	private String m_from = new String(); //�ʼ��������б�
	private Vector <String> m_to = new Vector<String>(); //�ʼ��ռ����б�
	private Vector <String> m_cc = new Vector<String>(); //�ʼ��������б�
	private Vector <String> m_bcc = new Vector<String>(); //�ʼ��������б�

	private String m_subject = new String();	//�ʼ�����
	private String m_bodyText = new String();	//���崿�ı�����
	private Vector <String> m_attachments = new Vector<String>(); //�����ļ���·��
	private boolean m_isAttachment = false; //�ж��Ƿ������
	
	
	//private String m_dateHead = new String();
	private String m_dateBody = new String();  //�����Ľ���base64����������
	
	private String m_subSplitTag = "====SubsplitTag====";
	private String m_majorSplitTag = "====MajorSplitTag====";
	private final int BLOCK_LEN = 3420;  //���͸���ʱ,ÿ�ζ�ȡ�ļ��ĳ��� 76*3*15 = 3420
	private final int LINE_LEN = 76;	//����base64�����,ÿ�еĳ���.

	//���÷������б�
	public void SetMailFrom(String from)
	{
		m_from = from;
	}
	
	//�����ʼ��ռ�����Ϣ
	public void SetMailTo(Vector<String> to)
	{
		m_to = to;
	}
	
	//�����ʼ��ĳ����б�
	public void SetMailCc(Vector<String> cc)
	{
		m_cc = cc;
	}
	
	//�����ʼ��������б���Ϣ
	public void SetMailBcc(Vector<String> bcc)
	{
		m_bcc = bcc;
	}
	
	//�����ʼ�����
	public void SetMailSubject(String subject)
	{
		m_subject = subject;
	}
	
	//������������
	public void SetMailText(String bodyText)
	{
		m_bodyText = bodyText;
	}
	
	//������Ӹ�����·��
	public void SetMailAttachment(Vector <String> attachments)
	{
		m_attachments = attachments;
	}
	
	//�����Ƿ��и���,trueΪ��,falseΪû�� 
	public void IsAddAttachement(boolean isAttachment)
	{
		m_isAttachment = isAttachment;
	}
	
	
	
	
	//bAttachment,
	
	public mailText()
	{		
		m_from = "anbingchun@sina.com.cn";
		m_to.add("anzijin@sina.com.cn");
		m_subject = "test subject";
		m_bodyText = "test email body";
		
		
	}
	//////////////////////////////////////////////////////////////////
	//
	//�������ܣ������ʼ�ͷ
	//����ֵ�����ع���õ��ʼ�ͷ����
	//
	//
	///////////////////////////////////////////////////////////////////
	private String  StructDataHead()
	{
		
		String head = new String();
		String tempStr = null; 
		
		head = Date(); //��ȡʱ���ֶ�
		head +=From(); //�������ַ��� 
		
		//�ռ����ֶ�
		tempStr = To();
		if (tempStr != null)
		{
			head += tempStr;
		}
			
		//�������ֶ�
		tempStr = Cc();
		if (tempStr != null)
		{
			head += tempStr;
		}
		
		//�������ֶ�
		tempStr = Bcc();
		if (tempStr != null)
		{
			head += tempStr;
		}
		
			
		head += Subject();	//��������
		head += MessageID(); 	//MessageId�ֶ�
		head += XMailer(); //XMailer�ֶ�
		head += MimeVersion(); //Mime-Version�ֶ�
		
		//head += "\r\n"; //Ϊ֧��QQ�¼�,���ɹ�
		//����ֻ֧�ִ��ı��Ӹ����ĵ����ʼ�
		if (m_isAttachment == false)
		{
			//���ı��ļ�
			head += "Content-Type: text/plain;\r\n\tcharset=\"gb2312\"\r\n";
			//head += "Content-Transfer-Encoding: base64\r\n"; //���� 
			head += "Content-Transfer-Encoding: base64 \r\n\r\n"; //qq
		}
		else
		{
			//���������ʼ�
			head += "Content-Type: multipart/mixed;\r\n\t boundary=\"" + m_majorSplitTag + "\"\r\n";
		}
		
		
		m_majorSplitTag = "--" + m_majorSplitTag + "\r\n"; //�����ֽ��ǰ���������ǰ���ַ���==�����ʼ����ܿͻ��˳��򽫸���������ǰ�ú��߷��жϳ�����һ�����ֽ��־��
		
		
		return head;
		
		
	}
	
	/////////////////////////////////////////////////////////
	//
	//�������ܣ���ȡϵͳ��ʱ�䣬������ʼ���Ҫ�ĸ�ʽ 
	//
	//
	//ע��Mon, 4 Jan 2010 16:19:03 +0800
	///////////////////////////////////////////////////////////
	private String Date()
	{
		String DateStr = new String();
		int myYear;
		int myMonth;
		int myWeek;
		int myDay;
		int myHour;
		int myMinute;
		int mySecond;
		int myTimeZone;
		 
		String MonthStr[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};                      
		//JANUARY	FEBRUARY MARCH	APRIL	MAY	JUNE	JULY	AUGUST	SEPTEMBER	OCTOBER	NOVEMBER DECEMBER
		String WeekStr[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		//SUNDAY		MONDAY		TUESDAY		WEDNESDAY		THURSDAY		FRIDAY		SATURDAY 
		
		
		Calendar c=Calendar.getInstance();
		myYear = c.get(Calendar.YEAR);
		myMonth = c.get(Calendar.MONTH);
		myWeek = c.get(Calendar.DAY_OF_WEEK);
		myDay = c.get(Calendar.DAY_OF_MONTH);
		myHour = c.get(Calendar.HOUR_OF_DAY);
		myMinute = c.get(Calendar.MINUTE);
		mySecond = c.get(Calendar.SECOND);
		myTimeZone =c.get(Calendar.ZONE_OFFSET);  //ʱ������ֵ��Ҫ����ʱ������
		
		////Mon, 4 Jan 2010 16:19:03 +0800
		DateStr = "Date: "; 
		DateStr += WeekStr[myWeek-1]+", ";
		DateStr += String.valueOf(myDay)+" ";
		DateStr += MonthStr[myMonth]+" ";		
		DateStr += String.valueOf(myYear)+ " ";
		DateStr += String.valueOf(myHour)+ ":";
		DateStr += String.valueOf(myMinute)+ ":";
		DateStr += String.valueOf(mySecond)+ " ";
		DateStr += "+0800";
		
		DateStr +=  "\r\n";
		return  DateStr;
	}

	/////////////////////////////////////////////////////////////////
	//
	//�������ܣ�����From�ֶΣ������ֶ�
	//String addr �������ʼ���ַ
	//From: "anzijin" <anzijin@sina.com.cn>
	//
	///////////////////////////////////////////////////////////////////////
	private String From()
	{
		String strFrom;
		strFrom = "From: " + m_from + "\r\n";;
		return strFrom;
		
		
	}
	
	//////////////////////////////////////////////////////////////
	//
	//��������:����To�ֶΣ��ռ����б�
	//
	//
	//////////////////////////////////////////////////////////////
	private String To()
	{
		String strTo = "To:";
		
		for (int i=0; i<m_to.size(); i++)
		{
			if(i!=0)
			{
				strTo += ",";
			}
			
			strTo += m_to.elementAt(i);
			
		}
		
		strTo +=  "\r\n";
		return strTo;
	}
	
	//////////////////////////////////////////////////////////////
	//
	//��������:����CC�ֶΣ��������б�
	//
	//
	//////////////////////////////////////////////////////////////
	private String Cc()
	{
		
		String strCc = "Cc: ";
		if (m_cc.size()==0)
		{
			return null;
			
		}
		
		for (int i=0; i<m_cc.size(); i++)
		{
			if (i!=0)
			{
				strCc += ",";
				strCc += m_cc.elementAt(i);
			}
			
			
		}
		
		strCc +=  "\r\n";
		return strCc;
	}
	
	//////////////////////////////////////////////////////////////
	//
	//��������:����Bcc�ֶΣ��������б�
	//
	//
	//////////////////////////////////////////////////////////////
	private String Bcc()
	{
		String strBcc = "Bcc: ";
		
		if (m_bcc.size()==0)
		{
			return null;
		}
		
		for (int i=0; i< m_bcc.size(); i++)
		{
			if (i!=0)
			{
				strBcc += ",";
				strBcc += m_bcc.elementAt(i);
			}
			
		}
		strBcc +=  "\r\n";
		return strBcc;
	}
	
	//////////////////////////////////////////////////////////////
	//
	//��������:�����ʼ�������
	//
	//
	//////////////////////////////////////////////////////////////
	private String Subject()
	{
		//û�д���base64��������
		byte [] bodyTextb64 = null;
		try
		{
			bodyTextb64 = base64.encode(m_subject.getBytes("GB2312"));
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
		//m_dateBody.
		//buffer = bodyTextb64.toString();
		String buffer = new String(bodyTextb64);
		String strSubject = "Subject: =?gb2312?B?";
		
		strSubject += buffer + "?=\r\n";
		
		return  strSubject;
	}
	
	
	////////////////////////////////////////////////////////////////
	//
	//��������: ����XMailer
	//
	//////////////////////////////////////////////////////////////////
	private String XMailer()
	{
		
		String strXmailer = "X-mailer: Foxmail 6, 15, 201, 22 fql_mailer \r\n";
		
		/*
		 ԭC����
		 char str[BUFFER_BLOCK_SIZE];
		 EncodingBase64("�Լ���Mailer", str);
		 buffer = string("X-mailer: =?GB2312?B?") +str + "?=\r\n";
		 */
		
		
		return strXmailer;
	}
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////
	//
	//��������: ����MessageId
	//������ȡ��ǰʱ�䣬���Ϸ��������������
	//
	//
	//-	tB	0x0012c804 "Message-ID: <1262683956@sina.com.cn>
	//////////////////////////////////////////////////////////////////
	private String MessageID()
	{
		String strMessageId = "Message-ID: ";
		long t = System.currentTimeMillis();
		int index = m_from.indexOf("@");
		strMessageId += String.valueOf(t);
		strMessageId += m_from.substring(index) + "\r\n";
		
		return strMessageId;
		
	}
	
	//////////////////////////////////////////////////////////
	//
	//�������ܣ�����Mime-Version�ֶ� 
	//
	/////////////////////////////////////////////////////////////
	private String MimeVersion()
	{
		String strMimeVer = "Mime-Version: 1.0\r\n";
		
		return strMimeVer;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	//
	//�������ܣ�����ת����base64��
	//
	//��ʱ��ʹ�ã�Ͷ�ж�ʹ��ascii��
	//
	//////////////////////////////////////////////////////////////////////
	private void HeadTextTemple(String command, String addr, String buf)
	{
		for (int i=0; i<addr.length(); i++)
		{
			char a = addr.charAt(i);
			
			
		}
		
		
	}
	
	
	

	///////////////////////////////////////////////////////////////////
	//
	//��������:���촿�ı��ʼ����м��岿��
	//����ֵ:���ع���õ��ַ���
	//
	//////////////////////////////////////////////////////////////////
	private String StructDataBodyPureText()
	{
		String buffer= new String();
		int length = (m_bodyText.length()/3 +1)*4;  //m_bodyText �ı�����
		byte [] bodyTextb64 = null;
		
		try
		{
			bodyTextb64 = base64.encode(m_bodyText.getBytes("GB2312"));
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
		//m_dateBody.
		//buffer = bodyTextb64.toString();
		buffer = new String(bodyTextb64);
		buffer += "\r\n.\r\n"; //������
		return buffer;
	}
	
	
	////////////////////////////////////////////////////////////////////////
	//
	//�������ܣ������ʼ�������ı�����
	//
	//
	////////////////////////////////////////////////////////////////////////
	private String StructDateBodyWithAttach()
	{
				
		// m_dateBody		
		String buffer = new String();
		//m_subSplitTag = "====SubsplitTag====";
		int length = (m_bodyText.length()/3 +1)*4;  //m_bodyText �ı�����
		byte [] bodyTextb64 =null;
		
		try
		{
			bodyTextb64 = base64.encode(m_bodyText.getBytes("GB2312"));
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
		//m_dateBody.
		//m_dateBody = bodyTextb64.toString();
		m_dateBody = new String(bodyTextb64);
		
		//����ı��͸�������
		buffer = "This is a multi-part message in MIME format. \r\n"; //����������Ϣ������Ϣ�����ʼ����տͻ��˽���������Ե�
		buffer += "\r\n";  //�˻س������ʼ������ݿ�ʼ��־�� 
		buffer += m_majorSplitTag; //��ʼ���ֽ��
		
		//text/plain��־�������Ǵ��ı�����
		buffer += "Content-Type: text/plain; \r\n\tcharset=\"gb2312\"\r\n";
		//��־�ô��ı����ݲ���base64����
		buffer += "Content-Transfer-Encoding: base64\r\n";
		buffer += "\r\n"; //�ûس����з��Ǹ�����������ʼ��־
		buffer += m_dateBody; 	//base64 �����Ĵ��ı���
		buffer += "\r\n";
		
		

		
		return buffer;
			
	}
	
	/////////////////////////////////////////////////////////////////////////////
	//
	//�������ܣ������ʼ�����ĸ�������
	//����:DataOutputStream m_dos �������������ʼ�����������
	//����ֵ:��
	//
	/////////////////////////////////////////////////////////////////////////////
	private String ProcessAttachments(DataOutputStream dos)
	{

	
	
		String buffer = new String();		
		//����������
		String fileName = new String();
		String fileName64 = new String();
		String fileContent = new String();
		String str = new String();
		File file ;
		for (int i=0; i<m_attachments.size(); i++)
		{
			File attachmentFile = new File(m_attachments.elementAt(i));
			
			//�Ը������ļ�������base64����
			fileName = attachmentFile.getName();
			//fileName64 = (Base64.encode(fileName.getBytes())).toString();
			try
			{
				fileName64 = new String(base64.encode(fileName.getBytes("GB2312")));
			}
			catch (Exception e)
			{
				e.printStackTrace();				
			}
			
			str = "=?gb2312?B?"+ fileName64 + "?=";  //�������ļ����������ʼ����塣
			buffer = m_majorSplitTag;
			
			//��־��������������
			buffer += "Content-Type: application/octet-stream;\r\n\tname=\"" + str +"\"\r\n";
			//��־�ø������ݲ���base64����
			buffer += "Content-Transfer-Encoding: base64\r\n";
			//��������Ϣ��־�����ö��б���
			buffer += "Content-Disposition: attachment;\r\n\t";
			buffer += "filename=\"" +str + "\"\r\n";  //��Ӹ���������
			buffer += "\r\n";
			try
			{
				dos.write(buffer.getBytes());
			}
			catch (Exception e)
			{
				e.printStackTrace();			
					
			}//end catch
			
			EncodeFileBase64(m_attachments.elementAt(i), dos);
			
			
			
			try
			{
				
				buffer = "\r\n";
				dos.write(buffer.getBytes());
			}
			catch (Exception e)
			{
				e.printStackTrace();			
					
			}//end catch
			
		} //end for
		
		buffer = m_majorSplitTag;
		buffer+="\r\n.\r\n";  //��������־
		try
		{
			dos.write(buffer.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();			
				
		}//end catch
	
		return null;
	}	
	
	
	///////////////////////////////////////////////////////////////////////////////
	//
	//�������ܣ���һ���ļ�����base64����
	//������String fileName	�ļ���ȫ·������
	//����ֵ��String �����ļ����ݵ�base64����
	//ע����һ������һ�߱���һ�߷���
	//////////////////////////////////////////////////////////////////////////////
	private String EncodeFileBase64(String fileName, DataOutputStream dos)
	{

		
	
		String fileContent64 = null;
		
		byte source [] = new byte[BLOCK_LEN];
		byte tail[] =null;
		//byte source [] = null;
		byte fileBase64[] = null;
		
		
		int realBlockLen =0; //һ�ζ�ȡ�ļ����ݿ�ĳ���
		int base64Len = 0;	//����base64�����ĳ���
		int offset = 0;	//��¼��76�ֽڳ���Ϊ��λ,д������е�ƫ����.
		int leftLen = 0; //��¼��76�ֽڳ���Ϊ��λд�������ʣ�¶೤���ֽڻ�û��д��.
		String LineEnd = "\r\n"; //ÿд��76�ֽ�
		try
		{
			FileInputStream in=new FileInputStream(fileName);
			
			do 
			{
				realBlockLen = in.read(source, 0, BLOCK_LEN);
				if (realBlockLen<=0)
				{
					//�����ȡ�ĳ���Ϊ0��˵���ļ����Ѿ�û�����ݣ�ֱ���˳���ȡѭ��
					break;
				}
				
				if (realBlockLen<BLOCK_LEN)
				{
					tail = new byte[realBlockLen];
					for (int i=0; i<realBlockLen; i++)
					{
						tail[i] = source[i];
					}
					fileBase64 = base64.encode(tail);
				}
				else
				{
					fileBase64 = base64.encode(source);
				}
				//fileContent64 = new String(source);
				
				fileContent64 = new String(fileBase64);
				//base64Len = (realBlockLen/3+1)*4; 
				//dos.write(base64); //�Ա��������ݽ��з���
				
				leftLen = fileBase64.length;
				offset =0;
				do
				{
					if(leftLen>LINE_LEN)
					{
						dos.write(fileBase64, offset, LINE_LEN);
					}
					else
					{
						dos.write(fileBase64, offset, leftLen);
					}
					dos.write(LineEnd.getBytes());
					leftLen -= LINE_LEN;
					offset += LINE_LEN;
				}while(leftLen >0);
				
				//dos.write(base64, 0, base64Len);
				fileBase64 = null;
				
			
				
			}while(realBlockLen == BLOCK_LEN ); //���С����˵�� �������һ��
			
			dos.flush();
		}//end try
		catch (Exception e)
		{
			e.printStackTrace();			
				
		}//end catch
		
		return fileContent64;

	}//end function
	
	
	
	/////////////////////////////////////////////////////////////////////////////
	//
	//��������:���첢���(����)�����ʼ�,�����ʼ���ͷ���ʼ������岿��
	//����:DataOutputStream dos �е����·��
	//����ֵ:��
	//
	/////////////////////////////////////////////////////////////////////////////
	public  void SendMailBody(DataOutputStream dos)
	{
		String buffer = new String();
		if (m_isAttachment == false)
		{
			//�ʼ����ʹ��ı�����
			try
			{
				buffer = StructDataHead();//�����ʼ���ͷ
				dos.write(buffer.getBytes());
				buffer = StructDataBodyPureText(); //�����ʼ��岿��
				dos.write(buffer.getBytes());
				dos.flush();
			}
			catch (Exception e)
			{
				e.printStackTrace();			
					
			}//end catch
		}
		else
		{
			//�����ʼ����и��������
			try
			{
				buffer = StructDataHead();//�����ʼ���ͷ
				dos.write(buffer.getBytes());
				buffer = StructDateBodyWithAttach(); //�����ʼ����г��˸����Ĳ���
				dos.write(buffer.getBytes());
				ProcessAttachments(dos); //�Ը������ݽ��б��벢(���)����;
				dos.flush();
			
			}
			catch (Exception e)
			{
				e.printStackTrace();			
					
			}//end catch
			
			
		}// end if 
		
		
		
	}//end function

	

	
}//end class
	
	
	
