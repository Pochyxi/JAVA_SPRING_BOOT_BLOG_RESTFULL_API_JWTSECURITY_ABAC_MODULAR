package com.developez.security.service;

import com.developez.security.DTO.LoginDto;
import com.developez.security.DTO.SignupDto;

public interface AuthService {

    String Login( LoginDto loginDto );
    String signup( SignupDto signupDto );
}
