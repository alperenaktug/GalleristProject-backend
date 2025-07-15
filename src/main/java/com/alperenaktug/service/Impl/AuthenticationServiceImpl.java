package com.alperenaktug.service.Impl;

import com.alperenaktug.dto.AuthRequest;
import com.alperenaktug.dto.AuthResponse;
import com.alperenaktug.dto.DtoUser;
import com.alperenaktug.exception.BaseException;
import com.alperenaktug.exception.ErrorMessage;
import com.alperenaktug.exception.MessageType;
import com.alperenaktug.jwt.JwtService;
import com.alperenaktug.model.RefreshToken;
import com.alperenaktug.model.User;
import com.alperenaktug.repository.RefreshTokenRepository;
import com.alperenaktug.repository.UserRepository;
import com.alperenaktug.service.IAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private User createUser(AuthRequest input) {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public DtoUser register(AuthRequest input) {

        DtoUser dtoUser = new DtoUser();
        User savedUser = userRepository.save(createUser(input));

        BeanUtils.copyProperties(savedUser, dtoUser);

        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {
       try{

           UsernamePasswordAuthenticationToken authenticationToken =
                   new UsernamePasswordAuthenticationToken(input.getUsername(),
                                                           input.getPassword());
           authenticationProvider.authenticate(authenticationToken);

           Optional<User> opUser = userRepository.findByUsername(input.getUsername());
           String accessToken = jwtService.generateToken(opUser.get());

        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(opUser.get()));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
       } catch (Exception e) {
           throw new BaseException(new ErrorMessage( e.getMessage(), MessageType.USERNAME_OR_PASSWORD_INVALID));
       }

    }
}
