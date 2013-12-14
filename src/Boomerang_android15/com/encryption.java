package Boomerang_android15.com;


import java.io.*;
import java.security.spec.KeySpec;    
//�ṩ�˼���/ǩ���ӿ�      

import javax.crypto.Cipher;     
//CipherΪ���ܺͽ����ṩ���빦�ܡ��������� Java Cryptographic Extension (JCE) ��ܵĺ��ġ�  
//��Ҫ�����У�init������Կ��ʼ���� Cipher  
//            doFinal:�������ֲ������ܻ�������ݣ����߽���һ���ಿ�ֲ��������ݽ������ܻ���ܣ�����ȡ���ڴ� Cipher �ĳ�ʼ����ʽ����   
 
import javax.crypto.SecretKey;  //��Կ   
import javax.crypto.SecretKeyFactory;  //��Կ����   
//��Կ������������Կ������ Key �Ĳ�͸��������Կ��ת��Ϊ��Կ�淶���ײ���Կ���ϵ�͸����ʾ��ʽ����������Կ����ֻ�����ܣ��Գƣ���Կ���в�������Կ����Ϊ˫��ģʽ������������ݸ�����Կ�淶����Կ���ϣ�������͸����Կ���󣬻����ʵ���ʽ��ȡ��Կ����ĵײ���Կ���ϡ���Կ�����Ĳ��������㷨 ������ģʽ����䣬����Э��  

  
import javax.crypto.spec.DESKeySpec;      
//ʵ��java.security.spec.KeySpec�ӿڣ�����һ�� DESKeySpec ����  

import android.util.Log;

public class encryption 
{
	
	static String DES = "DES/ECB/NoPadding";      
	final int Block_Len=0x1000;
	

	/*���ü��ܷ�ʽ�������㷨��ģʽ�����Ĳ��������庬��μ���http://java.sun.com/j2se/1.4.2/docs/guide/security/jce/JCERefGuide.html#AppA*/    
	//���캯��
	
	byte key[] = new byte[8];
	byte plainBuf []= null;
	int plainLen=0; //���ĳ���
	byte cipherBuf[]= null;
	int cipherLen = 0;
	
	public encryption()
	{
		//��Կ��ʼ��
		for(int i=0; i<8; i++)
		{
			key[i] = 0;			
		}
		
		//buf�����ʼֵ0
	
	}
	
	public boolean SetKey(String strKey)
	{
		if (strKey ==null)
		{	
			//��Կ�ַ���������
			return false;
		}
		
		if (strKey.equals(""))
		{
			return false;
		}
		
		key = strKey.getBytes();
	
		return true;
	}
	
	public String GetKey()
	{
		String strKey = "";
		strKey.valueOf(key);
		
		return strKey;
	}
	
	
	
	
	////////////////////////////////////////////////////////////
	//
	//��������:���ļ����������ݽ��м���
	//����:String fileName �ļ�����
	//
	/////////////////////////////////////////////////////////////////////
	public void encrptFileAll(String fileName)
	{
		File destiFile = new File(fileName);
		RandomAccessFile raf;
		
		long length = 0;
		int count =0; //���Էֳɼ������ܿ�, �������һ�����ݿ�
		int readLen =0;
		plainBuf = new byte[Block_Len];
		for (int i=0; i<Block_Len; i++)
		{
			plainBuf[i]= 0x0;
		}
		
		try
		{
			raf = new RandomAccessFile(destiFile, "rw");
			length = raf.length();
			count = (int)length/Block_Len +1;
			
			for (int i=0; i<count; i++)
			{
				
				readLen = raf.read(plainBuf, 0, Block_Len);
				if (readLen<Block_Len)
				{
					//�������һ�����ݰ�,�ļ����ݳ��Ȳ���һ���鳤�ȵ����
					for (int j=readLen; j<Block_Len; j++)
					{
						plainBuf[j] = 0x0;
					}
				}
				encrptBuf();
				raf.seek(i*Block_Len); 
				raf.write(cipherBuf, 0, cipherLen);
				
			}
			//���ܺ�ԭ���ļ��ĳ���,д�����.
			raf.writeLong(length);
			raf.close();
			
		
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		} 
	
		
	}
	
	
	
	
	////////////////////////////////////////////////////////////
	//
	//��������:���ļ����������ݽ��н���
	//����:String fileName �ļ�����
	//
	/////////////////////////////////////////////////////////////////////
	public void decrptFileAll(String fileName)
	{
		File destiFile = new File(fileName);
		String tempFile = destiFile.getParent()+ "/plain_" + destiFile.getName();
		RandomAccessFile raf;
		FileOutputStream out = null;
		
		long length = 0;	//���ļ���ʵ�ʳ���
		long fileDataLen = 0;	//ȥ��ĩβ��������ݺ��,��ʵ�������ݳ���
		long originalLen =0;	//�������ݳ���
		int count =0; //ͨ�����ٸ����ݿ�������ļ����ݶ�������.���������һ�����ݿ�
		int lastBlock =0; //���һ�����ݿ�ĳ���
		
		cipherBuf =  new byte[Block_Len];

		try
		{
			out = new FileOutputStream(tempFile);
			raf = new RandomAccessFile(destiFile, "rw");
			length = raf.length()-0x8;
			raf.seek(length);
			originalLen = raf.readLong();
			
			count = (int)(length/Block_Len);
			lastBlock = (int)originalLen %Block_Len;
			
			raf.seek(0);
			for (int i=0; i<count-1; i++)
			{
				raf.read(cipherBuf, 0, Block_Len);
				
				decrptBuf();
				 
				out.write(plainBuf, 0, Block_Len);
				//raf.write(plainBuf, 0, cipherLen);				
			}
			//�������һ����
			
			raf.read(cipherBuf, 0, Block_Len);
			
			decrptBuf();
			
			out.write(plainBuf, 0, lastBlock);
			
			raf.close(); //�رն����ļ�
			out.close(); //�ر�д���ļ�
		
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		} 
		

	}
	
