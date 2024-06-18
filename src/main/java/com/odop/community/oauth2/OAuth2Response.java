package com.odop.community.oauth2;

import com.odop.community.enums.Provider;

public interface OAuth2Response {
    Provider getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getProfileImage();
}
