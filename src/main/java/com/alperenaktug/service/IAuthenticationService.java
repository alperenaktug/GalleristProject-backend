package com.alperenaktug.service;

import com.alperenaktug.dto.AuthReguest;
import com.alperenaktug.dto.DtoUser;

public interface IAuthenticationService {

    public DtoUser register(AuthReguest input);
}
