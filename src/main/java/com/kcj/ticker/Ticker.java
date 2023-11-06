package com.kcj.ticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker {
    private String ask;
    private String bid;
    public Ticker() {
    }

    public Ticker(String ask, String bid) {
        this.ask = ask;
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "ask='" + ask + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}
