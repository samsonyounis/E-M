package com.example.emapp.securityConfig;

import com.example.emapp.models.Users;
import com.example.emapp.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class userDetailsService implements UserDetailsService{
    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> Optionaluser = usersRepository.findUserByEmail(username);
        if (Optionaluser.isEmpty()){
            log.info("Inavlid username");
            throw new UsernameNotFoundException(username);
        }
       Users user = Optionaluser.get();
        return new  userDetailsClass(user);
    }

}
