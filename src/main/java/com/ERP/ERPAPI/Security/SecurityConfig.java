package com.ERP.ERPAPI.Security;

import com.ERP.ERPAPI.JWT.AdminJwtUserDetailService;
import com.ERP.ERPAPI.JWT.StudentJwtRequestFilter;
import com.ERP.ERPAPI.JWT.StudentJwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private StudentJwtUserDetailsService studentJwtUserDetailsService;

    @Autowired
    private StudentJwtRequestFilter studentJwtRequestFilter;



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentJwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.httpBasic().disable().csrf().disable().authorizeRequests().antMatchers( "/createStudent","/forgotStudentPassword","/validateStudentOtp/**",
                        "/validateStudentForgotPassword","/createStudentNewPassword","/h2-console/**","/authenticateStudent","/delete/**","/create/**","/forgot/**","/validate/**","/authenticateAdmin","/show/**",
                        "/update/**").permitAll().
                anyRequest().authenticated();
        security.headers().frameOptions().disable();
        security.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        security.addFilterBefore(studentJwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        security.addFilterBefore(adminJwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
