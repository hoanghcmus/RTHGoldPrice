package com.robusttechhouse.goldprice.common;

/**
 * This {@link Enum} class identify for all fragment of the application
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public enum FRAGMENT_ID {
    GOLD_PRICE_LIST_FRAGMENT("GOLD_PRICE_LIST_FRAGMENT");

    private String key;

    FRAGMENT_ID(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
