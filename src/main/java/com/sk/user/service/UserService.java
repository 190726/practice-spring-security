package com.sk.user.service;

import com.sk.user.domain.User;
import com.sk.user.domain.UserRepository;
import com.sk.user.dto.UserDetailDto;
import com.sk.user.dto.UserResponseDto;
import com.sk.user.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public void register(UserSaveRequestDto dto) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        dto.setPassword(passEncoder.encode(dto.getPassword()));
        userRepository.save(dto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	log.debug("\r\n ##[4]##UserService's loadUserByUsername() called. user id is :{}", userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId + "- 해당 아이디는 찾을 수 없습니다. "));
        return new UserDetailDto(user);
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
    }
}
