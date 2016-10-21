参考博客http://blog.csdn.net/Marksinoberg/article/details/51142483     
作者使用新添加一个客户端便新开一个线程，并且用动态数组管理当前的客户端。     
每个线程中循环查询是否对应客户端发来了消息，并把消息公布出去。
