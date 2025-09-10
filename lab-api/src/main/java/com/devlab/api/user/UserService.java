package com.devlab.api.user;

import com.devlab.api.security.service.JwtTokenProvider;
import com.devlab.api.user.presentation.http.dto.UserSignUpDto;
import com.devlab.core.domain.user.entity.User;
import com.devlab.core.domain.user.entity.UserRole;
import com.devlab.core.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public User createUser(UserSignUpDto dto) {
    return userRepository.save(
        User.builder()
            .email(dto.email())
            .name(dto.name())
            .roles(List.of(dto.role()))
            .password(passwordEncoder.encode(dto.password()))
            .build());
  }

  public String login(String email, String password) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("Invalid email or password");
    }
    return jwtTokenProvider.createToken(user.getId(), user.getAuthorities());
  }

}
