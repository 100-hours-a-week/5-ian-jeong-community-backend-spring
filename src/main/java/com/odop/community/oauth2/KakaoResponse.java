package com.odop.community.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odop.community.enums.Provider;

import java.util.Map;

import static com.odop.community.enums.Provider.*;

public class KakaoResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        System.out.println(attribute.get("kakao_account"));
        this.attribute = (Map<String, Object>) attribute.get("kakao_account");
        this.attribute.put("id", attribute.get("id"));

    }

    @Override
    public Provider getProvider() {
        return KAKAO;
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
        return ((Map<String, Object>)attribute.get("profile")).get("profile_image_url").toString();
    }
}
