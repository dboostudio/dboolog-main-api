# Dboo's Log

## Main API Server


### 적용 기술 및 사용 라이브러리

- docker-compose : 가상컨테이너로 DB를 손쉽게 연결하기 위해 사용
  
- SpringBoot
  - AOP패턴을 각 컨트롤러에 적용, 응답 시간 로그로 모니터링
  
- Spring-Security
  - Account계정에 UserDetail과 entity를 동시에 적용하여 DB에서 사용자 계정을 관리할 수 있도록 개발
  
- Spring-JPA
  - 블로그 포스트와 코멘트, 댓글을 1:N관계로 적용
  - 블로그의 태그와 포스트를 N:N관계로 적용
  
- Lombok
- Gson : json처리
- commonmark : 마크다운 컨버터
- swagger : API 정의서 자동생성