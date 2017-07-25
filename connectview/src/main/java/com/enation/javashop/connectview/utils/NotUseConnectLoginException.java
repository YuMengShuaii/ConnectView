package com.enation.javashop.connectview.utils;

/**
 * 自定义异常，用来处理使用者没有使用任何第三方登录。
 */
public class NotUseConnectLoginException extends Error {
    public NotUseConnectLoginException(String message) {
        super(message);
    }
}
