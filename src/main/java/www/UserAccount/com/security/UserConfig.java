package www.UserAccount.com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class UserConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username,passwords,is_active from user where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select r.role_name, u.username from user u inner join role r on u.role_id = r.role_id where u.username=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(autho ->
                autho
                        .requestMatchers(HttpMethod.POST, "/api/user/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/verify").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/administrator/approve/").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/administrator/").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET, "/api/administrator/customer").hasAuthority("admin")

                        .anyRequest().authenticated()

        );
        http.formLogin(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.httpBasic(Customizer.withDefaults());
        return http.build();

    }

}
