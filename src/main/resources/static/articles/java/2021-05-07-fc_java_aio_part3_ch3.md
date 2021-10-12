---
title: <패스트캠퍼스> Java 웹개발마스터 Part3 - CH3. 웹 개발 개론
tags: LectureNote Fastcampus Java_All_In_One
---

## 웹 개발이란?

### Web의 기본 3요소

- URI : Uniform Resource Indentifier  
  리소스 식별자이다.  
  웹상에서 특정 정보에 접근하기 위한 정보를 말한다.

- HTTP : Hypertext Transfer Protocol   
  처음에는 HTML만을 전송하기위한 프로토콜이었으나, 현재는 다양한 정보를 전송한다.  
  GET, POST, PUT, ... 등 여러가지 프로토콜이 존재한다.

- HTML : Hyper Text Markup Language  
  XML을 바탕으로 한 범용 문서 포맷

## REST란?

### REST

REST는 Representational State Transfer 의 약자로, 자원의 상태 전달이라는 뜻이다.  
엄격한 의미의 REST는 네트워크 아키텍처 원리의 모음이다. 여기서 네트워크 아키텍처 원리란 자원을 정의하고,
자원에 대한 주소를 지정하는 방법 전반을 일컫는다. 이 개념은 2000년 로이 필딩의 박사학위 논문에서 소개되
었고 필딩의 REST원리를 따르는 시스템을 RESTful이란 용어로 지칭한다.

다음 6가지 항목을 만족해야 RESTful하다고 할 수 있다.

### REST 원리

1. Client, Server : 클라이언트와 서버가 독립적으로 분리되어 있어야 한다.
2. Stateless : 요청에 대하여 클라이언트의 상태를 서버에 저장하지 않는다.
3. Cache : 클라이언트는 서버의 응답을 Cache(임시저장)할 수 있어야 한다. 클라이언트가 Cache를 통해서
응답을 재사용할 수 있어야 하며, 이를 통해서 서버의 부하를 낮춘다.
4. Layered System(계층화) : 서버와 클라이언트 사이에 방화벽, 게이트웨이, 프록시 등 다양한 계층 형
태로 구성이 가능해야 하며, 이를 확장할 수 있어야 한다.
5. 인터페이스 일관성 : 인터페이스의 일관성을 지키고, 아키텍처를 단순화 시켜 작은 단위로 분리하여 클라이
언트, 서버가 독립적으로 개선될 수 있어야 한다.
6. Code on Demand(Optional) : 자바 애플릿, 자바스크립트, 플래시 등 특정한 기능을 서버로부터 클라
이언트가 전달받아 코드를 실행 할 수 있어야 한다.

### REST 인터페이스의 원칙에 대한 가이드

다음의 인터페이스의 일관성이 잘 지켜졌는지에 따라 판단할 수 있다.

1. 자원의 식별  

  웹 기반의 REST에서는 리소스접근을 할 때 URI를 사용한다.  
  https://foo.co.kr/user/100  
  Resource : user / 식별자 : 100

2. 메시지를 통한 리소스 조작  

  Web에서는 다양한 방식으로 데이터를 전달 할 수 있다.  
  그 중 가장 많이 사용하는 방식은 HTML, XML, JSON, TEXT 등이 있다.
  이 중, 어떠한 타입의 데이터인지 알려주기 위해 HTTP header 부분에 content-type을 통해 데이터의
  타입을 지정해 줄 수 있다.  

  또, 리소스 조작을 위해서 데이터 전체를 전달하지 않고, 이를 메세지로 전달한다.
  데이터 전체를 전달하지 않는다는 것은 서버에서 클라이언트로 정보를 전달할 때, 데이터를 그대로 보내게 되
  면, 변수명이 바뀌거나 했을 때 클라이언트가 인식하지 못하는 경우가 생기고 이를 방지하기 위해 별도 메세지
  를 새로 생성하여 전달하라는 의미이다.  

  즉 이것은 RESTful하기 위한 조건 중에 1번인 서버와 클라이언트가 독립적이어야 한다는 조건을 만족시키기
  위한 것이다.

3. 자기 서술적 메세지

  요청하는 데이터가 어떻게 처리되어져야 하는지 충분한 정보를 포함해야 한다.  
  HTTP기반 REST에서는 HTTP Method와 Header정보, 그리고 URI에 포함되는 정보로 표현할 수 있다.

  GET : https://foo.co.kr/user/100 사용자의 정보 요청  
  POST : https://foo.co.kr/user 사용자의 정보 생성  
  PUT : https://foo.co.kr/user 사용자 정보 생성 및 수정  
  DELETE : https://foo.co.kr/user/100 사용자 정보 삭제  

  그 외에 담지 못한 정보들은 URI의 메세지를 통하여 전달한다.

4. 어플리케이션 상태에 대한 엔지으로써 하이퍼미디어  
  단순 클라이언트 요청에 대한 데이터 응답만 주는것이 아니라, 리소스에 대한 link정보까지 같이 포함되어져
  야 한다.

## URI 설계 패턴

1. URI (Uniform Resource Identifier)

  인터넷에서 특정 자원을 나타내는 값이며 해당 값은 유일하다.  
  요청 : https://www.fastcampus.co.kr/resource/sample/1  
  응답 : fastcampus.pdf, fastcampus.docx

