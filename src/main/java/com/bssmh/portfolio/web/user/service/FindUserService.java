package com.bssmh.portfolio.web.user.service;

import com.bssmh.portfolio.db.entity.user.User;
import com.bssmh.portfolio.web.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindUserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
    
}
