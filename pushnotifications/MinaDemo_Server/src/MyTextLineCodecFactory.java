import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MyTextLineCodecFactory implements ProtocolCodecFactory{

	private MyTextLineEncoder mEncoder;
	private MyTextLineDecoder mDecoder;
	private MyTextLineCumulativeDecoder mCumulativeDecoder;
	
	public MyTextLineCodecFactory() {
		mEncoder=new MyTextLineEncoder();
		mDecoder=new MyTextLineDecoder();
		mCumulativeDecoder=new MyTextLineCumulativeDecoder();
	}
	
	@Override   //解密
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return mCumulativeDecoder;
	}

	@Override   //加密
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		return mEncoder;
	}

}
