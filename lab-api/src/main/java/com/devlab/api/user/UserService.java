package com.devlab.api.user;

import com.devlab.core.domain.user.entity.User;
import com.devlab.core.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }
}
