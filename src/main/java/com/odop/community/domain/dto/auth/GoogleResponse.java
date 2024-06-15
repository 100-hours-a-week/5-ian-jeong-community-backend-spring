package com.odop.community.domain.dto.auth;

import com.odop.community.enums.OAuthProvider;

import java.util.Map;

import static com.odop.community.enums.OAuthProvider.GOOGLE;

public class GoogleResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }


    @Override
    public OAuthProvider getProvider() {
        return GOOGLE;
    }

    @Override
    public String getProviderId() {

        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {

        return attribute.get("email").toString();
    }

    @Override
    public String getName() {

        return attribute.get("name").toString();
    }

    @Override
    public String getProfileImage() {
        return null;
    }
}
