package com.robusttechhouse.goldprice.common;

/**
 * Application's constants
 *
 * @author Nguyen Ngoc Hoang (www.hoangvnit.com)
 */
public class Setting {
    public static final String REST_ROOT_URL = "https://rth-recruitment.herokuapp.com/api";

    public static final String GSON_DATE_FORMAT = "dd/MM/yyyy";
    public static final String REQUEST_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String REQUEST_HEADER_CONTENT_TYPE_JSON_VALUE = "application/json";
    public static final String REQUEST_HEADER_TOKEN = "X-App-Token";
    public static final String REQUEST_HEADER_TOKEN_VALUE = "76524a53ee60602ac3528f38";

    public static final int OK_HTTP_CLIENT_CACHE_SIZE = 10 * 1024 * 1024;
    public static final int OK_HTTP_CLIENT_TIMEOUT = 30;
    public static final String OK_HTTP_CLIENT_CACHE_FILE_NAME = "responses";
}
