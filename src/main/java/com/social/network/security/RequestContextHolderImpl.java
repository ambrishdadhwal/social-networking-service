package com.social.network.security;

import org.springframework.stereotype.Component;

@Component
public class RequestContextHolderImpl implements RequestContextHolder {
    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();

    public void setContext(RequestContext customContext) {
        contextHolder.set(customContext);
    }

    public RequestContext getContext() {
        return contextHolder.get();
    }

    public void removeContext() {
        contextHolder.remove();
    }
}
