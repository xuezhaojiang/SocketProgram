# SocketProgram
学习socket编程
Socket处理输入输出用的是线程，不是进程。    
     
聊天室功能的实现参考博客http://blog.csdn.net/Marksinoberg/article/details/51142483     
作者使用新添加一个客户端便新开一个线程，并且用动态数组管理当前的客户端。通过循环检查客户端的输入，并且将输入公布出去。     
     
UDP传输文件的实现参考博客http://blog.csdn.net/u010811257/article/details/39455181     
作者做的好的地方是接收方和传送方通信，传输错误就重传。     
作者做的不好的是，接受和发送数据用的都是一个DatagramPacket让人容易混淆，最关键的是作者只用了一个DatagramPacket这样便必需指定发送方的ip和端口，其实接受数据时是不必知道对方的ip和端口的，非常容易让人糊涂。      
tcp是在运输层来检查是否有丢失数据，udp并不检查这一点，为了做的不丢包，自能在应用层检查，也就是接收方给予反馈，由于双方要通信，需要在自己文件中填写对方的ip。开始时我用的byte[]表示，但java中没有无符号数，填写时只能有负数表示较大的数，但一直做不到两台主机上通信，也不知道为什么。后来改用string中填写点分十进制的ip,方便填写，也能在两台主机上通信了。      
     
我上传的是一个个的project(工程)，关于源文件是如何组织的，可以看src文件夹的结构。
至于怎么导入project,在workspace（工作区）右键->import->General->Existing Projects into Workspace       
