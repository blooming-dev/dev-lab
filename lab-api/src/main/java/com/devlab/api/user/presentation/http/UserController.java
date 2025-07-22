package com.devlab.api.user.presentation.http;

import com.devlab.api.user.UserService;
import com.devlab.api.user.presentation.http.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(
      @PathVariable Long id
  ) {

    var user = userService.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    return ResponseEntity.ok(UserResponse.of(user));
  }
}
