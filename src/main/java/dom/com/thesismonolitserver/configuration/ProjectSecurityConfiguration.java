package dom.com.thesismonolitserver.configuration;

import dom.com.thesismonolitserver.convertors.GenericConverterType;
import dom.com.thesismonolitserver.dtos.ImageDTO;
import dom.com.thesismonolitserver.dtos.UserDTO;
import dom.com.thesismonolitserver.enteties.ImageEntity;
import dom.com.thesismonolitserver.enteties.UserDataEntity;
import dom.com.thesismonolitserver.filters.CustomAuthenticationFilter;
import dom.com.thesismonolitserver.filters.JwtRequestFilter;
import dom.com.thesismonolitserver.filters.SimpleCORSFilter;
import dom.com.thesismonolitserver.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ProjectSecurityConfiguration {

    private JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    public ProjectSecurityConfiguration(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return new CustomAuthenticationFilter(authenticationManager(authenticationConfiguration));
    }
    @Bean
    public SimpleCORSFilter simpleCORSFilter(){
        return new SimpleCORSFilter();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails user = User.withUsername("octavian")
//                .authorities("ROLE_USER")
//                .password("octavian")
//                .build();
//        manager.createUser(user);
//        return manager;
//    }

    @Bean
    public Jwt jwt(){
        return  new Jwt();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter(UserDetailsService userDetailsService){
        this.jwtRequestFilter = new JwtRequestFilter(userDetailsService, jwt());
        return jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new SimpleCORSFilter(), ChannelProcessingFilter.class);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
//                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/user/new-user").permitAll()
                        .requestMatchers("/api/user/phone_validation/**").permitAll()
                        .requestMatchers("/api/user/email_validation/**").permitAll()
                        .requestMatchers("/api/image/download-image/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GenericConverterType genericConverterType(@Qualifier("converterImageEntity2ImageDTO") Converter<ImageEntity, ImageDTO> converterImageEntity2ImageDTO,
                                                     @Qualifier("converterImageDTO2ImageEntity") Converter<ImageDTO, ImageEntity> converterImageDTO2ImageEntity,
                                                     @Qualifier("converterUserEntity2UserDTO") Converter<UserDataEntity, UserDTO> converterUserEntity2UserDTO){
        Map<Class<?>, Converter<?, ?>> mapOfClassConverterAndItsImplementation = new HashMap<>();
        mapOfClassConverterAndItsImplementation.put(ImageDTO.class, converterImageEntity2ImageDTO);
        mapOfClassConverterAndItsImplementation.put(ImageEntity.class, converterImageDTO2ImageEntity);
        mapOfClassConverterAndItsImplementation.put(UserDTO.class, converterUserEntity2UserDTO);
        return new GenericConverterType(mapOfClassConverterAndItsImplementation);
    }
}