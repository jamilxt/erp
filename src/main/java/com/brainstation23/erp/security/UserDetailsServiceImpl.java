package com.brainstation23.erp.security;

import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import com.brainstation23.erp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String COULD_NOT_FIND_USER_WITH_EMAIL = "Could not find user with email: ";
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(COULD_NOT_FIND_USER_WITH_EMAIL + email));
        return new UserDetailsImpl(userEntity);
    }
}
