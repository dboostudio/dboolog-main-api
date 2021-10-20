package studio.dboo.dboolog.applicationrunners;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import studio.dboo.dboolog.modules.accounts.AccountService;
import studio.dboo.dboolog.modules.accounts.entity.Account;

@Component
@RequiredArgsConstructor
public class AccountRunner implements ApplicationRunner {

    private final AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 개인 계정 가입
        Account personalAccount = Account.builder()
                .userId("dboo.studio@gmail.com")
                .password("eoghks12")
                .role("USER")
                .cellPhone("010-9134-6572")
                .sex("M")
                .point(99999999L)
                .build();
        accountService.createAccount(personalAccount);

        // 관리자 계정 가입
        Account adminAccount = Account.builder()
                .userId("dboo.admin@gmail.com")
                .password("eoghks12")
                .role("ADMIN")
                .cellPhone("010-9999-9999")
                .sex("ADMIN")
                .point(99999999L)
                .build();
        accountService.createAdminAccount(adminAccount);

    }
}
