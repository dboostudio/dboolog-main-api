package studio.dboo.dboolog.modules.accounts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.intellij.lang.annotations.RegExp;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account{

    final static String ENTER_USER_ID = "아이디를 입력해 주세요.";
    final static String CHECK_USER_ID = "아이디는 이메일 형식이어야 합니다. (e.g. something@email.com)";
    final static String ENTER_PASSWORD = "비밀번호를 입력해 주세요.";
    final static String CELLPHONE_FORM_NOT_CORRECT = "010-xxxx-xxxx 의 형식에 맞춰서 입력해주세요.";
    final static String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Id @GeneratedValue
    private Long id;

    /** Login Info **/
    @Column(unique = true)
    @Pattern(regexp = EMAIL_REGEXP, message = CHECK_USER_ID) @NotBlank(message = ENTER_USER_ID)
    private String userId;                //사용자 아이디(이메일)

    @NotNull(message = ENTER_PASSWORD) @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonIgnore
    private String role; //권한 (ADMIN, USER)

    /** Private Info **/
    @Nullable @Pattern(regexp = "^\\\\d{2,3}-\\\\d{3,4}-\\\\d{4}$", message = CELLPHONE_FORM_NOT_CORRECT)
    private String cellPhone;               // 핸드폰번호
    private String firstname;               // 성
    private String lastname;                // 이름
    private String birth;                   // 생년월일
    private String address;                 // 주소
    private String sex;                     // 성별

    /** Tier, Point **/
    private Integer tier;                   // 등급 : 1~5 blonze, silver, gold, platinum, diamond
    private Long point;                     // 포인트

    /** Logging Data Manipulation **/
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private LocalDateTime droppedAt;

    /** Encrypt Password */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
