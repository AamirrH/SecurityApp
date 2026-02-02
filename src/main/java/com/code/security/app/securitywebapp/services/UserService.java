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
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDTO signup(SignUpDTO signUpDTO) {
        UserEntity userEntity = modelMapper.map(signUpDTO,UserEntity.class);
        if(userRepository.existsById(userEntity.getId())){
            throw new BadCredentialsException("Already Registered");
        }
        else{
            // Else saving the entity
            userRepository.save(userEntity);
            return modelMapper.map(userEntity,UserDTO.class);
        }

    }
}
