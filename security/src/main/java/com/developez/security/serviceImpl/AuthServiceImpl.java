package com.developez.security.serviceImpl;

import com.developez.security.DTO.LoginDto;
import com.developez.security.DTO.SignupDto;
import com.developez.security.JwtTokenProvider;
import com.developez.security.entity.Permission;
import com.developez.security.entity.User;
import com.developez.security.enumerated.PermissionList;
import com.developez.security.exception.BlogAPIException;
import com.developez.security.repository.PermissionRepository;
import com.developez.security.repository.UserRepository;
import com.developez.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PermissionRepository permissionRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String Login( LoginDto loginDto ) {

        Authentication authentication =
                authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( loginDto.getUsernameOrEmail(),
                        loginDto.getPassword() ) );

        // Impostare l'oggetto di autenticazione
        SecurityContextHolder.getContext().setAuthentication( authentication );

        // Generazione del token JWT
        return jwtTokenProvider.generateToken( authentication );
    }

    @Override
    public String signup( SignupDto signupDto ) {

        // Aggiungiere un controllo per verificare se l'utente esiste già
        if( userRepository.existsByUsername( signupDto.getUsername() ) ) {
            throw new BlogAPIException( HttpStatus.BAD_REQUEST, "Username attualmente esistente" );
        }

        // Aggiungiere un controllo per verificare se l'email esiste già
        if( userRepository.existsByEmail( signupDto.getEmail() ) ) {
            throw new BlogAPIException( HttpStatus.BAD_REQUEST, "Email attualmente esistente" );
        }

        // Creare un nuovo utente
        User user = new User();
        user.setName( signupDto.getName() );
        user.setUsername( signupDto.getUsername() );
        user.setEmail( signupDto.getEmail() );
        // Impostare la password criptata
        user.setPassword( passwordEncoder.encode( signupDto.getPassword() ) );

        // Impostare i permessi dell'utente

        List<Permission> permissionEntities = getPermissionEntity( List.of(
                PermissionList.GET_POST,
                PermissionList.GET_COMMENT) );

        Set<Permission> permissions = new HashSet<>( permissionEntities );

        user.setPermissions(permissions);

        // Salvare l'utente
        userRepository.save(user);

        return "Utente registrato con successo";
    }

    private List<Permission> getPermissionEntity(List<PermissionList> permissions) {
        return permissionRepository.findByNameIn(permissions);



    }
}
