package com.lingoway.lingoway.user.service;

import com.lingoway.lingoway.security.JwtTokenProvider;
import com.lingoway.lingoway.user.dto.*;
import com.lingoway.lingoway.user.entity.User;
import com.lingoway.lingoway.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider; // ✅ 추가

    public UserResponse register(UserRegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public TokenResponse  login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.createToken(user.getEmail()); // ✅ JWT 생성

        return new TokenResponse(token); // ✅ TokenResponse 리턴
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public UserResponse getCurrentUserByToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getSubject(token); // ✅ 토큰에서 이메일 추출
        return getCurrentUser(email);
    }

    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return toResponse(user);
    }
}