	/*
	 
		try
		{
			raf = new RandomAccessFile(destiFile, "rw");
			length =  raf.length();
		
			
			if (length>256)
			{
				cipherLen  = 256;
				raf.read(cipherBuf, 0, 256);
			}
			else
			{
				//С��256�ֽڵ��ļ����ж�ȡ�ļ���ȫ�����ݽ��м���.
				cipherLen = (int)length;
				raf.read(cipherBuf, 0, cipherLen );
			}
			
			decrptBuf();
			
			raf.seek(0); //�Ƿ���Ҫ��һ��ȷ��
			//д���������
			raf.write(plainBuf, 0, plainLen);
			
			
			plainBuf = null;	
			cipherBuf = null;
			raf.close();
			

		}
	  */
	////////////////////////////////////////////////////////////
	//
	//��������:���ļ����ݽ��м���
	//����:String fileName �ļ�����
	//
	/////////////////////////////////////////////////////////////////////
	public void encrptFile(String fileName)
	{
		File destiFile = new File(fileName);
		RandomAccessFile raf;
		
		Log.i("333+++", "encryption encrptFile : begin!");
		
		long length=0;
	
		plainBuf = new byte[256];
		for (int i=0; i<256; i++)
		{
			plainBuf[i]= 0x0;
		}
		
		try
		{
			raf = new RandomAccessFile(destiFile, "rw");
			length =  raf.length();
		
			
			if (length>=256)
			{
				plainLen = 256;
				raf.read(plainBuf, 0, 256);
			}
			else
			{
				//С��256�ֽڵ��ļ����ж�ȡ�ļ���ȫ�����ݽ��м���.
				plainLen= (int)length;
				raf.read(plainBuf, 0, plainLen );
			}
			
			encrptBuf();
			
			raf.seek(0); //�Ƿ���Ҫ��һ��ȷ��
			//д���������
			raf.write(cipherBuf, 0, cipherLen);
			
			plainBuf = null;
			cipherBuf = null;
			raf.close();
			/*
			 С��64�ֽڵ�����,�ر��64�ֽ�.
			 */

		}
		catch(Exception e)
		{
			e.printStackTrace();	
		} 
		
	}
	
		

