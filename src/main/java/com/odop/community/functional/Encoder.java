package com.odop.community.functional;

@FunctionalInterface
public interface Encoder {
    String encode(String password);
}
