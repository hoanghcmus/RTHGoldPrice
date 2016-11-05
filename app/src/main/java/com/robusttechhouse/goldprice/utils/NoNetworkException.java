package com.robusttechhouse.goldprice.utils;

import java.io.IOException;

/**
 * Exception for network down
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class NoNetworkException extends IOException {
    public NoNetworkException() {
        super("No network exception.");
    }
}