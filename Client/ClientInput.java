package com.xuezhaojiang.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * @author Ѧ�׽�
 * ����������server���룬�������Ļ
 */
public class ClientInput extends Thread{
	private Socket socket ;
	String name;
	public ClientInput(Socket socket,String name) {
		this.socket = socket;
		this.name = name;
		// TODO Auto-generated constructor stub
	}
    public void run() 
    {
    	try 
        {
    		BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //��Socket����õ�����������������Ӧ��BufferedReader����
    		String readline;
            while(true){
                if((readline=is.readLine())!="")
                {
                	System.out.println(readline);//��Server����һ�ַ���������ӡ����׼�����
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}
