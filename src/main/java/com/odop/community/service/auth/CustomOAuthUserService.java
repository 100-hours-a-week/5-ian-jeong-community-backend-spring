package com.odop.community.service.auth;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.dto.auth.CustomOAuth2User;
import com.odop.community.domain.dto.auth.GoogleResponse;
import com.odop.community.domain.dto.auth.NaverResponse;
import com.odop.community.domain.dto.auth.OAuth2Response;
import com.odop.community.domain.entity.User;
import com.odop.community.enums.OAuthProvider;
import com.odop.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuthProvider oAuthProvider = OAuthProvider.find(registrationId);
        OAuth2Response oAuth2Response = null;

        switch (oAuthProvider) {
            case GOOGLE:
                oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
                break;
            case NAVER:
                oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
                break;
//            case KAKAO:
//                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }

        String username = oAuth2Response.getProvider().getName() + " " + oAuth2Response.getProviderId();
        Optional<User> existData = userRepository.findByNickname(username);

        if (existData.isEmpty()) { // 없다면 OAuth 유저 새로 등록
            User user = new User();
            user.setNickname(username);
            user.setEmail(oAuth2Response.getEmail());

            userRepository.insert(user);
            return new CustomOAuth2User(UserDTO.convertToDTO(userRepository.findByNickname(username).get()));

        } else { // 있다면
            userRepository.update(existData.get()); // 업데이트
            return new CustomOAuth2User(UserDTO.convertToDTO(existData.get()));
        }
    }
}
