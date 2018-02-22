/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.security;

/**
 *
 * @author Rider1
 */
import javax.sql.DataSource;
import org.dragoncave.home.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
public class DCSercurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    
    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
           .ignoring()
                
                .antMatchers("/resources/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/","/Hi.jsp","/register","/index2","/register/add","/game/Scrabble","/scrabble/game/req").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/welcome").defaultSuccessUrl("/welcome")
                .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/welcome?logout")
                .permitAll()
            .and()
            .csrf();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {    
    /*    auth
            .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from AppUser where username = ?")
                .authoritiesByUsernameQuery("select username, role as authority from AppUser where username = ?");
    */
        auth.userDetailsService(userServiceImpl);
    }
    
    
    
    /*
    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;
    
    @Autowired
    PersistentTokenRepository tokenRepository;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/","/list")
                .access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
            .antMatchers("/newuser/**","/delete-user-*")
                .access("hasRole('ADMIN')")
            .antMatchers("/edit-user-*")
                .access("hasRole('ADMIN') or hasRole('DBA')")
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("ssoID").passwordParameter("password")
            .and()
            .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenRepository(tokenRepository)
                .tokenValiditySeconds(86400)
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
            .csrf()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/AccessDenied");
                
    }
    */
    /*
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTBRMS(){
        PersistentTokenBasedRememberMeServices rememberMeService = new PersistentTokenBasedRememberMeServices("remember-me",userDetailsService,tokenRepository);
        return rememberMeService;
    }
    
    @Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver(){
        return new AuthenticationTrustResolverImpl();
    }*/
}
