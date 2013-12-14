package Boomerang_android15.com;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;


import android.util.Log;



public class fqlSmtp
{
	private String m_serverHostName = new String();		//ESTMP��������
	private int m_serverPort = 25;			//ESTMP�����������˿�
	private String m_username = new String();		//��¼�û���
	private String m_pass = new String();			//��¼����
	private String m_senderAddr = new String();		//�ʼ������ߵ�ַ
	private Vector <String> m_recptAddress = new Vector<String> (); //�ʼ������ߵ�ַ
	private String m_from = new String(); //�ʼ��������б�
	private Vector <String> m_to = new Vector<String> (); //�ʼ��ռ����б�
	private Vector <String> m_cc = new Vector<String> (); //�ʼ��������б�
	private Vector <String> m_bcc  = new Vector<String> (); //�ʼ��������б�
	
	private String m_subject;	//�ʼ�����
	private String m_bodytext;	//���崿�ı�����
	
	private Vector<String> m_attachments = new Vector<String>();
	private boolean m_isAttachment = false;
	
	
	mailText testMailText = new mailText();

	
	Socket m_socket;     
	InputStream m_is;     
	OutputStream m_os;     
	DataInputStream m_dis;    
	DataOutputStream m_dos;
	
	
	//////////////////////////////////////////////////////////////////
	//
	//��������:���캯��
	//
	//////////////////////////////////////////////////////////////////
	public fqlSmtp()
	{		
		Log.i("333+++", " fqlSmtp :construct function");
		//RegisterSina();
		//RegisterYahoo();
		//RegisterQQ();
		//RegisterGmail();
		
	}
	
	private void RegisterSina()
	{
		m_serverHostName = "smtp.sina.com.cn";		//ESTMP��������
		m_username = "anbingchun";		//��¼�û���
		m_pass = "zwwjsssdxz";			//��¼����
		m_senderAddr = "anbingchun@sina.com.cn";		//�ʼ������ߵ�ַ
		m_from = "anbingchun@sina.com.cn";
		
		m_recptAddress.add("anzijin@sina.com.cn");
		m_recptAddress.add("1321490265@QQ.com");
		m_recptAddress.add("anbingchun8@msn.com");
		m_recptAddress.add("feiqulaimobile@gmail.com");
		m_recptAddress.add("feiqulaimobile@hotmail.com");
		m_recptAddress.add("anbingchun@yahoo.com.cn");
		
		m_to.add("1321490265@QQ.com");
		m_subject = "test";
		m_bodytext = "fei qu lai testing!";  //base64����:ZmVpIHF1IGxhaSB0ZXN0aW5nIQ==
		m_isAttachment =true;
		m_attachments.add("/sdcard/try/email.txt");
		m_attachments.add("/sdcard/try/IMG_2424.jpg");
		
	}
	
	private void RegisterQQ()
	{
		m_serverHostName = "smtp.qq.com";		//ESTMP��������smtp.qq.com
		m_username = "94128203";		//��¼�û���
		m_pass = "dskjzyhdzw";			//��¼����
		m_senderAddr = "94128203@qq.com";		//�ʼ������ߵ�ַ
		m_from = "94128203@qq.com";
		
		
		m_recptAddress.add("anzijin@sina.com.cn");
		m_recptAddress.add("1321490265@QQ.com");
		m_recptAddress.add("anbingchun8@msn.com");
		m_recptAddress.add("feiqulaimobile@gmail.com");
		m_recptAddress.add("feiqulaimobile@hotmail.com");
		m_recptAddress.add("anbingchun@yahoo.com.cn");
		
		
		m_to.add("1321490265@QQ.com");
		m_subject = "test";
		m_bodytext = "fei qu lai testing!";  //base64����:ZmVpIHF1IGxhaSB0ZXN0aW5nIQ==
		m_isAttachment =true;
		m_attachments.add("/sdcard/try/email.txt");
		m_attachments.add("/sdcard/try/IMG_2424.jpg");
		
	}
	
