import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * @author Administrator
 *继承于CumulativeProtocolDecoder，可以实现把前面没有\n的数据保留着，和后面带\n的数据拼装成一条完整的数据来输出
 */
public class MyTextLineCumulativeDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		int startPosition = in.position();// 记录一开始读取的位置
		while (in.hasRemaining()) {
			byte b = in.get();
			if (b == '\n') {
				int currentPos = in.position();// 记录当前位置
				int limit = in.limit();// 记录走过的总长度
				in.position(startPosition);// 确定截取起点
				in.limit(currentPos);// 确定截取长度
				IoBuffer buf = in.slice();// 截取
				byte[] dest = new byte[buf.limit()];
				buf.get(dest);// 将字节转换成字符
				String str = new String(dest);
				in.position(currentPos);// 将position做还原操作
				in.limit(limit);
				out.write(str);// 把字符串写出，表示将数据发送出去
				return true;
			}
		}
		in.position(startPosition);//把开始位置定向到前面的startPosition，下次读取的时候会连着前面没有读的一起读出来
		return false;//没有读取完成，希望等待下一次继续读取时用return false
	}
}
