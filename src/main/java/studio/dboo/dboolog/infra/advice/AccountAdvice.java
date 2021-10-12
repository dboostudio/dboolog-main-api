package studio.dboo.dboolog.infra.advice;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import studio.dboo.dboolog.modules.accounts.AccountService;

@Slf4j
@RestControllerAdvice(value = "studio.dboo.dboolog.modules.accounts")
@RequiredArgsConstructor
public class AccountAdvice {

    // 유저이름을 찾지 못한 경우
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity usernameNotFoundExceptionHandler(AuthenticationException e){
        JsonArray result = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        log.error("========== DBOOLOG ERROR LOG START ==========");
        log.error("AuthenticationException occured");
        log.error("Error SimpleName : {} ", e.getClass().getSimpleName());
        log.error("Error Message : {}", e.getMessage());
        log.error("Error StackTrace : {} ", e);
        log.error("========== DBOOLOG ERROR LOG END ============");

        jsonObject.addProperty("field", "userId");
        jsonObject.addProperty("message", e.getMessage() + "의 이메일로 가입된 계정이 없습니다.");
        result.add(jsonObject);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
    }

    // 패스워드가 다른 경우
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity badCredentialsExceptionHandler(AuthenticationException e){
        JsonArray result = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        log.error("========== DBOOLOG ERROR LOG START ==========");
        log.error("AuthenticationException occured");
        log.error("Error SimpleName : {} ", e.getClass().getSimpleName());
        log.error("Error Message : {}", e.getMessage());
        log.error("Error StackTrace : {} ", e);
        log.error("========== DBOOLOG ERROR LOG END ============");

        jsonObject.addProperty("field", "password");
        jsonObject.addProperty("message", "패스워드가 일치하지 않습니다.");
        result.add(jsonObject);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.toString());
    }


}