	////////////////////////////////////////////////////////////
	//
	//��������:���ļ����ݽ��н���
	//����:String fileName �ļ�����
	//
	/////////////////////////////////////////////////////////////////////
	public void decrptFile(String fileName)
	{
		File destiFile = new File(fileName);
		RandomAccessFile raf;

		
		long length=0;
		cipherBuf = new byte[256];
		for (int i=0; i<256; i++)
		{
			cipherBuf[i] = 0x0;
		}
	

		try
		{
			raf = new RandomAccessFile(destiFile, "rw");
			length =  raf.length();
		
			
			if (length>256)
			{
				cipherLen  = 256;
				raf.read(cipherBuf, 0, 256);
			}
			else
			{
				//С��256�ֽڵ��ļ����ж�ȡ�ļ���ȫ�����ݽ��м���.
				cipherLen = (int)length;
				raf.read(cipherBuf, 0, cipherLen );
			}
			
			decrptBuf();
			
			raf.seek(0); //�Ƿ���Ҫ��һ��ȷ��
			//д���������
			raf.write(plainBuf, 0, plainLen);
			
			
			plainBuf = null;	
			cipherBuf = null;
			raf.close();
			/*
			 С��64�ֽڵ�����,�ر��64�ֽ�.
			 */

		}
		catch(Exception e)
		{
			e.printStackTrace();	
		} 
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////
	//
	//��������:��256�ֽڳ��ȵ����ݽ��м���
	//����: String plaintext   ����
	//		String key   ������Կ����Ϊ8�ֽ�
	//		String ciphertext	����
	//
	//
	///////////////////////////////////////////////////////////////////////////
	
	private boolean encrptBuf()
	{

	 
		
		Log.i("333+++", "encryption encrptBuf : begin!");
		
		des_crypt(); /*���м���*/  
		//���Ƚ���ʶ��.
		
		return true;
		
		
	}

	///////////////////////////////////////////////////////////////////////////
	//
	//��������:��256�ֽڳ��ȵ����ݽ��н���
	//����: String ciphertext  ����
	//		String key   ������Կ����Ϊ8�ֽ�
	//		String plaintext	����
	//
	//
	///////////////////////////////////////////////////////////////////////////
	private boolean decrptBuf()
	{
		
		
		des_decrypt(); /*���м���*/  
		
		
		//����λ����.
		return true;
		
	}
	
	////////////////////////////////////////////////////////////////
	//
	//��������:��������
	//
	/////////////////////////////////////////////////////////////////
	public void des_crypt() 
	{     
		byte buf[] = null;
	    try 
	    {     
	    	
	        KeySpec ks = new DESKeySpec(key);  //�½���Կ�淶
	        
	        /* ������Կ����ģʽΪDES����������ΪAES��DES��DESede��PBEWith��ģʽ  
	    	http://java.sun.com/j2se/1.4.2/docs/guide/security/jce/JCERefGuide.html#AppA  
	    	*/    
	        SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");    
	 
	        SecretKey ky = kf.generateSecret(ks);  //������Կ 
	        Cipher c = Cipher.getInstance(DES);  //���ü��ܹ���/
	        c.init(Cipher.ENCRYPT_MODE, ky);  //���ܳ�ʼ��/
	        
	        
	        buf = c.doFinal(plainBuf);  //��������
	        cipherLen = buf.length;
	        cipherBuf = null;
	        cipherBuf = buf;
	        
	        /*
	        //cipherBuf =  c.doFinal(plainBuf);  //��������
	        //�������
	        cipherLen = cipherBuf.length; 
	        */
	       
	    } 
	    catch (Exception e) 
	    {     
	        e.printStackTrace();     
	        
	    }     
	}    

	//////////////////////////////////////////////////////////////////////////
	//
	//��������:��������
	//
	//
	//////////////////////////////////////////////////////////////////////////
	public  void des_decrypt() 
	{     
		byte buf[]=null;
	    try 
	    {     
	        KeySpec ks = new DESKeySpec(key);     
	        SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");     
	        SecretKey ky = kf.generateSecret(ks);     
	        Cipher c = Cipher.getInstance(DES);     
	        c.init(Cipher.DECRYPT_MODE, ky);  
	        
	        buf = c.doFinal(cipherBuf); 
	        plainLen = buf.length;
	        plainBuf = null;
	        plainBuf = buf;
	        
	        
	        /*
	        plainBuf = c.doFinal(cipherBuf);   
	        plainLen = plainBuf.length;
	        */
	        
	    } 
	    catch (Exception e) 
	    {     
	        e.printStackTrace();     
	         
	    }     
	  
	}


}

/*

byte des_key[]= enc_key.getBytes();//��������ȡ��Կ 
byte data[] = new byte[64]; //��ʼ����������  
byte tempdata[]= et_text.getText().toString().getBytes();   
//��������ȡ��������  
for (int i=0;i<tempdata.length;i++)   
    data[i]=tempdata[i];   
if (tempdata.length <64){ //���ƶ���������data������64λ��0
        for (int i=data.length;i<64;i++)   
        data[i]='\0';   
        }   
byte result[] =des_crypt(des_key, data); //���м���

 */