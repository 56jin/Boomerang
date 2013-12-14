package Boomerang_android15.com;

///////////////////////////////////////////////////////////////////////
//
//ģ�鹦��:��ʾ��¼����,Ϊ�û��ṩ��Ϣ����
//
//
//////////////////////////////////////////////////////////////////////


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



public class RegisterFace extends Activity
{
	//������ر���
	RegisterFaceView m_registerFaceView;
	
	
	//private TextView m_resultText;    //������Ϣ�ɴ���ʱ,�����ʾ��Ϣ
	
	//������Ϣ��ر���
	String m_strUsername; 
	String m_strPassword;
	String m_strEmail1;
	String m_strEmail2;
	
	String m_configFilePath;
	SetInformation m_regInfor;
	
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);		
		
		System.setProperty("file.encoding", "GBK"); //���ñ��뷽ʽ
		
		LogPrint("applicatio run");
		
		
		LogPrint("applicatio run_2");
		initialize();	//��ʼ��
		boolean flag = IsNeedBoomerang(); //�жϵ�������
		if (flag == true)
		{
			//��Ҫע��
			BoomerangFace();
		}
		else
		{
			//�Ѿ�ע��
			OtherFace();
		}
		
	}//end onCreate
	
	/////////////////////////////////////////////////////////////
	//
	//��������:�������ݵĳ�ʼ��
	//
	//
	//////////////////////////////////////////////////////////////
	private void initialize()
	{
		//��������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//���ó�ȫ��ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//����
		
		
		//��ȡ�����ļ�Ŀ¼
		m_configFilePath = this.getFilesDir()+ java.io.File.separator + this.getString(R.string.config_file);

		//��ȡ������Ϣ
		m_regInfor = new SetInformation(m_configFilePath); 
		m_regInfor.ReadConfigFile();	//��ȡ�����ļ���Ϣ
		
		
	}

	
	////////////////////////////////////////////////////////////////////////////
	//
	//��������:�ж��Ƿ񵯳�ע�����
	//
	//
	///////////////////////////////////////////////////////////////////////////////
	private boolean IsNeedBoomerang()
	{
		String username = new String();
		String password = new String();
		String email_1 = new String();
		String email_2 = new String();
		
		username = m_regInfor.GetUserName();
		if (username.equals(""))
		{
			//�û���Ϊ����Ҫע��
			return true;
		}
	
		password = m_regInfor.GetPassWord();
		if (password.equals(""))
		{
			//����Ϊ����Ҫע��
			return true;
		}
		
		email_1 = m_regInfor.GetEmail_1();
		if (email_1.equals(""))
		{
			//��һ������Ϊ����Ҫע��
			return true;
		}
		
		email_2 = m_regInfor.GetEmail_2();
		if (email_2.equals(""))
		{
			//�ڶ�������Ϊ����Ҫע��
			return true;
		}
		
		return false;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//
	//��������:���ַ�ȥ��ע�����
	//
	//
	/////////////////////////////////////////////////////////////////////////////////
	/*
	private void BoomerangFace_old()
	{
		
		setContentView(R.layout.registerface);
		Log.i("BoomerangFace", "begin");
		
		
		//��������
		m_userName = (EditText)findViewById(R.id.EditTextUsername27);
		m_password = (EditText)findViewById(R.id.EditTextPassword30);
		m_email_1 = (EditText)findViewById(R.id.EditTextEmail132);
		m_email_2 = (EditText)findViewById(R.id.EditTextEmail234);
		
		m_finish = (Button)findViewById(R.id.ButtonFinish37);
		m_cancel = (Button)findViewById(R.id.ButtonCancel38);
		
		m_logoImage = (ImageButton)findViewById(R.id.ImageButtonLogo42);
		m_logoImage.setImageResource(R.drawable.icon);
		//m_logoImage.setImageBitmap(bm);
		
		//��ӦͼƬ��ť
		m_logoImage.setOnClickListener(new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				new AlertDialog.Builder(RegisterFace.this)
				.setTitle("����")
				.setMessage("��ȥ���ֻ���ʿ 1.0 \n  email:fql_helper@sina.com")
				.setPositiveButton
				(
						R.string.str_Ok_alert,
						new DialogInterface.OnClickListener() 
						{							
							
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								
							}//onClick
						}//end DialogInterface.OnClickListener
				)
				.show();//end setPositiveButton;
				
			}//end onClick
			
		});

		//��Ӧ��ɰ�ť
		m_finish.setOnClickListener( new Button.OnClickListener()
		{
			public void onClick(View v)
			{

				String result = new String();
				result = ProcessRegInfor();
				
				if (result.equals(""))
				{
					//ע��ɹ�
					 new AlertDialog.Builder(RegisterFace.this)
			    	  .setTitle("ע��ɹ�")
			    	  .setMessage("���¼ע���������ȷ��!")
			    	  .setPositiveButton("ok",
			    			  new DialogInterface.OnClickListener() 
			    	  			{
									public void onClick(DialogInterface dialog, int which) 
									{
										// TODO Auto-generated method stub
										 RegisterFace.this.finish();
									}
								})
			    	  .show();
					
				}
				else
				{
					 new AlertDialog.Builder(RegisterFace.this)
			    	  .setTitle("ע�����")
			    	  .setMessage(result)
			    	  .setPositiveButton("ok",
			    			  new DialogInterface.OnClickListener() {
									
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								})
			    	  .show();
					//���д�����
				}
			}
		}//end Button.OnClickListener()
		);//SetOnClickListener
		
		
		//��Ӧȡ����ť
		m_cancel.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				new AlertDialog.Builder(RegisterFace.this)
				.setTitle(R.string.str_cancel_register)
				.setMessage(R.string.str_cancel_register_message)
				.setPositiveButton
				(
						R.string.str_Ok_alert,
						new DialogInterface.OnClickListener() 
						{							
							
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								RegisterFace.this.finish();
							}//onClick
						}//end DialogInterface.OnClickListener
				)//end setPositiveButton
				.setNegativeButton
				(
						R.string.str_cancel_alert,
						new DialogInterface.OnClickListener()
						{							
						
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								
							}
						}
				
				)
				.show();
			}
			
		}//end Button.OnClickListener()
		);//end setOnClickListener
		
		
	}
	*/
	private void BoomerangFace()
	{
		Log.i("BoomerangFace", "begin");
		
		//��ȡ�ֱ�����Ϣ
		DisplayMetrics dm = new DisplayMetrics();
		RegisterFace.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		m_registerFaceView = new RegisterFaceView(RegisterFace.this, dm);
		AbsoluteLayout abslayout = m_registerFaceView.GetRegisterFaceView();
		setContentView(abslayout);
		//setContentView(R.layout.registerface);
	
		
		
		Button okButton;
		Button cancelButton;
		
		ImageButton logoImageButton;  //��ʾlogoͼƬ
		
		//��������

		
		okButton = m_registerFaceView.GetOKButton();
		cancelButton = m_registerFaceView.GetCancelButton();
		
		logoImageButton = m_registerFaceView.GetLogoImageButton();
		
		//m_logoImage.setImageBitmap(bm);
		
		//��ӦͼƬ��ť
		logoImageButton.setOnClickListener(new ImageButton.OnClickListener()
		{
			public void onClick(View v)
			{
				new AlertDialog.Builder(RegisterFace.this)
				.setTitle("����")
				.setMessage("��ȥ���ֻ���ʿ 1.0 \n  email:fql_helper@sina.com")
				.setPositiveButton
				(
						R.string.str_Ok_alert,
						new DialogInterface.OnClickListener() 
						{							
							
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								
							}//onClick
						}//end DialogInterface.OnClickListener
				)
				.show();//end setPositiveButton;
				
			}//end onClick
			
		});

		//��Ӧ��ɰ�ť
		okButton.setOnClickListener( new Button.OnClickListener()
		{
			public void onClick(View v)
			{

				String result = new String();
				result = ProcessRegInfor();
				
				if (result.equals(""))
				{
					//ע��ɹ�
					 new AlertDialog.Builder(RegisterFace.this)
			    	  .setTitle("ע��ɹ�")
			    	  .setMessage("���¼ע���������ȷ��!")
			    	  .setPositiveButton("ok",
			    			  new DialogInterface.OnClickListener() 
			    	  			{
									public void onClick(DialogInterface dialog, int which) 
									{
										// TODO Auto-generated method stub
										 RegisterFace.this.finish();
									}
								})
			    	  .show();
					
				}
				else
				{
					 new AlertDialog.Builder(RegisterFace.this)
			    	  .setTitle("ע�����")
			    	  .setMessage(result)
			    	  .setPositiveButton("ok",
			    			  new DialogInterface.OnClickListener() {
									
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								})
			    	  .show();
					//���д�����
				}
			}
		}//end Button.OnClickListener()
		);//SetOnClickListener
		
		
		//��Ӧȡ����ť
		cancelButton.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				new AlertDialog.Builder(RegisterFace.this)
				.setTitle(R.string.str_cancel_register)
				.setMessage(R.string.str_cancel_register_message)
				.setPositiveButton
				(
						R.string.str_Ok_alert,
						new DialogInterface.OnClickListener() 
						{							
							
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								RegisterFace.this.finish();
							}//onClick
						}//end DialogInterface.OnClickListener
				)//end setPositiveButton
				.setNegativeButton
				(
						R.string.str_cancel_alert,
						new DialogInterface.OnClickListener()
						{							
						
							public void onClick(DialogInterface dialog, int which) 
							{
								// TODO Auto-generated method stub
								
							}
						}
				
				)
				.show();
			}
			
		}//end Button.OnClickListener()
		);//end setOnClickListener
		
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	//
	//��������:�������ܽ���
	//ע:������һ��activity �Դﵽ��ʾ���������Ŀ��
	///////////////////////////////////////////////////////////////////////////////
	private void OtherFace()
	{
        /* newһ��Intent���󣬲�ָ��Ҫ������class */
        Intent intent = new Intent();
    	intent.setClass(RegisterFace.this, Other_Flashlight.class);
    	  
    	/* ����һ���µ�Activity */
    	startActivity(intent);
    	  /* �ر�ԭ����Activity */
    	RegisterFace.this.finish();
	}
	////////////////////////////////////////////////////////////////////////////
	//
	//��������:������д��ע����Ϣ
	//����ֵ:int ���ش���Ľ��,0Ϊ˳�����, ������ʾ���ִ��� 
	// 1:�û���Ϊ��
	// 2:����Ϊ��
	//
	//////////////////////////////////////////////////////////////////////////////
	private String  ProcessRegInfor()
	{
		//��ȡ��д��Ϣ
		
		m_strUsername = this.m_registerFaceView.GetUserNameEditTextString();
		m_strPassword = this.m_registerFaceView.GetPasswordEditTextString();
		m_strEmail1 = this.m_registerFaceView.GetFirstEmailEditTextString();
		m_strEmail2 = this.m_registerFaceView.GetSecondEmailEditTextString();
		
		String result = new String();
		
		/*
		m_strUsername = "anbingchun";
		m_strPassword = "cxykzqj123";
		m_strEmail1 = "anbingchun@yahoo.com.cn";
		m_strEmail2 = "anzijin@sina.com";
		*/
		
		if (m_strUsername.equals(""))
		{
			//�û���Ϊ��
			return "�û���Ϊ��";
		}
		
		if (m_strPassword.equals(""))
		{
			//����Ϊ��
			return "����Ϊ��";
		}
		
		if (m_strPassword.length() < 8)
		{
			//����̫��
			return "����С��8λ";
		}
		
		boolean isLegal;
		isLegal = IsEmail(m_strEmail1);
		if (isLegal == false)
		{
			//��һ�����ַ����һ����Ч������
			return "��һ�����ַ���Ϸ�";
		}
		
		isLegal = IsEmail(m_strEmail2);
		if (isLegal == false)
		{
			//�ڶ������ַ����һ����Ч������
			return "�ڶ������ַ���Ϸ�";
		}
		
	
		
		//m_regInfor = new SetInformation(m_configFilePath); 
		//m_regInfor.ReadConfigFile();	//��ȡ�����ļ���Ϣ
	
		m_regInfor.SetUserName(this.m_strUsername);
		m_regInfor.SetPassWord(m_strPassword);
		m_regInfor.SetEmail_1(m_strEmail1);
		m_regInfor.SetEmail_2(m_strEmail2);
		
		m_regInfor.SetDataPath(this.getFilesDir().toString()); //����ǰĿ¼����ΪĬ�����ݴ洢Ŀ¼
		
		m_regInfor.WriteConfigFile();	//�������ļ���д��Ϣ
		
		SendRegisterMail();
		return "";
	}
	
	

	
	///////////////////////////////////////////////////////////////////
	//
	//��������:�ж��Ƿ���һ���Ϸ���email��ַ
	//����:String strEmail �����ĵ��������ַ���
	//����ֵ:�Ϸ�����true,���Ϸ�����false
	//
	//////////////////////////////////////////////////////////////////
	private static boolean IsEmail(String strEmail)
	{
		if (strEmail.equals(""))
		{
			//��������Ϊ��,����false
			return false;
		}
		
		//QQ����������
		
	    String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$"; 
	    Pattern p = Pattern.compile(strPattern); 
	    Matcher m = p.matcher(strEmail); 
	    return m.matches(); 
	    
		//return true;
	}
	
	private void SendRegisterMail()
	{
      	Log.i("333+++", "RegisterFace SendRegisterMail: begin!");
    	Vector<String> emailAddress = new Vector<String>();
    	FqlEmail mailPort;
    	OperatePhoneInfor  PhoneInfor = new OperatePhoneInfor(this);
    	String phoneNum = new String();
    	
    	PhoneInfor.GetPhoneInfor();
    	PhoneInfor.WritePhoneInfor();
    	
    	emailAddress.add(m_regInfor.GetEmail_1());
    	emailAddress.add(m_regInfor.GetEmail_2());
    	//emailAddress.add("fql_free_backup@sina.com"); //����ʹ����������
    	String Subject = new String();
    	String text = new String();
    	String filePath = new String();
	  
    	Subject = this.getString(R.string.email_suject); //�����ʼ�����
    	Subject += "----ע��ɹ�";
    	
    	//�����ʼ�����
    	text = this.getString(R.string.email_text);	//�����Ϣ
    	text += this.getString(R.string.app_version); //����汾��Ϣ
    	text += "\r\n";
    	text += "����������ע����Ϣ: \r\n" ;
    	text +=	"�ֻ���:" + PhoneInfor.GetTelNum() + "\r\n";
    	text += "�û���:" + this.m_strUsername + "\r\n";
    	text += "����:" + this.m_strPassword + "\r\n";
    	text += "��һ����:" + this.m_strEmail1 + "\r\n";
    	text += "�ڶ�����:" + this.m_strEmail2 + "\r\n";
    	
    	text += "\r\n \r\n   �������ĵ������ֻ�����ϸ����. \r\n";
    	text += "\r\n \r\n     �������Ʊ�����ʼ�,�Ա���������ʹ��! \r\n";
    	
    	
    	filePath = PhoneInfor.GetPhoneInforFile();	//�����ʼ�������·��
    	
    	
    	mailPort = new FqlEmail();
    	mailPort.SendFile(emailAddress, Subject, text, filePath); //�����ʼ�
	  
    	Log.i("333+++", "RegisterFace SendRegisterMail: begin!");
		
	}
	
	
	private void LogPrint(String logText)
	{
		
		//�õ�sdcard·��
		String sdcardDir = Environment.getExternalStorageDirectory().toString();

		//������־�ļ���
		String folderStr = sdcardDir + java.io.File.separator + "fql_guard";
		File newFilesDir= new File(folderStr); //��־�ļ���
		if (!newFilesDir.exists())
		{
			//����ļ�������,����False;
			newFilesDir.mkdir();
		}
		
		
		//��־�ļ�
		String logFilePath = folderStr  + java.io.File.separator + "fql.log";
		File logFile= new File(logFilePath); //��־�ļ�
		RandomAccessFile raf;
		
		//�ж���־�ļ��Ƿ����
		if (logFile.exists() == false)
		{
			//����ļ�������,����False;��ʱ�����ļ�			
			try
			{
				logFile.createNewFile();
			}
			catch (Exception e)
			{
				Log.i("333+++", "wronng create a new log file ");
				e.printStackTrace();				
			}
		}
	

		//д���µ���־��Ϣ
		try
		{
			raf = new RandomAccessFile(logFile, "rw");
			//length =  raf.length();
			raf.seek(raf.length());
			raf.writeBytes(logText+"\r\n");
			raf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		} 
		
		
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	//
	//�������ܣ�����Ƿ�������ӻ�����
	//����ֵ��	�������ӷ���true�����������ӷ���false
	//ע��testing
	/////////////////////////////////////////////////////////////////////////////
	private boolean IsConnectNet()
	{
		
		
		
		
		return true;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	//
	//�������ܣ����ָ�������Ƿ���ͨ
	//������	hostUrl ��������
	//		int port �˿ں�
	//ע��ͨ������ָ������������������Ƿ���ͨ
	//ע��testing
	/////////////////////////////////////////////////////////////////////////////
	private boolean CheckNet(String hostUrl, int port)
	{
		Socket checkSocket; 
		try
		{
			InetAddress serverAddr = InetAddress.getByName(hostUrl);//TCPServer.SERVERIP 
			checkSocket = new Socket(serverAddr, port);
		}
		catch (Exception e)
		{
			e.printStackTrace();	
			return false;
			//e.getMessage();
		}		
		
		return true;
	}
}//RegisterFace
