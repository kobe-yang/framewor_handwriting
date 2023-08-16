package com.zy.register;

import java.util.HashMap;
import java.util.Map;

public class LocalRegister {
    public static Map<String,Class> serviceClassMap = new HashMap<String, Class>(16);

    public static Class get(String interfaceName) {
        return serviceClassMap.get(interfaceName);
    }

    public static void register(String key,Class value) {
        serviceClassMap.put(key,value);
    }

}
