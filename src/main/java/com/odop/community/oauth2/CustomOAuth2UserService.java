package com.odop.community.oauth2;

import com.odop.community.domain.dto.UserDTO;
import com.odop.community.domain.entity.User;
import com.odop.community.enums.Provider;
import com.odop.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private static final String USER_IMAGE_DIRECTORY = "src/main/resources/images/user/";

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Provider provider = Provider.find(registrationId);

        OAuth2Response oAuth2Response = switch (provider) {
            case GOOGLE -> new GoogleResponse(oAuth2User.getAttributes());
            case NAVER -> new NaverResponse(oAuth2User.getAttributes());
            case KAKAO -> new KakaoResponse(oAuth2User.getAttributes());
            default -> null;
        };

        String username = oAuth2Response.getEmail();

        try {
            User existData = userRepository.findByNickname(username);
            userRepository.update(existData);
            return new CustomOAuth2User(UserDTO.convertToDTO(existData));

        } catch (EmptyResultDataAccessException e) {
            UserDTO userDTO = UserDTO.builder()
                                    .email(oAuth2Response.getEmail())
                                    .nickname(username)
                                    .provider(provider.getName())
                                    .image(oAuth2Response.getProfileImage())
                                    .build();


            if (!userDTO.getImage().isEmpty()) {
                String imageName = UUID.randomUUID().toString();
                Path imagePath = Paths.get(USER_IMAGE_DIRECTORY + imageName);

                try (OutputStream outputStream = new FileOutputStream(imagePath.toFile())) {
                    FileCopyUtils.copy(userDTO.getImage().getBytes(), outputStream);
                    userDTO.setImage(imageName);
                } catch (IOException error) {
                    try {
                        throw new IOException(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            userRepository.insert(userDTO.convertToEntity());
            return new CustomOAuth2User(UserDTO.convertToDTO(userRepository.findByNickname(username)));
        }
    }
}
