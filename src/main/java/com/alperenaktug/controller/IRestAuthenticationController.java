package com.alperenaktug.controller;

import com.alperenaktug.dto.AuthRequest;
import com.alperenaktug.dto.AuthResponse;
import com.alperenaktug.dto.DtoUser;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthRequest input);

public RootEntity<AuthResponse> authenticate(AuthRequest input);
}
