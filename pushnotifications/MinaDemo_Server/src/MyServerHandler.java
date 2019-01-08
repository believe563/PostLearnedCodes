import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author Administrator
 * mina的所有handler都是要继承IoHandlerAdapter的
 * 菜单中source--override/implement methods可以看到类的所有可重写的方法
 */
public class MyServerHandler extends IoHandlerAdapter{
	/**
	 * IoHandler可重写方法的作用：
	 * exceptionCaught：出现异常时会调用这个方法
	 * messageReceive：收到消息的时候会进到这个方法
	 * messageSent；发送完消息会进到这个方法
	 * sessionClosed:客户端服务器关闭会话时会进
	 * sessionCreated:会话创建时
	 * sessionIdle：会话期间进入空闲状态
	 * sessionOpened：会话打开时
	 */

	@Override
public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		//session参数是用来管理会话的，message表示收到的消息（Object类型表示mina不只支持字符串，还有其它好多种）
		//mina收到object类型的消息怎样进行转化---收到来自网络的消息时是字节，然后通过解码器转换为对象类型
		String s=(String) message;
		System.out.println("messageReceived:"+s);
		session.write("server reply:"+s);//仅仅用这一句话就可以向客户端返回一条消息
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("sessionCreated");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		//这个功能一般用来检测客户端是否掉线，
		//客户端想要与服务器有一个长时间的连接，就有一个心跳机制，每隔多长时间向服务器发送一条消息，告诉服务器自己还在连着
		//客户端在一个时间内没有发送消息，服务器就认为客户端在空闲状态，可以选择把客户端踢下线或进行一些其它的操作
		System.out.println("sessionIdle");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened");
	}
	
}
