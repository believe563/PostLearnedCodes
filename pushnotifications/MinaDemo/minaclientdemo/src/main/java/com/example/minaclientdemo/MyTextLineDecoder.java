package com.example.minaclientdemo;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MyTextLineDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        int startPos=ioBuffer.position();
        while (ioBuffer.hasRemaining()) {
            byte b=ioBuffer.get();
            if (b == '\n') {
                int currentPos=ioBuffer.position();
                int limit=ioBuffer.limit();//记录当前走过的长度
                ioBuffer.position(startPos);//定位到起点的位置
                ioBuffer.limit(currentPos);//定位到终点的位置
                IoBuffer io=ioBuffer.slice();
                byte[] bb = new byte[ioBuffer.limit()];
                io.get(bb);
                String s = new String(bb);
                protocolDecoderOutput.write(s);
                startPos=currentPos;
                ioBuffer.limit(limit);
                return true;
            }
        }
        ioBuffer.position(startPos);
        return false;
    }
}