2. URL (Uniform Resource Locator)

  인터넷 상에서 자원, 특정 파일이 어디에 위치하는지 식별하는 주소
  요청 : https://www.fastcampus.co.kr/fastcampus.pdf

  URL은 URI의 하위 개념이다. URI는 해당 리소스정보가 변경될 수 있지만, URL은 정해진 주소이기 때문에
  리소스변경이 일어날 수 없다.

### URI 설계 원칙 (RFC-3968)

- 슬래시 구분자(/)는 계층 관계를 나타낼 때 사용한다.
- URI 마지막 문자로 (/)를 포함하지 않는다.
- 하이픈(-)은 가독성을 높이는데 사용한다.
- 밑줄은(_)은 사용하지 않는다.
- URI경로는 소문자를 쓴다.
- 파일확장자는 URI에 포함하지 않는다.
- 프로그래밍 언어에 의존적인 확장자를 쓰지 않는다. ex) .../somthing.do
- 구현에 의존적인 경로를 쓰지 않는다. ex) .../servlet/...
- 세션ID를 포함하지 않는다.
- 프로그래밍 언어의 Method명을 이용하지 않는다. ex) .../somthing?action=intro
- 명사는 단수형보다 복수형을 사용해야 한다. 컬렉션에 대한 표현은 복수로 사용한다.
- 컨트롤러 이름으로는 동사나 동사구를 사용한다. ex) .../re-order
- 경로 부분 중 변하는 부분은 유일한 값으로 대체한다.  
  ex).../{lesson-id}/users/{user-id}  -> .../2/users/100
- CRUD기능을 나타내는것은 URI에 사용하지 않는다.  
  GET : .../users/100/READ (x)  
  DELETE : .../users/100 (o)
- URI Query Parameter 디자인  
  URI쿼리 부분으로 컬렉션 결과에 대해서 필터링할 수 있다.  
  ex) .../web-master?chapter=2
- URI 쿼리는 컬렉션의 결과를 페이지로 구분하여 나타내는데 사용한다.
- API에 있어서 서브 도메인은 일관성 있게 사용한다.  
  ex) naver.com -> news.naver.com
- 클라이언트 개발자 포털 서브 도메인은 일관성 있게 만든다.
  ex) https://dev-fastcampus.co.kr

## HTTP Protocol

- HTTP(Hyper Text Transfer Protocol)로 RFC2616에서 규정된 Web에서 데이터를 주고받는 프로토콜
- 이름에는 하이퍼텍스트 전송용 프로토콜로 정의되어 있지만 실제로 HTML, XML, JSON, Image, Voice,
  Video, Javascript, PDF 등 다양한 것을 모두 전송할 수 있다.
- HTTP는 TCP를 기반으로 한 REST의 특징을 모두 구현하고 있는 Web기반의 프로토콜
- HTTP는 메세지를 요청(Request)하고 응답(Response)하는 형태의 통신방법이다.

### HTTP 요청 메소드

|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
||의미|CRUD|멱등성|안정성|Path  Variable|Query  Parameter|DataBody|
|-|-|-|-|-|-|-|-|
|GET|리소스 취득|R|O|O|O|O|X|
|POST|리소스 생성,추가|C|X|X|O|△|O|
|PUT|리소스 갱신, 생성|C/U|O|X|O|△|O|
|DELETE|리소스 삭제|D|O|X|O|O|X|
|HEAD|헤더 데이터 취득|-|O|O|-|-|-|
|OPTION|지원하는 메소드 취득|-|O|-|-|-|-|
|TRACE|요청메세지 변환|-|O|-|-|-|-|
|CONNECT|프록시 동작의 터널접속으로 변경|-|X|-|-|-|-|

멱등성 : 연산을 여러번 적용하더라도 결과가 달라지지 않음. 여기서는 여러번 요청해도 같은 응답이 온다는 것
을 의미한다.

### HTTP Status

|:-:|:-:|:-:|
|응답코드|의미|내용|
|-|-|-|
|1XX|처리중|처리상태. 클라이언트는 요청을 계속하거나 서버에 지시에 따라 재요청|
|2XX|성공|요청성공|
|3XX|리다이렉트|다른 리소스로 리다이렉트. 해당코드를 받았을때 새로운 주소로 다시 요청|
|4XX|클라이언트 에러|클라이언트의 요청에 에러가 있는상태. 재요청해도 에러해결되지 않는다.|
|5XX|서버에러|서버 처리중 에러. 재 전송시 에러가 해결되었을 수도 있다.|

- 자주 사용되는 응답코드

|:-:|:-:|
|코드|내용|
|-|-|
|200|성공|
|201|리소스 생성 성공|
|301|리다이렉트, 리소스가 다른 장소로 변경됨을 알림|
|303|리다이렉트, 클라이언트에서 자동으로 새로운 리소스로 요청처리|
|400|요청 오류, 파라미터 에러|
|401|인증실패, 권한없음|
|404|리소스 없음(페이지를 찾을 수 없음)|
|500|서버 내부 에러(서버 동작 처리 에러)|
|503|서비스 정지(점검 등등)|