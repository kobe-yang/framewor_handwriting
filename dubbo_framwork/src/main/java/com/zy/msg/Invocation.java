package com.zy.msg;

import java.io.Serializable;

public class Invocation implements Serializable {
    private String interfaceName;

    private String methodName;

    private Object[] params;

    private Class[] paramsType;

    public Invocation() {
    }

    public Invocation(String interfaceName, String methodName, Object[] params, Class[] paramsType) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.params = params;
        this.paramsType = paramsType;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Class[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class[] paramsType) {
        this.paramsType = paramsType;
    }
}