	private void RegisterYahoo()
	{
		m_serverHostName = "smtp.mail.yahoo.com.cn";		//ESTMP��������smtp.qq.com
		m_username = "anbingchun";		//��¼�û���
		m_pass = "zwwjsssdxz";			//��¼����
		m_senderAddr = "anbingchun@yahoo.com.cn";		//�ʼ������ߵ�ַ
		m_from = "anbingchun@yahoo.com.cn";
		
		
		m_recptAddress.add("anzijin@sina.com.cn");
		m_recptAddress.add("1321490265@QQ.com");
		m_recptAddress.add("anbingchun8@msn.com");
		m_recptAddress.add("feiqulaimobile@gmail.com");
		m_recptAddress.add("feiqulaimobile@hotmail.com");
		m_recptAddress.add("anbingchun@yahoo.com.cn");
		
		
		m_to.add("1321490265@QQ.com");
		m_subject = "test";
		m_bodytext = "fei qu lai testing!";  //base64����:ZmVpIHF1IGxhaSB0ZXN0aW5nIQ==
		m_isAttachment =true;
		m_attachments.add("/sdcard/try/email.txt");
		m_attachments.add("/sdcard/try/IMG_2424.jpg");
		
	}
	
	private void RegisterGmail()
	{
		//����ʹ��ssl����Э��
		m_serverHostName = "smtp.gmail.com";		//ESTMP��������smtp.qq.com
		m_username = "feiqulaimobile";		//��¼�û���
		m_pass = "dskjzyhdzw";			//��¼����
		m_senderAddr = "feiqulaimobile@gmail.com";		//�ʼ������ߵ�ַ
		m_from = "feiqulaimobile@gmail.com";
		
		
		m_recptAddress.add("anzijin@sina.com.cn");
		m_recptAddress.add("1321490265@QQ.com");
		m_recptAddress.add("anbingchun8@msn.com");
		m_recptAddress.add("feiqulaimobile@gmail.com");
		m_recptAddress.add("feiqulaimobile@hotmail.com");
		m_recptAddress.add("anbingchun@yahoo.com.cn");
		
		

		m_to.add("1321490265@QQ.com");
		m_subject = "test";
		m_bodytext = "fei qu lai testing!";  //base64����:ZmVpIHF1IGxhaSB0ZXN0aW5nIQ==
		m_isAttachment =true;
		m_attachments.add("/sdcard/try/email.txt");
		m_attachments.add("/sdcard/try/IMG_2424.jpg");
		
		
	}
	
	/////////////////////////////////////////////////////////////
	//
	//��������:����smtp����������Ϣ
	//����:	String serverHost	stmp����������
	//		String serverPort	�������˿�
	//		String username		�û���
	//		String password		�û�����
	//		String senderAddress	����������
	//		String recptAddress		����������
	//
	//////////////////////////////////////////////////////////////
	public void SetSmtpInfor(
			String serverHost,
			int serverPort,
			String username,
			String password,
			String senderAddress,
			String recptAddress
			)
	{
		SetServerHost(serverHost);
		SetServerPort(serverPort);
		SetUserName(username);
		SetPassword(password);
		SetMailSender(senderAddress);
		AddOneMailTo(recptAddress);
		
		
	}
	//�����ʼ�����������
	public void SetServerHost(String serverHostName)
	{
		Log.i("333+++", " fqlSmtp :SetServerHost ");
		m_serverHostName = serverHostName;
	}
	
	//�����ʼ��������˿�
	public void SetServerPort(int port)
	{
		m_serverPort = port;
	}
	
	//�����û�����Ϣ
	public void SetUserName(String username)
	{
		m_username = username;
	}
	
	//����������Ϣ
	public void SetPassword(String password)
	{
		m_pass = password;
	}
	
	////////////////////////////////////////////////
	//
	//��������:�����ʼ���������Ϣ
	//ע:m_senderAddr��m_from��Ϊ��ͬ��ֵ
	//
	/////////////////////////////////////////////////
	public void SetMailSender(String sender)
	{
		m_senderAddr = sender;
		m_from = sender;
	}
	
	/////////////////////////////////////////////////////
	//
	//��������:���һ����ַ�������ʼ��б�
	//����˭���յ��ʼ�
	/////////////////////////////////////////////////////
	public void AddOneRecptAddr(String recptAddr)
	{
		
		m_recptAddress.add(recptAddr);
	}
	
	/////////////////////////////////////////////////////////
	//
	//��������:���һ���ռ��˵�ַ
	//ֻӰ���ʼ����ݵ���ʾ
	///////////////////////////////////////////////////////////
	public void AddOneMailTo(String oneTo)
	{
		
		m_to.add(oneTo);
	}
	
	//���һ�������˵�ַ
	public void AddOneMailCc(String oneCc)
	{
		m_cc.add(oneCc);
	}
	
	//���һ�������˵�ַ
	public void AddOneMailBcc(String oneBcc)
	{
		m_bcc.add(oneBcc);
	}
	
