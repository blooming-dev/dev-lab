package com.devlab.api.user.application.component;

import com.devlab.api.user.UserService;
import com.devlab.api.user.presentation.http.dto.UserSignUpDto;
import com.devlab.api.user.presentation.http.response.TokenResponse;
import com.devlab.api.user.presentation.http.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSignUpComponent {

  private final UserService userService;

  public UserResponse create(UserSignUpDto dto) {
    var user = userService.createUser(dto);
    return UserResponse.of(user);
  }

  public TokenResponse login(String email, String password) {
    String token = userService.login(email, password);
    return TokenResponse.builder().accessToken(token).build();
  }
}
