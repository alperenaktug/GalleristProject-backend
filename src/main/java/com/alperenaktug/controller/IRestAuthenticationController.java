package com.alperenaktug.controller;

import com.alperenaktug.dto.AuthReguest;
import com.alperenaktug.dto.DtoUser;

public interface IRestAuthenticationController {

    public RootEntity<DtoUser> register(AuthReguest input);
}
