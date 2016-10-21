package com.xuezhaojiang.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * @author Ѧ�׽�
 * ��������ȴӼ��̶��룬�������server
 */
public class ClientOutput extends Thread{
	private Socket socket ;
	String name;
	public ClientOutput(Socket socket,String name) {
		this.socket = socket;
		this.name = name;
		// TODO Auto-generated constructor stub
	}
    public void run() 
    {
    	try 
        {
    		BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
            //��ϵͳ��׼�����豸����BufferedReader����  ͨ��os �����server
            PrintWriter os=new PrintWriter(socket.getOutputStream());
            //��Socket����õ��������������PrintWriter����
    		String outline;
    		while(true){
            	if((outline=sin.readLine())!=""){//��ϵͳ��׼�������һ�ַ���
            		os.println(name+':'+outline);//����ϵͳ��׼���������ַ��������Server
            	}
            	os.flush();//ˢ���������ʹServer�����յ����ַ���
    		}
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}

