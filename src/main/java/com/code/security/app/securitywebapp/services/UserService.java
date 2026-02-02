package com.code.security.app.securitywebapp.services;


import com.code.security.app.securitywebapp.dtos.SignUpDTO;
import com.code.security.app.securitywebapp.dtos.UserDTO;
import com.code.security.app.securitywebapp.entities.UserEntity;
import com.code.security.app.securitywebapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return user; // ONLY if UserEntity implements UserDetails
    }

    public UserDTO signup(SignUpDTO signUpDTO) {
        UserEntity userEntity = modelMapper.map(signUpDTO,UserEntity.class);
        /*Checking for existence of user with username because if done with ID,
        which is generated after the user has been persisted, so the id becomes null for a
        user which hasn't persisted yet.
         */
        if (userRepository.existsByUsername(userEntity.getUsername())) {
            throw new BadCredentialsException("Already Registered");
        }
        else{
            // Else encoding the password and then saving the entity.
            userEntity.setPassword(bCryptPasswordEncoder.encode(signUpDTO.getPassword()));
            userRepository.save(userEntity);
            return modelMapper.map(userEntity,UserDTO.class);
        }

    }
}
