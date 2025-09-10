package com.devlab.api.user.presentation.http;

import com.devlab.api.user.application.component.UserSignUpComponent;
import com.devlab.api.user.presentation.http.dto.UserSignUpDto;
import com.devlab.api.user.presentation.http.request.LoginRequest;
import com.devlab.api.user.presentation.http.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserSignUpComponent userSignUpComponent;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
    return ResponseEntity.ok(userSignUpComponent.create(UserSignUpDto.from(request)));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(userSignUpComponent.login(request.getEmail(), request.getPassword()));
  }
}
