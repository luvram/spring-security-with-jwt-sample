Spring Security 및 JWT를 활용하는 방법을 샘플로 작성하였습니다.
`JWTFilter`를 등록하여 매 요청마다 JWT 토큰의 유효성 검사하고 `SecurityContext`에 인증 정보를 저장합니다.
`POST /sign-in` 요청에서는 `AuthenticationManager`를 이용하여 Spring Security의 인증 로직을 실행합니다. 이때 `UserDetailsService`가 내부적으로 호출되어 저장되어있는 사용자 정보를 조회하게 되는데, `CustomUserDetailsService`로 오버라이드 하여 DB에서 데이터를 가져올 수 있도록 하였습니다.

### 클래스 설명
`WebSecurityConfig`: 시큐리티 설정
    - `WebSecurityConfigurerAdapter`: 스프링 시큐리티 보안설정 기능 초기화 및 설정
    - `@EnableWebSecurity`: 스프링 시큐리티 설정 클래스에 달아준다.
    - `frameOptions().sameOrigin()`: X-Frame-Options 헤더를 설정한다. 같은 Origin 이면 iframe에서 랜더링을 허용한다.
    - `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)`: SessionCreationPolicy.STATELESS 는 스프링 시큐리티가 세션을 생성하지 않고, 존재해도 사용하지 않도록 하는 설정값이다. 원래는 자동으로 세션을 사용하도록 설정되어 있다.
`JWTConfigurer`: `JWTFilter`를 필터로 등록
`JWTFilter`: 요청시 사용자의 jwt가 올바른지 검증하는 필터
`TokenProvider`: jwt 생성 및 검증과 관련된 기능을 제공
`CustomUserDetailsService`: 로그인 정보와 일치한 사용자의 정보를 제공. AuthenticationProvider에 의해 자동으로 호출됨.

    
`UserAuthenticationErrorHandler`: 패스워드가 일치하지 않을 시 추가 동작을 작성할 수 있다.
`AsynchronousSpringEventsConfig`: `ApplicationListener` 실행을 위한 설정을 정의할 수 있다. `TaskExecutor` 를 설정해주면 `ApplicationListener` 실행시 별도의 스레드에서 실행된다.
 

       