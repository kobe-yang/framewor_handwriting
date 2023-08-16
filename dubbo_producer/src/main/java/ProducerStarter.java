import com.zy.api.HelloService;
import com.zy.impl.HelloServiceImpl;
import com.zy.protocol.Protocol;
import com.zy.protocol.ProtocolFactory;
import com.zy.protocol.URL;

public class ProducerStarter {
    public static void main(String[] args) {
        String protocolStr = System.getProperty("protocol");
        if(null == protocolStr || "".equals(protocolStr)){
            protocolStr = "dubbo";
        }
        Protocol protocol = ProtocolFactory.getProtocol(protocolStr);
        URL url = new URL(protocolStr,"localhost",8080, HelloService.class.getName());
        //注册启动服务
        protocol.export(url,HelloServiceImpl.class);
    }
}
