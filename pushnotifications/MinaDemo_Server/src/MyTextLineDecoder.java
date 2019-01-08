import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MyTextLineDecoder implements ProtocolDecoder {

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		
		int startPosition=in.position();//记录一开始读取的位置
		while(in.hasRemaining()){//判断当前iobuffer里是否还有字节 可以读取
			byte b=in.get();//每次读取一个字节
			if(b=='\n'){//这样处理的话只有当发送来的数据最后有\n时messageReceived才能收到这条消息
				//为了解决这一问题，写一个继承于CumulativeProtocolDecoder的decoder类
				int currentPos=in.position();//记录当前位置
				int limit=in.limit();//记录走过的总长度
//				System.out.println("limit:"+limit);
				//把从start位置到\n的位置截取出来
				in.position(startPosition);//确定截取起点
				in.limit(currentPos);//确定截取终点
				IoBuffer buf=in.slice();//截取
				byte[] dest=new byte[buf.limit()];
				buf.get(dest);//将字节转换成字符，把buffer赋值到dest中
				String str=new String(dest);
				in.position(currentPos);//将position做还原操作
				in.limit(limit);
				out.write(str);//把字符串写出，表示将数据发送出去
			}
		}
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
		// TODO Auto-generated method stub

	}

}
