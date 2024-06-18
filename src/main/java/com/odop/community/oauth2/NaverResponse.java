package com.odop.community.oauth2;

import com.odop.community.enums.Provider;

import java.util.Map;

import static com.odop.community.enums.Provider.*;

public class NaverResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public Provider getProvider() {
        return NAVER;
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("email").toString();
    }

    @Override
    public String getProfileImage() {
        return attribute.get("profile_image").toString();
    }
}
