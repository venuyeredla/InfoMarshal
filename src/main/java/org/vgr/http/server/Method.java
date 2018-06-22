package org.vgr.http.server;

/**
 * HTTP Request methods, with the ability to decode a <code>String</code> back
 * @author vyeredla
 *
 */
public enum Method {
    GET,PUT,POST,DELETE,HEAD,OPTIONS,TRACE,CONNECT,PATCH,PROPFIND,PROPPATCH,MKCOL,MOVE,COPY,LOCK,UNLOCK;
    public static Method lookup(String method) {
        if (method == null)
            return null;
        try {
            return valueOf(method);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
