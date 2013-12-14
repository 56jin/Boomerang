package Boomerang_android15.com;

/*
 * ��Ĺ��ܣ�����ע��������ʾ��
 * ���������ݵ��߼�����
 * */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
//import android.widget.LinearLayout;
import android.widget.TextView;

//public class RegisterFaceView extends LinearLayout
public class RegisterFaceView 
{
	/*
	 * �ؼ���չʾ
	 * */  
	AbsoluteLayout m_abslayout;
	  
	private TextView m_textViewDescript;
	
	private EditText m_editTextUserName;
	private TextView m_textViewUserName;
	
	private TextView m_textViewPassword;
	private EditText m_editTextPassword;
	
	private TextView m_textViewEmail_1;
	private EditText m_editTextEmail_1;
	
	private TextView m_textViewEmail_2;
	private EditText m_editTextEmail_2;
	
	private Button m_buttonFinish;
	private Button m_buttonCancel;
	
	private ImageButton m_imageButtonLogo; 
	
	
	
	/*
	 * ��Ļ�����Ϣ
	 * */
	private Context m_context;	//Activity��thisָ��
	private float m_widthPixels;
	private float m_heightPixels;
	
	
	/*
	 * ��Ԫ�ص�λ����Ϣ����
	 * */
	private int STANDARD_WEIGHT = 480;
	private int STANDARD_HEIGHT = 800; 

	private int POINT_LOGO_X=40;  //logo�ĺ�����λ�õ�
	private int POINT_LOGO_Y=50;  //logo��������λ�õ�
	
	private int POINT_TITLE_X = 170; //Title�ĺ�����λ�õ�
	private int POINT_TITLE_Y = 62; //Title��������λ�õ�
	
	//�û���
	private int POINT_USERNAME_TEXT_X =60; //�û�����ǩ�ĺ�����
	private int POINT_USERNAME_TEXT_Y =165; //�û�����ǩ��������
	
	private int POINT_USERNAME_EDIT_X =150; //�û��������ĺ�����
	private int POINT_USERNAME_EDIT_Y =160; //�û���������������
	
	
	//����
	private int POINT_PASSWORD_TEXT_X =60; //�����ǩ�ĺ�����
	private int POINT_PASSWORD_TEXT_Y =230; //�����ǩ��������
	
	private int POINT_PASSWORD_EDIT_X =150; //���������ĺ�����
	private int POINT_PASSWORD_EDIT_Y =225; //����������������
	
	//����һ
	private int POINT_FIRST_EMAIL_TEXT_X =60; //����һ��ǩ�ĺ�����
	private int POINT_FIRST_EMAIL_TEXT_Y =290; //����һ��ǩ��������
	
	private int POINT_FIRST_EMAIL_EDIT_X =150; //����һ�����ĺ�����
	private int POINT_FIRST_EMAIL_EDIT_Y =285; //����һ������������
	
	
	//�����
	private int POINT_SECOND_EMAIL_TEXT_X =60; //�������ǩ�ĺ�����
	private int POINT_SECOND_EMAIL_TEXT_Y =355; //�������ǩ��������
	
	private int POINT_SECOND_EMAIL_EDIT_X =150; //����������ĺ�����
	private int POINT_SECOND_EMAIL_EDIT_Y =350; //�����������������
	
	//OK ��ť
	private int POINT_OK_BUTTON_X = 100; //ok��ť�ĺ�����
	private int POINT_OK_BUTTON_Y = 450; //ok��ť��������
	
	//cancel ��ť
	private int POINT_CANCEL_BUTTON_X =300; //cancel��ť�ĺ�����
	private int POINT_CANCEL_BUTTON_Y =450; //cancel��ť��������
	
	

	
	/*
	 * ��������Ĵ�С
	 * */
	//logo�Ĵ�С
	private int LOGO_WIDTH = 70;
	private int LOGO_HEIGHT = 70;
	
	//title�Ĵ�С
	private int TITLE_WIDTH = 250;
	private int TITLE_HEIGHT = 41;
	
