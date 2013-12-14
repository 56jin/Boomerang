package Boomerang_android15.com;

///////////////////////////////////////////////////////////////////////
//
//ģ�鹦��:���ն���ģ��.
//
//
//////////////////////////////////////////////////////////////////////


import android.content.BroadcastReceiver;
import android.content.Context; 
import android.content.Intent; 
import android.os.Bundle; 
import android.widget.Toast;
import android.util.Log;

/*��������telephoney.gsm.SmsMessage����ȡ����*/
import android.telephony.gsm.SmsMessage; 
/*��������Toast������֪�û��յ�����*/
//import android.widget.Toast; 
import android.util.Log;

/* �Զ���̳���BroadcastReceiver��,����ϵͳ����㲥����Ϣ */
public class FqlSMSReceiver extends BroadcastReceiver 
{ 
  /*������̬�ַ���,��ʹ��android.provider.Telephony.SMS_RECEIVED
  ��ΪActionΪ���ŵ�����*/
	private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED"; 


  
	public void onReceive(Context context, Intent intent) 
	{ 
		
		System.setProperty("file.encoding", "GBK"); //���ñ��뷽ʽ
		
		/* �жϴ���Intent�Ƿ�Ϊ����*/
		if (intent.getAction().equals(mACTION)) 
		{ 
			/*����һ�ַ������ϱ���sb*/
			StringBuilder sb = new StringBuilder(); 
			/*������Intent����������*/
			Bundle bundle = intent.getExtras(); 
			/*�ж�Intent��������*/
			if (bundle != null) 
			{ 
				/* pdusΪ android���ö��Ų��� identifier
				 * ͨ��bundle.get("")����һ����pdus�Ķ���*/
				Object[] myOBJpdus = (Object[]) bundle.get("pdus"); 
				
				
				/*�������Ŷ���array,�������յ��Ķ��󳤶�������array�Ĵ�С*/
				SmsMessage[] messages = new SmsMessage[myOBJpdus.length];  
        
				//����messages���������� �յ����ŵ�����,�����Ƕ�ÿ��
				for (int i = 0; i<myOBJpdus.length; i++)
				{  
					messages[i] = SmsMessage.createFromPdu((byte[]) myOBJpdus[i]);
				}// for (int i = 0; i<myOBJpdus.length; i++)
          
				
				/* �������Ķ��źϲ��Զ�����Ϣ��StringBuilder���� */  
				for (SmsMessage currentMessage : messages) 
				{  
					/*
					sb.append("���յ�����:\n");  						
					sb.append(currentMessage.getDisplayOriginatingAddress());	// ��Ѷ�ߵĵ绰����  		
					
					sb.append("\n------�����Ķ���------\n");  				
					sb.append(currentMessage.getDisplayMessageBody());   // ȡ�ô�����Ϣ��BODY  
					
					*/
					ParseSmsMessage (currentMessage, context); //��Ա����,����ÿһ���յ��Ķ�Ϣ��ʶ����Ч����Ϣ
					
				}//end   for (SmsMessage currentMessage : messages) 
        
			}  //end if (bundle != null)  
    

       

      
      
		}//end if (intent.getAction().equals(mACTION)) 
    
	}//end onReceive(Context context, Intent intent) 
  
	/////////////////////////////////////////////////////////////////////////
	//
	//�������ܣ�����ÿһ���յ��Ķ�Ϣ��ʶ����Ч����Ϣ
	//
	//
	//
	/////////////////////////////////////////////////////////////////////////////
	private void ParseSmsMessage (SmsMessage messages , Context context)
	{
		boolean isCorrectSMS = false;  //��ʾ�Ƿ�����Ч���ţ�Ĭ������Ч��Ϣ��
		boolean isRootCommand = true;  //��ʾ�Ƿ��Ǹ�ָ�Ĭ���Ǹ�ָ��
		//java.lang.String
		String messageBody=  messages.getDisplayMessageBody();
		String phoneNum = messages.getDisplayOriginatingAddress();
		String standardMessage = PreTreatSMSString(messageBody);  //�Զ��Ž���Ԥ����ĳ�Ա����

		//���ж��Ƿ��йؼ���"FQL",�����ֵ����ͨ�������ļ����á�
		int index = standardMessage.indexOf("fql");
		if (-1 == index)
		{
			//����Ч����Ϣ
			return;
			
		}
		
		String ruleMessage = standardMessage.substring(index); //ȡ���ַ���
		
		
		//��������ָ������,LaunchService����
		Intent itent = new Intent(Intent.ACTION_RUN);
		itent.setClass(context, LaunchService.class); 
		Bundle bundle = new Bundle();
		bundle.putString("MESSAGE", ruleMessage);
		bundle.putString("PHONE_NUM", phoneNum);
		itent.putExtras(bundle);
			
		//*��������һ��ȫ�µ�task������
		itent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		Log.i("ParseSmsMessage", "begin");
		context.startService(itent);   //����LaunchService����
		
		
	}
	
	////////////////////////////////////////////////////////////////////
	//
	//�������ܣ��Զ�Ϣ����Ϣ����Ԥ����
	//1��ת��Ϊ��д�ַ���
	//2�����˻س���
	//3�����˻��з�
	////////////////////////////////////////////////////////////////////
  	private String PreTreatSMSString(String preString)
  	{
  		String standardMessage_1 = preString.toLowerCase();
  		String standardMessage_2 = standardMessage_1.replace('\n', ' ');
  		String standardMessage_3 = standardMessage_2.replace('\r', ' ');
  		
  		return standardMessage_3;
  		
  	}
  
  
  
  
  
  
}

