package studio.dboo.dboolog.modules.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studio.dboo.dboolog.modules.accounts.entity.Account;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    /** Bean Injection */
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    /** Constant */
    private static final String CANNOT_FIND_USER = "해당 유저명으로 가입된 계정이 없습니다.";
    private static final String ALREADY_EXIST_USER = "이미 해당 유저명으로 가입된 계정이 있습니다.";
    private static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Account> byUserId = accountRepository.findByUserId(userId);
        Account account = byUserId.orElseThrow(()-> new UsernameNotFoundException(userId));
        return new User(account.getUserId(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public Account createAccount(Account account){
        if(accountRepository.existsAccountByUserId(account.getUserId()) == true){
            throw new RuntimeException(ALREADY_EXIST_USER);
        }
        account.encodePassword(passwordEncoder);
        account.setRole("USER");
        return accountRepository.save(account);
    }

    public Account getAccount(String userId){
        Optional<Account> byUserId = accountRepository.findByUserId(userId);
        Account account = byUserId.orElseThrow(()-> new UsernameNotFoundException(userId));
        return account;
    }

    public Account updateAccount(Account account) {
        if(accountRepository.existsAccountByUserId(account.getUserId()) == false){
            throw new RuntimeException(CANNOT_FIND_USER);
        }
        accountRepository.save(account);
        return account;
    }

    public void deleteAccount(Account account) {
        if(accountRepository.existsAccountByUserId(account.getUserId()) == false){
            throw new RuntimeException(CANNOT_FIND_USER);
        }
        accountRepository.delete(account);
    }

    public void login(Account account) {
        // 가입여부, 패스워드 체크
        Optional<Account> byUserId = accountRepository.findByUserId(account.getUserId());
        Account savedAccount = byUserId.orElseThrow(()-> new UsernameNotFoundException(account.getUserId()));

        if(!passwordEncoder.matches(account.getPassword(),savedAccount.getPassword())){
            throw new BadCredentialsException(PASSWORD_NOT_MATCH);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
