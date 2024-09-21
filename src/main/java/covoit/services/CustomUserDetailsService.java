package covoit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import covoit.entities.CustomUserDetails;
import covoit.entities.UserAccount;
import covoit.repository.UserAccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUserName(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(userAccount);
    }
}