package com.odop.community.domain.dto.auth;

import com.odop.community.enums.OAuthProvider;

public interface OAuth2Response {
    OAuthProvider getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getProfileImage();
}
