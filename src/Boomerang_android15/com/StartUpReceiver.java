package Boomerang_android15.com;

////////////////////////////////////////////////////////////////////
//
//��������:����phone����ʱ��Ҫ��ɵĹ���
//ע:û��@@@@���ϲ���
//
////////////////////////////////////////////////////////////////////



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartUpReceiver extends BroadcastReceiver
{
 	
	public void onReceive(Context context, Intent intent)
	{
		
		//���������ȼ��sim���Ƿ񱻸���
		CheckSimCard(context);
	  
	  
		/*
    	// ���յ�Receiverʱ��ָ���򿪴˳���EX06_16.class��
    	Intent mBootIntent = new Intent(context, EX06_16.class);    
    	// ����Intent��ΪFLAG_ACTIVITY_NEW_TASK 
    	mBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
    	//��Intent��startActivity���͸�����ϵͳ 
    	context.startActivity(mBootIntent);
		 */
	}//end function
	
	/////////////////////////////////////////////////////////////////
	//
	//��������:��sim�����м��
	//����:Context context
	//
	////////////////////////////////////////////////////////////////////
	private void CheckSimCard(Context context)
	{
		OperatePhoneInfor  oldPhoneInfor = new OperatePhoneInfor(context);
		OperatePhoneInfor  newPhoneInfor = new OperatePhoneInfor(context);
		
		//��Ҫ�����ļ���ɾ�������
		oldPhoneInfor.ReadPhoneInfor();  //���ļ��ж�ȡ�ϵ���Ϣ
		String oldSimNumber =oldPhoneInfor.GetSimNumber();//���ԭ����sim������
		
		newPhoneInfor.GetPhoneInfor(); //��ϵͳ�л�ȡ����ϵͳ��Ϣ
		String newSimNumber = newPhoneInfor.GetSimNumber();//��õ�ǰ��sim������
		
		if (oldSimNumber.equals(newSimNumber)!= true)
		{
			//��������sim��
			//���µ�sim����Ϣ���浽����
			newPhoneInfor.WritePhoneInfor();
			
			//���������ʼ�
			newPhoneInfor.SendPhoneAlarm();
		}//end if
		
		
		
	}//end function
	
	
}