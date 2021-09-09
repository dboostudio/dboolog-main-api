
package studio.dboo.dboolog.modules.accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import studio.dboo.dboolog.modules.accounts.entity.Account;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);
    boolean existsAccountByUserId(String userId);

}