	//text�Ĵ�С
	private int TEXT_WIDTH = 170;
	private int TEXT_HEIGHT = 40;
	
	//edit�����Ĵ�С
	private int EDIT_WIDTH = 250;
	private int EDIT_HEIGHT = 30;
	private int EDIT_HEIGHT_LAYOUT_PARAMS = 50;
	
	//��ť�Ĵ�С
	private int BUTTON_WIDTH = 80;
	private int BUTTON_HEIGHT = 45;
	private int BUTTON_WIDTH_LAYOUT_PARAMS = 90;
	private int BUTTON_HEIGHT_LAYOUT_PARAMS = 55;
	private int IMAGE_BUTTON_SIDE = 70;
	
	
	
	
	/*
	 * ������Ļ��С����õĸ�Ԫ����Ϣ��ʵ��ֵ
	 * */
	private int m_point_logo_x = 0;  //logo�ĺ�����λ�õ�
	private int m_point_logo_y = 0;  //logo��������λ�õ�
	
	private int m_point_title_x = 0; //Title�ĺ�����λ�õ�
	private int m_point_title_y = 0; //Title��������λ�õ�
	
	//�û���
	private int m_point_username_text_x = 0; //�û�����ǩ�ĺ�����
	private int m_point_username_text_y = 0; //�û�����ǩ��������
	
	private int m_point_username_edit_x = 0; //�û��������ĺ�����
	private int m_point_username_edit_y = 0; //�û���������������
	
	
	//����
	private int m_point_password_text_x = 0; //�����ǩ�ĺ�����
	private int m_point_password_text_y = 0; //�����ǩ��������
	
	private int m_point_password_edit_x = 0; //���������ĺ�����
	private int m_point_password_edit_y = 0; //����������������
	
	//����һ
	private int m_point_first_email_text_x = 0; //����һ��ǩ�ĺ�����
	private int m_point_first_email_text_y = 0; //����һ��ǩ��������
	
	private int m_point_first_email_edit_x = 0; //����һ�����ĺ�����
	private int m_point_first_email_edit_y = 0; //����һ������������
	
	
	//�����
	private int m_point_second_email_text_x = 0; //�������ǩ�ĺ�����
	private int m_point_second_email_text_y = 0; //�������ǩ��������
	
	private int m_point_second_email_edit_x = 0; //����������ĺ�����
	private int m_point_second_email_edit_y = 0; //�����������������
	
	//OK ��ť
	private int m_point_ok_button_x = 0; //ok��ť�ĺ�����
	private int m_point_ok_button_y = 0; //ok��ť��������
	
	//cancel ��ť
	private int m_point_cancel_button_x = 0; //cancel��ť�ĺ�����
	private int m_point_cancel_button_y = 0; //cancel��ť��������
	
	
	/*
	 * ��������Ĵ�С
	 * */
	//logo�Ĵ�С
	private int m_logo_width = 0;
	private int m_logo_height = 0;
	
	//title�Ĵ�С
	private int m_title_width = 0;
	private int m_title_height = 0;
	
	//text�Ĵ�С
	private int m_text_width = 0;
	private int m_text_height = 0;
	
	//edit�����Ĵ�С
	private int m_edit_width = 0;
	private int m_edit_height = 0;
	private int m_edit_height_layout_params = 0;
	
	//��ť�Ĵ�С
	private int m_button_width = 0;
	private int m_button_height = 0; 
	private int m_button_width_layout_params = 0;
	private int m_button_height_layout_params = 0;
	
	private int m_image_button_side = 0;
	
