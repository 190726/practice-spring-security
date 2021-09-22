package com.sk.user.service;

import com.sk.user.domain.User;
import com.sk.user.domain.UserRepository;
import com.sk.user.dto.UserSaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Long register(UserSaveRequestDto dto) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        dto.setPassword(passEncoder.encode(dto.getPassword()));
        return userRepository.save(dto.toEntity()).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + "- 해당 아이디는 찾을 수 없습니다. "));
    }
}
