package com.pragmatists.cdc.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class BooksProviderConfiguration {
    private int port = 8080;
    private String host = "localhost";

    public BooksProviderConfiguration setPort(int port) {
        this.port = port;
        return this;
    }

    public int getPort() {
        return port;
    }

    public BooksProviderConfiguration setHost(String host) {
        this.host = host;
        return this;
    }

    public String getHost() {
        return host;
    }
}
