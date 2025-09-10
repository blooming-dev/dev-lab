package com.devlab.api.user.presentation.http.dto;

import com.devlab.api.user.presentation.http.request.SignUpRequest;
import com.devlab.core.domain.user.entity.UserRole;
import lombok.Builder;

@Builder
public record UserSignUpDto(
    String email,
    String password,
    String name,
    UserRole role
) {

  public static UserSignUpDto from(SignUpRequest request) {
    return UserSignUpDto
        .builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .name(request.getName())
        .role(UserRole.USER)
        .build();
  }
}
