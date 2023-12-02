# netty_chat
利用netty实现了一个简单的聊天业务：
```java
==================================
send [username] [content]
gsend [group name] [content]
gcreate [group name] [m1,m2,m3...]
gmembers [group name]
gjoin [group name]
gquit [group name]
quit
==================================
```