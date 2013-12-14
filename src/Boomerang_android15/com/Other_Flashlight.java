package Boomerang_android15.com;



/* import���class */
import java.io.File;
import java.util.List;
import java.util.Vector;

import android.app.Activity; 
import android.app.AlertDialog;
import android.content.Context; 
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle; 
import android.os.Environment;
import android.os.PowerManager; 
import android.view.Menu;
import android.view.MenuItem; 
import android.view.Window; 
import android.view.WindowManager; 
import android.widget.LinearLayout;
import android.widget.Toast;

public class Other_Flashlight extends Activity 
{
	private boolean ifLocked = false;
	private PowerManager.WakeLock mWakeLock; 
	private PowerManager mPowerManager; 
	private LinearLayout mLinearLayout;
	/* �������ֵ�menuѡ��identifier���ñ�ʶ?�¼� */ 
	static final private int M_CHOOSE = Menu.FIRST; 
	static final private int M_EXIT = Menu.FIRST+1;
	static final private int M_SEND_LOG = Menu.FIRST +2;
	/* ��ɫѡ������ɫ����?���� */
	private int[] color={R.drawable.white,R.drawable.blue,
                       R.drawable.pink,R.drawable.green,
                       R.drawable.orange,R.drawable.yellow};
	private int[] text={R.string.str_white,R.string.str_blue,
                      R.string.str_pink,R.string.str_green,
                      R.string.str_orange,R.string.str_yellow};

	@Override 
	public void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
     
		//�����Ի���
		new AlertDialog.Builder(Other_Flashlight.this)
		.setTitle("��ӭʹ�÷�ȥ���ֵ�Ͳ")
		//.setMessage("���¼ע������,ȷ��ע���Ƿ�ɹ�!")
		.setPositiveButton("ok",
			  new DialogInterface.OnClickListener() 
	  			{
					public void onClick(DialogInterface dialog, int which) 
					{
						// TODO Auto-generated method stub
						
					}
				})
				.show();
		/* ����?setContentView֮ǰ��?����Ļ��ʾ */ 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    
		this.getWindow().setFlags
		( 
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		);
		setContentView(R.layout.other);
    
		/* ?Activity����ʱ����Ļ������Ϊ����
		 * �����ΪSDK1.5���½��ܣ���?1.5����أ����
		 */
		WindowManager.LayoutParams lp = getWindow().getAttributes(); 
		lp.screenBrightness = 1.0f; 
		getWindow().setAttributes(lp); 
    
		/* ��ʼ��mLinearLayout */
		mLinearLayout=(LinearLayout)findViewById(R.id.myLinearLayout1);         
    
		/* ȡ��PowerManager */ 
		mPowerManager = (PowerManager)
                     getSystemService(Context.POWER_SERVICE); 
		/* ȡ��WakeLock */
		mWakeLock = mPowerManager.newWakeLock 
		( 
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "BackLight" 
		);    
	} 

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) 
	{ 
		/* menuȺ��ID */ 
		int idGroup1 = 0;    
		/* menuItemID */ 
		int orderMenuItem1 = Menu.NONE; 
		int orderMenuItem2 = Menu.NONE+1; 
		int orderMenuItem3 = Menu.NONE+2;
		/* ����menu */ 
		menu.add(idGroup1,M_CHOOSE,orderMenuItem1,R.string.str_title);
	    menu.add(idGroup1,M_EXIT,orderMenuItem2,R.string.str_exit);
	    menu.add(idGroup1, M_SEND_LOG, orderMenuItem3, R.string.str_log);
	    menu.setGroupCheckable(idGroup1, true, true);
	 
	    return super.onCreateOptionsMenu(menu); 
	} 
   
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) 
	{  
		switch(item.getItemId()) 
	    { 
	      case (M_CHOOSE):
	        /* ��߶ѡ�񱳺���ɫ��AlertDialog */
	        new AlertDialog.Builder(Other_Flashlight.this)
	          .setTitle(getResources().getString(R.string.str_title))
	          .setAdapter(new Other_MyAdapter(this,color,text),listener1)
	          .setPositiveButton("ȡ��",
	              new DialogInterface.OnClickListener()
	          {
	            public void onClick(DialogInterface dialog, int which)
	            {
	            }
	          })
	          .show();
	        break; 
	      case (M_EXIT): 
	        /* �뿪���� */ 
	        this.finish(); 
	        break; 
	        
	      case (M_SEND_LOG):
	    	//���������־������������
	    	SendLogEmail();
	    }
	    return super.onOptionsItemSelected(item); 
	}
  
	/* ѡ�񱳺���ɫ��AlertDialog��OnClickListener */
	OnClickListener listener1=new DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog,int which)
		{
			/* ���ı�����ɫ */
			mLinearLayout.setBackgroundResource(color[which]);
			/* ��Toast��ʾ�趨����ɫ */
			Toast.makeText(Other_Flashlight.this,
                     getResources().getString(text[which]),
                     Toast.LENGTH_LONG).show();
		}
	};
   
	@Override 
	protected void onResume() 
	{  
	    /* onResume()ʱ��?wakeLock() */
	    wakeLock(); 
	    super.onResume(); 
	} 
   
	@Override 
	protected void onPause() 
	{
		/* onPause()ʱ��?wakeUnlock() */
		wakeUnlock(); 
		super.onPause();
	} 
  
	/* ����WakeLock��method */
	private void wakeLock()
	{ 
		if (!ifLocked) 
		{ 
			ifLocked = true;
			mWakeLock.acquire();
		}
	}

	/* �ͷ�WakeLock��method */
	private void wakeUnlock() 
	{ 
	    if (ifLocked) 
	    { 
	    	mWakeLock.release(); 
	    	ifLocked = false;
	    }
	}
  
	////////////////////////////////////////////////////////////////
	//
	//�������ܣ����������־��Ϣ���͵�������������
	//
	//
	//////////////////////////////////////////////////////////////////
	private boolean SendLogEmail()
	{
		//�ʼ���Ϣ����
		FqlEmail mailPort = new FqlEmail();  //ͨ�������ʼ��Ľӿ�
		Vector<String> emailAddress = new Vector<String>();
		emailAddress.add("fql_helper@sina.com.cn");		
		String Subject = "��־��Ϣ_";
		String text = "��־��Ϣ";
	
		  
		//�õ�sdcard·��
		String sdcardDir = Environment.getExternalStorageDirectory().toString();
		
		//�����־�ļ��У�����false
		String folderStr = sdcardDir + java.io.File.separator + "fql_guard";
		File newFilesDir= new File(folderStr); //��־�ļ���
		if (!newFilesDir.exists())
		{
			//����ļ�������,����False;
			return false;
		}
				
		List <String> folderPaths = null;
		String logFilePath = "";
		File f = new File(folderStr); //������־�ļ��е��ļ���
		File oneFile; //�������־�ļ����ļ���
		File [] files = f.listFiles();
				
		for (int i=0; i<files.length; i++)
		{
			oneFile = files[i];
			//newFilePath = oneFile.getPath();
			if (oneFile.isDirectory())
			{
				//������ļ���,�����κδ���
				continue;						
			}
			else
			{
				logFilePath = oneFile.getPath();
				Subject += oneFile.getName();
				
				mailPort.SendFile(emailAddress, Subject, text, logFilePath);
						
			}//end if
				
		}//end for
		
		return true;
		  
	}
  
  
  
}