	/*
	 * �������ܣ����캯��
	 * */
	public RegisterFaceView(Context c, DisplayMetrics dm) 
	{ 
		m_context = c;
		m_abslayout=new AbsoluteLayout (m_context);
		m_widthPixels = dm.widthPixels;
		m_heightPixels = dm.heightPixels;
		
		ComputerInterface();
		DrawTitle();
		DrawUserName();
		DrawPassword();
		DrawFirstEmail();
		DrawSecondEmail();
		DrawOKButton();
		DrawCancelButton();
		DrawLogo();
 
		//float x = dm.xdpi;

		
		
         
	} 
	
	
	/*
	 * �������ܣ����ݷֱ��ʵ����������������ʾ
	 * */
	private void ComputerInterface()
	{
		float widthRate = 0;  //��Ļ��ȱ仯��
		float heightRate = 0; //��Ļ�߶ȱ仯��
		float averageRate = 0;  //ƽ���仯��
		
		
		widthRate = this.m_widthPixels/this.STANDARD_WEIGHT;
		heightRate =  this.m_heightPixels/this.STANDARD_HEIGHT;
		averageRate = (widthRate+heightRate)/2;
				
	
		/*
		 * ������Ļ��С����õĸ�Ԫ����Ϣ��ʵ��ֵ
		 * */
		m_point_logo_x = Math.round(this.POINT_LOGO_X * averageRate);  //logo�ĺ�����λ�õ�
		m_point_logo_y = Math.round(this.POINT_LOGO_Y * averageRate);  //logo��������λ�õ�
		
		m_point_title_x = Math.round(this.POINT_TITLE_X * averageRate); //Title�ĺ�����λ�õ�
		m_point_title_y = Math.round(this.POINT_TITLE_Y * averageRate); //Title��������λ�õ�
		
		//�û���
		m_point_username_text_x =  Math.round(this.POINT_USERNAME_TEXT_X * averageRate); //�û�����ǩ�ĺ�����
		m_point_username_text_y = Math.round(this.POINT_USERNAME_TEXT_Y * averageRate); //�û�����ǩ��������
		
		m_point_username_edit_x = Math.round(this.POINT_USERNAME_EDIT_X * averageRate); //�û��������ĺ�����
		m_point_username_edit_y = Math.round(this.POINT_USERNAME_EDIT_Y * averageRate); //�û���������������
		
		
		//����
		m_point_password_text_x = Math.round(this.POINT_PASSWORD_TEXT_X * averageRate); //�����ǩ�ĺ�����
		m_point_password_text_y = Math.round(this.POINT_PASSWORD_TEXT_Y * averageRate); //�����ǩ��������
		
		m_point_password_edit_x = Math.round(this.POINT_PASSWORD_EDIT_X * averageRate); //���������ĺ�����
		m_point_password_edit_y = Math.round(this.POINT_PASSWORD_EDIT_Y * averageRate); //����������������
		
		//����һ
		m_point_first_email_text_x = Math.round(this.POINT_FIRST_EMAIL_TEXT_X * averageRate); //����һ��ǩ�ĺ�����
		m_point_first_email_text_y = Math.round(this.POINT_FIRST_EMAIL_TEXT_Y * averageRate); //����һ��ǩ��������
		
		m_point_first_email_edit_x = Math.round(this.POINT_FIRST_EMAIL_EDIT_X * averageRate); //����һ�����ĺ�����
		m_point_first_email_edit_y = Math.round(this.POINT_FIRST_EMAIL_EDIT_Y * averageRate); //����һ������������
		
		
		//�����
		m_point_second_email_text_x = Math.round(this.POINT_SECOND_EMAIL_TEXT_X * averageRate); //�������ǩ�ĺ�����
		m_point_second_email_text_y = Math.round(this.POINT_SECOND_EMAIL_TEXT_Y * averageRate); //�������ǩ��������
		
		m_point_second_email_edit_x = Math.round(this.POINT_SECOND_EMAIL_EDIT_X * averageRate); //����������ĺ�����
		m_point_second_email_edit_y = Math.round(this.POINT_SECOND_EMAIL_EDIT_Y * averageRate); //�����������������
		
		//OK ��ť
		m_point_ok_button_x = Math.round(this.POINT_OK_BUTTON_X * averageRate); //ok��ť�ĺ�����
		m_point_ok_button_y = Math.round(this.POINT_OK_BUTTON_Y * averageRate); //ok��ť��������
		
		//cancel ��ť
		m_point_cancel_button_x = Math.round(this.POINT_CANCEL_BUTTON_X * averageRate);  //cancel��ť�ĺ�����
		m_point_cancel_button_y = Math.round(this.POINT_CANCEL_BUTTON_Y * averageRate); //cancel��ť��������
		
		
		/*
		 * ��������Ĵ�С
		 * */
		//logo�Ĵ�С
		m_logo_width = Math.round(this.LOGO_WIDTH * averageRate);
		m_logo_height = Math.round(this.LOGO_HEIGHT * averageRate);
		
		//title�Ĵ�С
		m_title_width = Math.round(this.TITLE_WIDTH * averageRate);
		m_title_height = Math.round(this.TITLE_HEIGHT * averageRate);
		
		//text�Ĵ�С
		m_text_width = Math.round(this.TEXT_WIDTH * averageRate);
		m_text_height = Math.round(this.TEXT_HEIGHT * averageRate);
		
		//edit�����Ĵ�С
		m_edit_width = Math.round(this.EDIT_WIDTH * averageRate);
		m_edit_height = Math.round(this.EDIT_HEIGHT * averageRate);
		m_edit_height_layout_params = Math.round(this.EDIT_HEIGHT_LAYOUT_PARAMS * averageRate);
		
		//��ť�Ĵ�С
		m_button_width = Math.round(this.BUTTON_WIDTH * averageRate);
		m_button_height = Math.round(this.BUTTON_HEIGHT * averageRate); 
		m_button_width_layout_params = Math.round(this.BUTTON_WIDTH_LAYOUT_PARAMS * averageRate);
		m_button_height_layout_params = Math.round(this.BUTTON_HEIGHT_LAYOUT_PARAMS * averageRate);
		
		//ͼƬ��ť�Ĵ�С��ʱ�ǹ̶�ֵ
		//this.m_image_button_side = Math.round(this.IMAGE_BUTTON_SIDE * averageRate); 
		this.m_image_button_side =70;
		
	}
	


	
	/*
	 * �������ܣ���ʾ���Title
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawTitle()
	{
		
		m_textViewDescript = new TextView(m_context);       
		m_textViewDescript.setText("����ȥ�ֻ���ʿ");         
		m_textViewDescript.setWidth(this.m_title_width);
		m_textViewDescript.setHeight(this.m_title_height);
		m_textViewDescript.setTextSize(18);
		
		m_textViewDescript.setId(1);
		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_point_title_x,
				this.m_point_title_y);
		m_abslayout.addView(m_textViewDescript, lp1);
		
	}
  
	/*
	 * �������ܣ���ʾ�û�����Ϣ
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawUserName()
	{
		//�û����ı�
		m_textViewUserName = new TextView(m_context);       
		m_textViewUserName.setText("�û���:");         
		m_textViewUserName.setWidth(this.m_text_width);
		m_textViewUserName.setHeight(this.m_text_height);
		m_textViewUserName.setTextSize(15);
		
		m_textViewUserName.setId(2);
		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_point_username_text_x,
				this.m_point_username_text_y);
		m_abslayout.addView(m_textViewUserName, lp1);
	
		
		//�û��������
		this.m_editTextUserName = new EditText(this.m_context);
		m_editTextUserName.setWidth(this.m_edit_width);
		m_editTextUserName.setHeight(this.m_edit_height);
		m_editTextUserName.setTextSize(13);
		m_editTextUserName.setId(3);
		m_editTextUserName.setSingleLine(true);
		
		
		AbsoluteLayout.LayoutParams lp2 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				m_edit_height_layout_params,
				m_point_username_edit_x,
				m_point_username_edit_y);
		
		m_abslayout.addView(m_editTextUserName, lp2);	
		

	}
	
	
	/*
	 * �������ܣ���ʾ������Ϣ
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawPassword()
	{
		
		this.m_textViewPassword = new TextView(m_context);       
		this.m_textViewPassword.setText("��    ��:");         
		this.m_textViewPassword.setWidth(this.m_text_width);
		this.m_textViewPassword.setHeight(this.m_text_height);
		this.m_textViewPassword.setTextSize(15);
		
		m_textViewUserName.setId(2);
		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_point_password_text_x,
				this.m_point_password_text_y);
		m_abslayout.addView(this.m_textViewPassword, lp1);
	
		

		this.m_editTextPassword = new EditText(this.m_context);
		this.m_editTextPassword.setWidth(this.m_edit_width);
		this.m_editTextPassword.setHeight(this.m_edit_height);
		this.m_editTextPassword.setTextSize(13);
		this.m_editTextPassword.setId(3);
		this.m_editTextPassword.setSingleLine(true);
		
		
		AbsoluteLayout.LayoutParams lp2 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				m_edit_height_layout_params,
				this.m_point_password_edit_x,
				this.m_point_password_edit_y);
		
		m_abslayout.addView(this.m_editTextPassword, lp2);	
		
		 
	}
	
	/*
	 * �������ܣ���ʾ��һ����
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawFirstEmail()
	{
		
		this.m_textViewEmail_1 = new TextView(m_context);       
		this.m_textViewEmail_1.setText("����һ:");         
		this.m_textViewEmail_1.setWidth(this.m_text_width);
		this.m_textViewEmail_1.setHeight(this.m_text_height);
		this.m_textViewEmail_1.setTextSize(15);
		
		m_textViewUserName.setId(2);
		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_point_first_email_text_x,
				this.m_point_first_email_text_y);
		m_abslayout.addView(this.m_textViewEmail_1, lp1);
	
		

		this.m_editTextEmail_1 = new EditText(this.m_context);
		this.m_editTextEmail_1.setWidth(this.m_edit_width);
		this.m_editTextEmail_1.setHeight(this.m_edit_height);
		this.m_editTextEmail_1.setTextSize(13);
		this.m_editTextEmail_1.setId(3);
		this.m_editTextEmail_1.setSingleLine(true);
		
		
		AbsoluteLayout.LayoutParams lp2 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				m_edit_height_layout_params,
				this.m_point_first_email_edit_x,
				this.m_point_first_email_edit_y);
		
		m_abslayout.addView(this.m_editTextEmail_1, lp2);	
		
		 
	}
	
	
	/*
	 * �������ܣ���ʾ�ڶ�����
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawSecondEmail()
	{
		
		this.m_textViewEmail_2 = new TextView(m_context);       
		this.m_textViewEmail_2.setText("�����:");         
		this.m_textViewEmail_2.setWidth(this.m_text_width);
		this.m_textViewEmail_2.setHeight(this.m_text_height);
		this.m_textViewEmail_2.setTextSize(15);
		
		m_textViewUserName.setId(2);
		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_point_second_email_text_x,
				this.m_point_second_email_text_y);
		m_abslayout.addView(this.m_textViewEmail_2, lp1);
	
		

		this.m_editTextEmail_2 = new EditText(this.m_context);
		this.m_editTextEmail_2.setWidth(this.m_edit_width);
		this.m_editTextEmail_2.setHeight(this.m_edit_height);
		this.m_editTextEmail_2.setTextSize(13);
		this.m_editTextEmail_2.setId(3);
		this.m_editTextEmail_2.setSingleLine(true);
		
		
		AbsoluteLayout.LayoutParams lp2 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				m_edit_height_layout_params,
				this.m_point_second_email_edit_x,
				this.m_point_second_email_edit_y);
		
		m_abslayout.addView(this.m_editTextEmail_2, lp2);	
		
		 
	}
	
	
	
	/*
	 * �������ܣ���ʾ"ȷ��"��ť
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawOKButton()
	{
		
		this.m_buttonFinish = new Button(m_context);       
		this.m_buttonFinish .setText("ȷ  ��");         
		this.m_buttonFinish.setWidth(this.m_button_width);
		this.m_buttonFinish.setHeight(this.m_button_height);
		this.m_buttonFinish.setTextSize(14);
		this.m_buttonFinish.setSingleLine();
		
		m_textViewUserName.setId(2);
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_button_width_layout_params,
				this.m_button_height_layout_params,
				this.m_point_ok_button_x,
				this.m_point_ok_button_y);
		m_abslayout.addView(this.m_buttonFinish, lp);
				
		 
	}
	
	/*
	 * �������ܣ���ʾ"ȡ��"��ť
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawCancelButton()
	{
		
		this.m_buttonCancel = new Button(m_context);       
		this.m_buttonCancel.setText("ȡ  ��");         
		this.m_buttonCancel.setWidth(this.m_button_width);
		this.m_buttonCancel.setHeight(this.m_button_height);
		this.m_buttonCancel.setTextSize(14);
		this.m_buttonCancel.setSingleLine();
		
		m_textViewUserName.setId(2);
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				this.m_button_width_layout_params, 
				this.m_button_height_layout_params,
				this.m_point_cancel_button_x,
				this.m_point_cancel_button_y);
		m_abslayout.addView(this.m_buttonCancel, lp);
				
		 
	}
	
	
	
	
	/*
	 * �������ܣ���ʾLOGO
	 * ��������
	 * ����ֵ��void
	 * author��anbc
	 * */
	private void DrawLogo()
	{
		Resources res = this.m_context.getResources(); 
		Bitmap logoBitMap = BitmapFactory.decodeResource(res, R.drawable.logo); 
		logoBitMap.getHeight();
		



		m_imageButtonLogo = new ImageButton(this.m_context); 		
		//m_imageButtonLogo.setImageBitmap(logoBitMap);
		m_imageButtonLogo.setImageResource(R.drawable.logo);
		m_imageButtonLogo.setScaleType(ImageButton.ScaleType.FIT_CENTER);
		//m_imageButtonLogo.setScaleType(ImageButton.ScaleType.FIT_XY);
		//m_imageButtonLogo.setScaleType(ImageButton.ScaleType.CENTER_INSIDE);
		//m_imageButtonLogo.setScaleType(ImageButton.ScaleType.FIT_END);
		//m_imageButtonLogo.setScaleType(ImageButton.ScaleType.FIT_START);
		//m_imageButtonLogo.setScaleType(ImageButton.ScaleType.FIT_XY);
		//m_imageButtonLogo.setScaleType(ImageButton.ScaleType.MATRIX);
		m_imageButtonLogo.setBackgroundColor(0xFF000000);
		
		m_imageButtonLogo.setId(10);
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				//ViewGroup.LayoutParams.WRAP_CONTENT,
				m_image_button_side,
				m_image_button_side,
				m_point_logo_x,
				m_point_logo_y);
		m_abslayout.addView(m_imageButtonLogo, lp);
	
		
		
		 
	}
	

	
	/*
	 * �������ܣ���ȡAbsoluteLayout����
	 * ��������
	 * ����ֵ��AbsoluteLayout
	 * author��anbc
	 * date��2011.12.25
	 * */
	public AbsoluteLayout GetRegisterFaceView()
	{
		return this.m_abslayout;
	}
	
	
	/*
	 * �������ܣ���ȡ�û�������EditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public String GetUserNameEditTextString()
	{
		return this.m_editTextUserName.getText().toString();
	}
	
	
	/*
	 * �������ܣ���ȡ�û��������EditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public String GetPasswordEditTextString()
	{
		return this.m_editTextPassword.getText().toString();
	}
	
	/*
	 * �������ܣ���ȡ����һEditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public String GetFirstEmailEditTextString()
	{
		return this.m_editTextEmail_1.getText().toString();
	}
	
	/*
	 * �������ܣ���ȡ�����EditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public String GetSecondEmailEditTextString()
	{
		return this.m_editTextEmail_2.getText().toString();
	}
	
	/*
	 * �������ܣ���ȡOK��ťEditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public Button GetOKButton()
	{
		return this.m_buttonFinish;
	}
	
	/*
	 * �������ܣ���ȡCancel��ťEditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public Button GetCancelButton()
	{
		return this.m_buttonCancel;
	}
	
	/*
	 * �������ܣ���ȡCancel��ťEditText����
	 * ��������
	 * ����ֵ��EditText
	 * author��anbc
	 * date��2011.12.25
	 * */
	public ImageButton GetLogoImageButton()
	{
		return this.m_imageButtonLogo;
	}
	
}

