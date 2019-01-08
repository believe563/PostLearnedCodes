import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @author Administrator mina设计是想把网络管理方面的代码和消息处理的代码做分割， 方便代码进行管理
 *         所以处理消息方面是用handler来做处理的--step2（新建一个专门用来处理消息收发的handlerduixia）
 */
public class MinaServer {
	public static 	void main(String[] args) {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();// 1st step
		acceptor.setHandler(new MyServerHandler());// 2nd step
		// setHandler表示使用MyServerHandler来做会话管理和消息处理

		//第二部分》》定义sessionIdle的空闲时间：
//		acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 2);//第一个参数是什么样的状态下会调用这个方法，第二个参数单位是秒
		
		// 第三步是mina的拦截器机制
		// 是设计模式中的责任链的设计模式
		// acceptor.getFilterChain()方法可以获取到mina当前所有的拦截器
		// 所收发的所有消息必须要经过拦截器过滤之后，这条消息才能到messageReceive等方法中
		// addLast就是添加新的拦截器 (ProtocolCodecFilter是一个拦截器--用于二进制数据和对象之间进行转化的)
		// TextLineCodecFactory()（读字符串文本数据的）是里面的参数，是系统自带的编解码器，也可以是自定义的
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MyTextLineCodecFactory()));
		try {
			acceptor.bind(new InetSocketAddress(10001));// 4th step,添加端口号
			// 调用完bind之后服务器就启动起来了
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
