package studio.dboo.dboolog.modules.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import studio.dboo.dboolog.modules.accounts.entity.Account;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired ObjectMapper objectMapper;

    @BeforeEach
    public void createTestAccounts(){
        for(int i = 0; i<50; i++){
            createUser(
                    "test"+i,
                    "1234",
                    "test"+i+"@test.com"
            );
        }
    }

    @DisplayName("테스트환경 테스트")
    @Test
    public void testEnvironment(){
        System.out.println("성공!");
    }

    @DisplayName("계정조회_성공")
    @Test
    public void getAccount_success() throws Exception {
        // TODO - 로그인한 계정이 본인 계정에 한해서만 조회할수 있도록 변경
        String userId = "test1";
        mockMvc.perform(get("/api/account").param("userId", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("계정조회_실패")
    @Test
    public void getAccount_fail() throws Exception {
        String userId = "@!#$%%%";
        mockMvc.perform(get("/api/account").param("userId", userId))
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @DisplayName("계정생성_성공")
    @Test
    public void createAccount_success() throws Exception {
        Account account = Account.builder()
                .userId("test")
                .password("1234")
                .build();
        mockMvc.perform(post("/api/account")
                .content("{\"userId\":\"dboo.studio\"" +
                        ",\"email\":\"dboo.studio@gmail.com\"" +
                        ",\"password\":\"1234\",\"groups\":[]}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
        Optional<Account> byUserId = accountRepository.findByUserId(account.getUserId());
    }

    @DisplayName("계정생성_실패(중복이름)")
    @Test
    public void createAccount_fail() throws Exception {
        Account account  = Account.builder()
                .userId("test")
                .password("1234")
                .build();
        String param = objectMapper.writeValueAsString(account);
        mockMvc.perform(post("/api/account")
                .content(param)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @DisplayName("폼로그인_성공")
    @Test
    public void formLogin_success() throws Exception {
        String password = "1234";
        Account account = Account.builder()
                .userId("test")
                .password(password)
                .build();

        accountService.createAccount(account);

        mockMvc.perform(formLogin().user(account.getUserId()).password(password))
                .andDo(print())
                .andExpect(authenticated());
    }

    @DisplayName("폼로그인_실패_사용자미존재")
    @Test
    public void formLogin_fail_user_not_found() throws Exception {
        String password = "1234";
        Account account = Account.builder()
                .userId("test")
                .password(password)
                .build();

        accountService.createAccount(account);

        mockMvc.perform(formLogin().user(account.getUserId() + "___").password(password))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @DisplayName("폼로그인_실패_패스워드불일치")
    @Test
    public void formLogin_fail_wrong_password() throws Exception {
        String password = "1234";
        Account account = Account.builder()
                .userId("test")
                .password(password)
                .build();

        accountService.createAccount(account);

        mockMvc.perform(formLogin().user(account.getUserId()).password(password + "1"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    @DisplayName("로그인_성공")
    @Test
    public void login_success() throws Exception {
        Account account = Account.builder()
                .userId("dboo")
                .password("1234")
                .role("USER")
                .build();

        accountService.createAccount(account);

        mockMvc.perform(post("/api/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"dboo\",\"password\":\"1234\"}")
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인_실패_사용자미존재")
    @Test
    public void login_fail_user_not_found() throws Exception {
        Account account = Account.builder()
                .userId("test")
                .password("1234")
                .role("USER")
                .build();

        accountService.createAccount(account);

        mockMvc.perform(post("/api/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"test1\",\"password\":\"1234\"}")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());;
    }

    @DisplayName("로그인_실패_패스워드불일치")
    @Test
    public void login_fail_wrong_password() throws Exception {
        String userId = "test";
        String password = "123";
        String email = "test@gmail.com";
        this.createUser(userId, password, email);

        mockMvc.perform(formLogin().user(userId).password(password+"0"))
                .andDo(print())
                .andExpect(unauthenticated());
    }

    private Account createUser(String userId, String password, String email) {
        Account account = Account.builder()
                .userId(userId)
                .password(password)
                .role("USER")
                .build();
        accountService.createAccount(account);
        return account;
    }
}