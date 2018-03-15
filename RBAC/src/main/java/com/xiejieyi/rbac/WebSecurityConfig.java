package com.xiejieyi.rbac;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.HttpStatus;
        import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.core.GrantedAuthorityDefaults;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.AuthenticationException;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;

/**
 * 类描述：
 *
 * @author
 */
@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

    //@Bean 把实例化的对象转化成一个Bean，放在Ioc容器中，会和@Autowired配合到一起，把对象、属性、方法完美封装。
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated(); //任何请求,登录后可以访问
                //    如果自定义filter，需要把formLogin注释掉。否则不会走这里。
                // .and()
                // .formLogin()
                // .successHandler(this::loginSuccessHandler)
                // .failureHandler(this::loginFailureHandler)
                // .loginPage("/login")
                // .failureUrl("/login?error")
                // .loginProcessingUrl("/authenticate")
                // // .defaultSuccessUrl("/perform_login")
                // .permitAll(); //登录页面用户任意访问
        // .and()
        // .logout().permitAll(); //注销行为任意访问
        http.csrf().disable();

        // 添加自定义的filter，处理用户登录的数据
        http.addFilterBefore(customAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        filter.setAuthenticationFailureHandler(this::loginFailureHandler);
        // 设置登录的URL
        filter.setFilterProcessesUrl("/authenticate");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    private void loginSuccessHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json; charset=utf-8");
        Map<String,String> result = new HashMap<String,String>();
        result.put("result","login success");
        objectMapper.writeValue(response.getWriter(), result);
    }

    private void loginFailureHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=utf-8");
        Map<String,String> result = new HashMap<String,String>();
        result.put("retCode","login failed");
        objectMapper.writeValue(response.getWriter(), result);
    }
}