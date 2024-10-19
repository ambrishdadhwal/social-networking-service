package com.social.network.security;

public interface RequestContextHolder {
    void setContext(RequestContext customContext);

    RequestContext getContext();

    void removeContext();
}
