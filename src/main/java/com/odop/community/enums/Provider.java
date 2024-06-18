package com.odop.community.enums;

public enum Provider {
    LOCAL("local"),
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String name;

    Provider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Provider find(String text) {
        if (text != null) {
            for (Provider provider : Provider.values()) {
                if (text.equalsIgnoreCase(provider.getName())) {
                    return provider;
                }
            }
        }

        return Provider.LOCAL;
    }
}
