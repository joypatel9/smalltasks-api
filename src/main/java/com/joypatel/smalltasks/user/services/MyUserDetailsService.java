package com.joypatel.smalltasks.user.services;

import com.joypatel.smalltasks.common.security.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

//        User user = userRepository.findByMobile(mobile);
//        if (mobile == null)
//            throw new UsernameNotFoundException("User " + mobile + " not found");
//
//        return MyUserDetails.builder()
//                        .username(user.getMobile())
//                        .password(user.getPassword())
//                        .authorities(List.of())
//                .build();

        return userRepository.findByMobile(mobile)
                .map(user -> MyUserDetails.builder()
                        .username(user.getMobile())
                        .password(user.getPassword())
                        .authorities(List.of()).build())
                .orElseThrow(() -> new UsernameNotFoundException("User " + mobile + " not found"));
    }
}
