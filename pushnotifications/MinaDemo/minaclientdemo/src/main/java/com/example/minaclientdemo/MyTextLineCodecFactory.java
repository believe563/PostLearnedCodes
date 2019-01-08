package com.example.minaclientdemo;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MyTextLineCodecFactory implements ProtocolCodecFactory {
    private MyTextLineEncoder mEncoder;
    private MyTextLineDecoder mDecoder;

    public MyTextLineCodecFactory() {
        mEncoder=new MyTextLineEncoder();
        mDecoder=new MyTextLineDecoder();
    }
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return mEncoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return mDecoder;
    }
}
