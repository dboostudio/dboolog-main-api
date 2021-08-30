package studio.dboo.dboolog.modules.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studio.dboo.dboolog.infra.jwt.JwtTokenUtil;
import studio.dboo.dboolog.modules.accounts.entity.Account;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    /** Bean Injection */
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenUtil jwtTokenUtil;

    /** Constant */
    private static final String CANNOT_FIND_USER = "해당 유저명으로 가입된 계정이 없습니다.";
    private static final String ALREADY_EXIST_USER = "이미 해당 유저명으로 가입된 계정이 있습니다.";
    private static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        Account account = byUsername.orElseThrow(()-> new UsernameNotFoundException(username));
        return new User(account.getUsername(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public Account createAccount(Account account){
        if(accountRepository.existsAccountByUsername(account.getUsername()) == true){
            throw new RuntimeException(ALREADY_EXIST_USER);
        }
        account.encodePassword(passwordEncoder);
        account.setRole("USER");
        return accountRepository.save(account);
    }

    public Account getAccount(String username){
        Optional<Account> byUsername = accountRepository.findByUsername(username);
        Account account = byUsername.orElseThrow(()-> new UsernameNotFoundException(username));
        return account;
    }

    public Account updateAccount(Account account) {
        if(accountRepository.existsAccountByUsername(account.getUsername()) == false){
            throw new RuntimeException(CANNOT_FIND_USER);
        }
        accountRepository.save(account);
        return account;
    }

    public void deleteAccount(Account account) {
        if(accountRepository.existsAccountByUsername(account.getUsername()) == false){
            throw new RuntimeException(CANNOT_FIND_USER);
        }
        accountRepository.delete(account);
    }

    public String loginAndGenerateToken(Account account) {
        // 가입여부, 패스워드 체크
        Optional<Account> byUsername = accountRepository.findByUsername(account.getUsername());
        Account savedAccount = byUsername.orElseThrow(()-> new UsernameNotFoundException(account.getUsername()));

        if(!passwordEncoder.matches(account.getPassword(),savedAccount.getPassword())){
            throw new BadCredentialsException(PASSWORD_NOT_MATCH);
        }

        // 아이디 패스워드 일치 시, authentication등록
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 등록한 authentication을 기반으로 token발급
        Optional<String> optionalJwt = jwtTokenUtil.generateJwtToken(authentication);
        String token = optionalJwt.orElseThrow(() -> new RuntimeException());

        return token;
    }
}