	//�����ʼ�����
	public void SetMailSubject(String subject)
	{
		m_subject =  subject;
	}
	
	//�����ʼ�����
	public void SetMailBodytext(String bodytext)
	{
		m_bodytext = bodytext;
	}

	//��Ӹ����б�
	public void AddMailAttachment(String attachmentFile)
	{
		m_isAttachment = true;
		m_attachments.add(attachmentFile);
	}
	
	
	
	

	/////////////////////////////////////////////////////////////////////
	//
	//��������:
	//����:
	//
	////////////////////////////////////////////////////////////////////
	public boolean SendMail(String subject, 
			String bodytext,
			Vector <String> attachmentList
			)
	{
		
		SetMailSubject(subject);
		SetMailBodytext(bodytext);
		
		if (0 == attachmentList.size())
		{
			m_isAttachment = false;
		}
		else
		{
			m_attachments = attachmentList;
		}
		
		
		
		SendMail();
		
		return true;
	}
	
	
	
	//////////////////////////////////////////////////////////////
	//
	//��������:�����ʼ�
	//
	//ע:���������ʼ�������,����mailText�๹���ʼ�,�����ʼ�,�ر�����
	//
	///////////////////////////////////////////////////////////////////
	public boolean SendMail()
	{
		Log.i("333+++", "fqlSmtp  SendMail: begin!");
		
		String sendStr = "";
		String recvStr = "";
		//byte [] b_base64 = new byte[0x100];
		int result =0;
		boolean flag = false;
		//
		flag = SetMailTextInfor();
		if (flag == false)
		{
			return false;
			//Log.i("call SetMailTextInfor false!\n");
		}
		
		try
		{
			InetAddress serverAddr = InetAddress.getByName(m_serverHostName);//TCPServer.SERVERIP 
			m_socket = new Socket(serverAddr, m_serverPort);
			
			
			
			//��ö�Ӧ��socket�����롢�����
			m_is = m_socket.getInputStream();
			m_os = m_socket.getOutputStream();
			
			//����������
			m_dis = new DataInputStream(m_is);
			m_dos = new DataOutputStream(m_os);
			
			//1   �õ�ESMTP��������ӭ�ʣ��ɹ���Ӧ����220
			result = GetResponseCode (220, recvStr);
			if (result !=0)
			{
				return false;
			}
	
			//2   ��ESMTP���������к���EHLO���ɹ���Ӧ����250
			sendStr = "EHLO fql" + m_senderAddr + "\r\n";  //QQ�����
			//sendStr = "EHLO " + m_senderAddr + "\r\n"; //�����Ѿ��ɹ�
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode (250, recvStr);
			if (result !=0)
			{
				return false;
			}
			
			
			//3	��¼ESMTP��������AUTH�����ھ��󲿷�ESMTP��������֧��LOGIN��¼��ʽ������ֱ�Ӳ��øõ�¼��ʽ��
			//��ʵ�ϣ���ȷ�����������ж�EHLO����Ӧ�����Ƿ����AUTH LOGIN�ַ�����
			sendStr = "AUTH LOGIN\r\n";
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode (334, recvStr);
			if (result !=0)
			{
				return false;
			}
			
			
			//4	�����������base64������û������ɹ���Ӧ����334
			//b_base64 = Base64.encode(this.m_senderAddr.getBytes());  //QQ����
			//b_base64 = base64.encode(m_username.getBytes());  //�Ѿ�֧�ֵ�����
			sendStr = new String(base64.encode(m_username.getBytes()));
			//sendStr += " \r\n"; //QQ
			sendStr += "\r\n"; //yahoo
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode (334, recvStr);
			if (result !=0)
			{
				return false;
			}
			
				
			//5	��ESTMP����������base64��������룬�ɹ���Ӧ��Ϊ235
			//b_base64 = base64.encode(m_pass.getBytes()); 
			sendStr = new String(base64.encode(m_pass.getBytes()));
			sendStr += "\r\n";
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode (235, recvStr);
			if (result !=0)
			{
				return false;
			}
			
			//6   ���ͷ����˵�ַ��mail from  .�ɹ���Ӧ��Ϊ 250
			sendStr = "MAIL FROM:" + m_senderAddr + "\r\n";
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode (250, recvStr);
			if (result !=0)
			{
				return false;
			}
			
			//7 �����ռ��˵�ַ RCPT TO ���ɹ���Ӧ��Ϊ250
			for (int i=0; i<m_recptAddress.size(); i++)
			{
				sendStr = "RCPT TO: <" + m_recptAddress.get(i) + ">\r\n";
				m_dos.write(sendStr.getBytes());
				m_dos.flush();
				result = GetResponseCode (250, recvStr);
				if (result !=0)
				{
					return false;
				}
				
			}//end for
			
			
			//8 ����DATA�������ʾ׼�������ʼ����ݣ��ɹ���Ӧ��354.
			sendStr = "DATA\r\n";
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode (354, recvStr); //354 go ahead
			if (result !=0)
			{
				return false;
			}
			
			
			//9�����ż�
			testMailText.SendMailBody(m_dos);
			
			result = GetResponseCode(250, recvStr);
			if (result !=0)
			{
				return false;
			}
			
						
			
			//10  �ص��ż�����������
			
			
			//11�Ƴ��ʼ�������
			sendStr = "QUIT\r\n";
			m_dos.write(sendStr.getBytes());
			m_dos.flush();
			result = GetResponseCode(221, recvStr);
			if (result != 0)
			{
				return false;
			}
			
			return true;
			   
		}
		catch (Exception e)
		{
			e.printStackTrace();			
			//e.getMessage();
		}
		
		return true;
	}
	
	
	////////////////////////////////////////////////////////////////////
	//
	//��������:��ȡ�ʼ��������ķ�����Ϣ
	//����:	int correctCode	��ȷ�ķ��ر���
	//		String retString �����ַ���
	//����ֵ:������óɹ�����0, ���򷵻�1;
	//
	////////////////////////////////////////////////////////////////////
	private int GetResponseCode (int correctCode, String retString)
	{
		int retCode;
		String recvStr;
		int result ;
		int waitCount = 1;
		try
		{
			result = m_is.available();
			while(result==0)
			{
				//Thread.sleep(1000);   //�Ժ�ͨ�������������������������Ӧ,����false
 			    
 			    /*
 			    Thread.sleep(20*waitCount);
 			    result = m_is.available();
 			    waitCount *=2;	//�ȴ�ʱ��ָ������
 			    if(waitCount > 256) //256=c^8 
 			    {
 			    	//��ʱ
 			    	 return -1;
 			    } 
 			     * */
				
 			    result = m_is.available();

			}			
			
			byte[] data = new byte[result];
		    m_is.read(data);
		    recvStr = new String(data);
		     
		    //��������GetResponseCode ����
		    String code = recvStr.substring(0, 3);
		     
		    int i_code = 0;
		    i_code = Integer.parseInt(code);
		     
		    if (i_code == correctCode)
		    {
		    	//220 irxd5-203.sinamail.sina.com.cn ESMTP\r\n
		    	//500 #5.5.1 command not recognized\r\n
		    	//501 #5.5.4 cannot decode AUTH parameter [B@435a24c0\r\n
		    	//501 #5.5.4 cannot decode AUTH parameter [B@435a24a0 \r\n
		    	//������Ϣ��ȷ
		    	return 0;
		    }
		    else
		    {
		    	return 1;
		    }
		}
		catch (Exception e)
		{
			e.printStackTrace();				
		}
		return 0;
	}
	
