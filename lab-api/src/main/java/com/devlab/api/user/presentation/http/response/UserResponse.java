package com.devlab.api.user.presentation.http.response;

import com.devlab.core.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {
  private Long id;
  private String name;

  public static UserResponse of(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .build();
  }
}
