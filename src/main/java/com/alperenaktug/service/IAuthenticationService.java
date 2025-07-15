package com.alperenaktug.service;

import com.alperenaktug.dto.AuthRequest;
import com.alperenaktug.dto.AuthResponse;
import com.alperenaktug.dto.DtoUser;

public interface IAuthenticationService {

    public DtoUser register(AuthRequest input);

    public AuthResponse authenticate(AuthRequest input);
}
