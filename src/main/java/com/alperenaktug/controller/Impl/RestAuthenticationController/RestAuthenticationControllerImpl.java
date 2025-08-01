package com.alperenaktug.controller.Impl.RestAuthenticationController;

import com.alperenaktug.controller.IRestAuthenticationController;
import com.alperenaktug.controller.RestBaseController;
import com.alperenaktug.controller.RootEntity;
import com.alperenaktug.dto.AuthRequest;
import com.alperenaktug.dto.AuthResponse;
import com.alperenaktug.dto.DtoUser;
import com.alperenaktug.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping(path = "/register")
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest input) {
        return ok(authenticationService.register(input));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {
        return ok(authenticationService.authenticate(input));
    }
}
