package com.xuezhaojiang.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * @author 薛兆江
 * 输出流，先从键盘读入，再输出到server
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
            //由系统标准输入设备构造BufferedReader对象  通过os 输出到server
            PrintWriter os=new PrintWriter(socket.getOutputStream());
            //由Socket对象得到输出流，并构造PrintWriter对象
    		String outline;
    		while(true){
            	if((outline=sin.readLine())!=""){//从系统标准输入读入一字符串
            		os.println(name+':'+outline);//将从系统标准输入读入的字符串输出到Server
            	}
            	os.flush();//刷新输出流，使Server马上收到该字符串
    		}
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}

