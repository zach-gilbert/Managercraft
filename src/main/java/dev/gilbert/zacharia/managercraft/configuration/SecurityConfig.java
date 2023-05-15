package dev.gilbert.zacharia.managercraft.configuration;

//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.SecurityFilterChain;

// TODO: Temporarily disabling mutual TLS

//TODO: Solve this
/*
2023-05-14T17:55:55.842-04:00  WARN 40828 --- [           main] .s.s.UserDetailsServiceAutoConfiguration :

Using generated security password: ed72d132-4b55-4914-9d48-27bd48c3e57a

This generated password is for development use only. Your security configuration must be updated before running your application in production.

2023-05-14T17:55:55.901-04:00  INFO 40828 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will secure any request with [org.springframework.security.web.session.DisableEncodeUrlFilter@6a1ef65c, org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@72976b4, org.springframework.security.web.context.SecurityContextHolderFilter@315105f, org.springframework.security.web.header.HeaderWriterFilter@5a545b0f, org.springframework.security.web.csrf.CsrfFilter@58658f63, org.springframework.security.web.authentication.logout.LogoutFilter@7134b8a7, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@45ab3bdd, org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@430b2699, org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@726934e2, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@64e1377c, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@40247d48, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@18b74ea, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@1319bc2a, org.springframework.security.web.access.ExceptionTranslationFilter@b73433, org.springframework.security.web.access.intercept.AuthorizationFilter@6088451e]
 */

//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .x509()
//                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
//                .userDetailsService(userDetailsService())
//                .and()
//                .authorizeHttpRequests ()
//                .anyRequest().authenticated();
//
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) {
//                if (username.equals("localhost")) {
//                    return new User(username, "",
//                            AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
//                } else {
//                    throw new UsernameNotFoundException("User not found");
//                }
//            }
//        };
//    }
//}



