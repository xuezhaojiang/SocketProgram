# SocketProgram
学习socket编程
Socket处理输入输出用的是线程，不是进程。    
     
聊天室功能的实现参考博客http://blog.csdn.net/Marksinoberg/article/details/51142483     
作者使用新添加一个客户端便新开一个线程，并且用动态数组管理当前的客户端。通过循环检查客户端的输入，并且将输入公布出去。     
     
UDP传输文件的实现参考博客http://blog.csdn.net/u010811257/article/details/39455181     
作者做的好的地方是接收方和传送方通信，传输错误就重传。     
作者做的不好的是，接受和发送数据用的都是一个DatagramPacket让人容易混淆，最关键的是作者只用了一个DatagramPacket这样便必需指定发送方的ip和端口，其实接受数据时是不必知道对方的ip和端口的，非常容易让人糊涂。
