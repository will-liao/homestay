package com.will.homestay.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class MyRequestWrapper extends HttpServletRequestWrapper {
    private Map<String,String[]> paramsMap;
    private Map<String, String> headerMap;
    public MyRequestWrapper(HttpServletRequest request) {
        super(request);

        this.paramsMap = new HashMap<>();
        this.paramsMap.putAll(request.getParameterMap());

        this.headerMap = new HashMap<>();
    }

    public void addParameter(String name,String value) {
        String[] parameterValues = getParameterValues(name);
            this.paramsMap.put(name, new String[] {value});
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<String>(paramsMap.keySet());
        return vector.elements();
    }
    @Override
    public String getParameter(String name) {
        if(paramsMap.containsKey(name)) {
            return paramsMap.get(name).length > 0 ? paramsMap.get(name)[0] : null;
        }
        return null;
    }
    @Override
    public String[] getParameterValues(String name) {
        return paramsMap.get(name);
    }
    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(paramsMap);
    }
    @Override
    public String getQueryString() {
        StringBuilder sb = new StringBuilder();
        if(paramsMap != null) {
            for(Map.Entry<String, String[]> en : paramsMap.entrySet()) {
                sb.append(en.getKey()).append("=").append(en.getValue()[0]).append("&");
            }
        }
        int len = sb.length();
        if(len > 0) {
            sb.deleteCharAt(len-1);
        }
        return sb.toString();
    }


    public void addHeader(String name, String value){
        this.headerMap.put(name, value);
    }
    @Override
    public String getHeader(String name) {
        String headerValue = headerMap.get(name);
        if (headerValue != null){
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        for (String name : headerMap.keySet()) {
            names.add(name);
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)) {
            values.add(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }
}
