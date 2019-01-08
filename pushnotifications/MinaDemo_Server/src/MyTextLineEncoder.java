import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
/**
 * 比decoder简单，由易到难
 * @author Administrator
 *
 */
public class MyTextLineEncoder implements ProtocolEncoder {

	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * 对发送出去的东西编码一下使它变为字节码(non-Javadoc)
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
	 */
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		String s=null;
		if(message!=null){
			s=(String)message;
		}
		if(s!=null){//转码操作
			CharsetEncoder ce=(CharsetEncoder) session.getAttribute("encoder");
			if(ce==null){
					ce=Charset.defaultCharset().newEncoder();
					session.setAttribute("encoder", ce);
			}
			IoBuffer ioBuffer = IoBuffer.allocate(s.length());
			//让它的长度可以自动扩展
			ioBuffer.setAutoExpand(true);
			//要发送的数据添加进iobuffer里
			ioBuffer.putString(s,ce);
			ioBuffer.flip();//flip:轻弹 轻击 翻转 打开
			out.write(ioBuffer);
		}
	}

}
