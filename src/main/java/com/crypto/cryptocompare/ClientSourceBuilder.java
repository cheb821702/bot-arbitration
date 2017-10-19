package com.crypto.cryptocompare;

import java.util.*;

public class ClientSourceBuilder {

    private Set<Class> imports;
    private List<Method> methods;

    public ClientSourceBuilder() {
        this.imports = new HashSet<Class>();
        this.methods = new ArrayList<Method>();
    }

    public void addMethod(String callUrl, String signature, Map<String,Class> parameters) {

    }

    public String buildToString() {
        return null;
    }

    private class Method {
        private String baseUrl;
        private String signature;
        private Map<String,Class> parameters;
    }
}