	private boolean SetMailTextInfor()
	{
		//���÷���������
		if (m_from != null)
		{
			testMailText.SetMailFrom(this.m_from);
		}
		else
		{
			return false;
		}
		
		//�����ռ�������
		if (m_to != null)
		{
			testMailText.SetMailTo(this.m_to);
		}
		else
		{
			return false;
		}
		
		
		//���ó����б�
		if (m_cc != null)
		{
			if ( m_cc.size() != 0)
			{
				testMailText.SetMailCc(this.m_cc);
			}
		}
		
		//���������ʼ��б�
		if (m_bcc != null)
		{
			if (m_bcc.size() != 0)
			{
				testMailText.SetMailBcc(this.m_bcc);
			}
		}
	
		
		testMailText.SetMailSubject(this.m_subject);  //�����ʼ�����
		testMailText.SetMailText(this.m_bodytext);		//�����ʼ�����
		
		
		//���ø�����ص���Ϣ
		if (m_isAttachment == true)
		{
			if (m_attachments != null)
			{
				if (m_attachments.size()!= 0)
				{
					testMailText.IsAddAttachement(true);
					testMailText.SetMailAttachment(this.m_attachments);
				}
				else
				{
					testMailText.IsAddAttachement(false);
				}
			}
			else
			{
				testMailText.IsAddAttachement(false);
			}
			
		}
		else
		{
			testMailText.IsAddAttachement(false);
		}
				
		return true;
		
	}



}
