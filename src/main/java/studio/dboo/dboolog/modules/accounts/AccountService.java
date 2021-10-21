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

    //TODO - 에러 메세지들을 컨트롤러 단에서 처리하지 않고, custom-exception을 만들어 던지도록 수정
    //TODO - custom-exception들은 advice로 처리하고, advice패키지내에 enum을 만들어서 에러메세지 관리할 수 있도록 수정
    /** Constant */
    private static final String CANNOT_FIND_USER = "의 이메일로 가입된 계정이 없습니다.";
    private static final String ALREADY_EXIST_USER = "이미 가입한 이메일 계정입니다.";
    private static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Account> byUserId = accountRepository.findByUserId(userId);
        Account account = byUserId.orElseThrow(()-> new UsernameNotFoundException(userId));
        return new User(account.getUserId(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public Account createAccount(Account account) {
        if (accountRepository.existsAccountByUserId(account.getUserId()) == true) {
            throw new RuntimeException(ALREADY_EXIST_USER);
        }
        account.encodePassword(passwordEncoder);
        account.setRole("USER");
        return accountRepository.save(account);
    }

    public Account createAdminAccount(Account account) {
        if (accountRepository.existsAccountByUserId(account.getUserId()) == true) {
            throw new RuntimeException(ALREADY_EXIST_USER);
        }
        account.encodePassword(passwordEncoder);
        account.setRole("ADMIN");
        return accountRepository.save(account);
    }

    public Account getAccount(String userId){
        Optional<Account> byUserId = accountRepository.findByUserId(userId);
        Account account = byUserId.orElseThrow(()-> new UsernameNotFoundException(userId));
        return account;
    }

    public Account updateAccount(Account account) {
        if(accountRepository.existsAccountByUserId(account.getUserId()) == false){
            throw new RuntimeException(account.getUserId() + CANNOT_FIND_USER);
        }
        accountRepository.save(account);
        return account;
    }

    public void deleteAccount(Account account) {
        if(accountRepository.existsAccountByUserId(account.getUserId()) == false){
            throw new RuntimeException(account.getUserId() + CANNOT_FIND_USER);
        }
        accountRepository.delete(account);
    }

    public String loginAndGenerateToken(Account account) {
        // 가입여부, 패스워드 체크
        Optional<Account> byUsername = accountRepository.findByUserId(account.getUserId());
        Account savedAccount = byUsername.orElseThrow(()-> new UsernameNotFoundException(account.getUserId()));

        if(!passwordEncoder.matches(account.getPassword(),savedAccount.getPassword())){
            throw new BadCredentialsException(PASSWORD_NOT_MATCH);
        }

        // 아이디 패스워드 일치 시, authentication등록
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(account.getUserId(), account.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 등록한 authentication을 기반으로 token발급
        Optional<String> optionalJwt = jwtTokenUtil.generateJwtToken(authentication);
        String token = optionalJwt.orElseThrow(() -> new RuntimeException());

        return token;
    }
}
