package com.example.minaclientdemo;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MyTextLineEncoder implements ProtocolEncoder {
    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        String s=null;
        if (o != null) {
            s = (String) o;
        }

        CharsetEncoder ce = (CharsetEncoder) ioSession.getAttribute("encoder");
        if (ce == null) {
            ce = Charset.defaultCharset().newEncoder();
            ioSession.setAttribute("encoder", ce);
        }
        IoBuffer ioBuffer = IoBuffer.allocate(s.length());
        ioBuffer.setAutoExpand(true);
        ioBuffer.putString(s, ce);
        ioBuffer.flip();
        protocolEncoderOutput.write(ioBuffer);
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
