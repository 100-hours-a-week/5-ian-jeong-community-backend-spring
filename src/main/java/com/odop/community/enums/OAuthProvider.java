package com.odop.community.enums;

public enum OAuthProvider {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String name;

    OAuthProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OAuthProvider find(String text) {
        if (text != null) {
            for (OAuthProvider provider : OAuthProvider.values()) {
                if (text.equalsIgnoreCase(provider.getName())) {
                    return provider;
                }
            }
        }

        return OAuthProvider.GOOGLE;
    }
}
