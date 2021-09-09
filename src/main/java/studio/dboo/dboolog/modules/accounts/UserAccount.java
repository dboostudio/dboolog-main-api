package studio.dboo.dboolog.modules.accounts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import studio.dboo.dboolog.modules.accounts.entity.Account;

import java.util.Collection;
import java.util.List;

public class UserAccount extends User {
    private Account account;

    public UserAccount(Account account){
        super(account.getUserId(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;

    }
}
