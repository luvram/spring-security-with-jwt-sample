`@EnableWebSecurity`: 스프링 시큐리티 설정 클래스에 달아준다.
`@EnableGlobalMethodSecurity`: 
    - `prePostEnabled`: `@PreAuthorize` 어노테이션을 사용하기 위한 설정 활정화
    - `securedEnabled`: `@Secured`어노테이션 사용 활성화
`WebSecurityConfigurerAdapter`: 스프링 시큐리티 보안설정 기능 초기화 및 설정
`frameOptions().sameOrigin()`: X-Frame-Options 헤더를 설정한다. 같은 Origin 이면 iframe에서 랜더링을 허용한다.
`sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)`: SessionCreationPolicy.STATELESS 는 스프링 시큐리티가 세션을 생성하지 않고, 존재해도 사용하지 않도록 하는 설정값이다. 원래는 자동으로 세션을 사용하도록 설정되어 있다.

       