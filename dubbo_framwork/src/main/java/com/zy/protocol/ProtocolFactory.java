package com.zy.protocol;

import com.zy.protocol.dubbo.DubboProtocol;

public class ProtocolFactory {
    public static Protocol getProtocol(String protocolName){
        switch (protocolName){
            case "dubbo" :
                return new DubboProtocol();
            default:
                return new DubboProtocol();
        }
    }
}
