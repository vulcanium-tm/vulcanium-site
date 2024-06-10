package dev.vulcanium.site.tech.application.config;

import dev.vulcanium.site.tech.store.facade.customer.CustomerFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import dev.vulcanium.site.tech.admin.security.UserAuthenticationSuccessHandler;
import dev.vulcanium.site.tech.admin.security.WebUserServices;
import dev.vulcanium.site.tech.store.facade.customer.CustomerFacade;
import dev.vulcanium.site.tech.store.security.AuthenticationTokenFilter;
import dev.vulcanium.site.tech.store.security.ServicesAuthenticationSuccessHandler;
import dev.vulcanium.site.tech.store.security.admin.JWTAdminAuthenticationProvider;
import dev.vulcanium.site.tech.store.security.admin.JWTAdminServicesImpl;
import dev.vulcanium.site.tech.store.security.services.CredentialsService;
import dev.vulcanium.site.tech.store.security.services.CredentialsServiceImpl;

/**
 * Main entry point for security - admin - customer - auth - private - services
 */
@Configuration
@EnableWebSecurity
public class MultipleEntryPointsSecurityConfig{

private static final String API_VERSION = "/api";

@Bean
public AuthenticationTokenFilter authenticationTokenFilter(){
	return new AuthenticationTokenFilter();
}

@Bean
public CredentialsService credentialsService(){
	return new CredentialsServiceImpl();
}

@Bean
public PasswordEncoder passwordEncoder(){
	return new BCryptPasswordEncoder();
}

@Bean
public UserAuthenticationSuccessHandler userAuthenticationSuccessHandler(){
	return new UserAuthenticationSuccessHandler();
}

@Bean
public ServicesAuthenticationSuccessHandler servicesAuthenticationSuccessHandler(){
	return new ServicesAuthenticationSuccessHandler();
}

@Bean
public CustomerFacade customerFacade(){
	return new CustomerFacadeImpl();
}

@Configuration
@Order(1)
public static class CustomerConfigurationAdapter{
	
	@Autowired
	private UserDetailsService customerDetailsService;
	
	@Bean("customerAuthenticationManager")
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception{
		return http.getSharedObject(AuthenticationManager.class);
	}
	
	@Bean
	public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http) throws Exception{
		http.securityMatcher("/shop/**").csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("/shop/").permitAll().requestMatchers("/shop/**").permitAll().requestMatchers("/shop/customer/logon*").permitAll().requestMatchers("/shop/customer/registration*").permitAll().requestMatchers("/shop/customer/logout*").permitAll().requestMatchers("/shop/customer/customLogon*").permitAll().requestMatchers("/shop/customer/denied*").permitAll().requestMatchers("/shop/customer/**").hasRole("AUTH_CUSTOMER").anyRequest().authenticated()).httpBasic(httpBasic->httpBasic.authenticationEntryPoint(shopAuthenticationEntryPoint())).logout(logout->logout.logoutUrl("/shop/customer/logout").logoutSuccessUrl("/shop/").invalidateHttpSession(true).deleteCookies("JSESSIONID")).exceptionHandling(exceptionHandling->exceptionHandling.accessDeniedPage("/shop/"));
		return http.build();
	}
	
	@Bean
	public AuthenticationEntryPoint shopAuthenticationEntryPoint(){
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("shop-realm");
		return entryPoint;
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return web->{
			web.ignoring().requestMatchers("/");
			web.ignoring().requestMatchers("/error");
			web.ignoring().requestMatchers("/resources/**");
			web.ignoring().requestMatchers("/static/**");
			web.ignoring().requestMatchers("/services/public/**");
		};
	}
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(customerDetailsService);
	}
}

/**
 * services api v0
 */
@Configuration
@Order(2)
public static class ServicesApiConfigurationAdapter{
	
	@Autowired
	private WebUserServices userDetailsService;
	@Autowired
	private ServicesAuthenticationSuccessHandler servicesAuthenticationSuccessHandler;
	
	public ServicesApiConfigurationAdapter(){
		super();
	}
	
	@Bean
	public SecurityFilterChain userDetailsFilterChain(HttpSecurity http) throws Exception{
		http.securityMatcher("/services/**").csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("/services/public/**").permitAll().requestMatchers("/services/private/**").hasRole("AUTH").anyRequest().authenticated()).httpBasic(httpBasic->httpBasic.authenticationEntryPoint(servicesAuthenticationEntryPoint())).formLogin(formLogin->formLogin.successHandler(servicesAuthenticationSuccessHandler)).exceptionHandling(exceptionHandling->exceptionHandling.accessDeniedPage("/shop/"));
		return http.build();
	}
	
	@Bean
	public AuthenticationEntryPoint servicesAuthenticationEntryPoint(){
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("rest-customer-realm");
		return entryPoint;
	}
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService);
	}
}

/**
 * admin
 */
/**
 @Configuration
 @Order(3) public static class AdminConfigurationAdapter extends WebSecurityConfigurerAdapter {
 
 @Autowired private WebUserServices userDetailsService;
 
 @Autowired private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
 
 public AdminConfigurationAdapter() {
 super();
 }
 
 @Override public void configure(AuthenticationManagerBuilder auth) throws Exception {
 auth.userDetailsService(userDetailsService);
 }
 
 @Override public void configure(WebSecurity web) {
 }
 
 @Override protected void configure(HttpSecurity http) throws Exception {
 http
 .antMatcher("/admin/**")
 .authorizeRequests()
 .antMatchers("/admin/logon*").permitAll()
 .antMatchers("/admin/resources/**").permitAll()
 .antMatchers("/admin/layout/**").permitAll()
 .antMatchers("/admin/denied*").permitAll()
 .antMatchers("/admin/unauthorized*").permitAll()
 .antMatchers("/admin/users/resetPassword*").permitAll()
 .antMatchers("/admin/").hasRole("AUTH")
 .antMatchers("/admin/**").hasRole("AUTH")
 .antMatchers("/admin/**").hasRole("AUTH")
 .antMatchers("/admin/users/resetPasswordSecurityQtn*").permitAll()
 .anyRequest()
 .authenticated()
 .and()
 .httpBasic()
 .authenticationEntryPoint(adminAuthenticationEntryPoint())
 .and()
 .formLogin().usernameParameter("username").passwordParameter("password")
 .loginPage("/admin/logon.html")
 .loginProcessingUrl("/admin/performUserLogin")
 .successHandler(userAuthenticationSuccessHandler)
 .failureUrl("/admin/logon.html?login_error=true")
 .and()
 .csrf().disable()
 .logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/home.html")
 .invalidateHttpSession(true).and().exceptionHandling().accessDeniedPage("/admin/denied.html");
	
	
 }
 
 @Bean public AuthenticationEntryPoint adminAuthenticationEntryPoint() {
 BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
 entryPoint.setRealmName("admin-realm");
 return entryPoint;
 }
	
 }
 **/

/**
 * api - private
 */
@Configuration
@Order(5)
public class UserApiConfigurationAdapter{
	
	@Autowired
	private AuthenticationTokenFilter authenticationTokenFilter;
	
	@Autowired
	JWTAdminServicesImpl jwtUserDetailsService;
	
	public UserApiConfigurationAdapter(){
		super();
	}
	
	public SecurityFilterChain jwtAdminAuthenticationFilterChain(HttpSecurity http) throws Exception{
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizeRequests->authorizeRequests
						                                          .requestMatchers(API_VERSION + "/private/**").authenticated()
						                                          .requestMatchers(API_VERSION + "/private/login*").permitAll()
						                                          .requestMatchers(API_VERSION + "/private/refresh").permitAll()
						                                          .requestMatchers(HttpMethod.OPTIONS, API_VERSION + "/private/**").permitAll()
						                                          .requestMatchers(API_VERSION + "/private/**").hasRole("AUTH")
						                                          .anyRequest().authenticated())
				.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(apiAdminAuthenticationEntryPoint()))
				.addFilterAfter(authenticationTokenFilter, BasicAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationEntryPoint apiAdminAuthenticationEntryPoint(){
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("api-admin-realm");
		return entryPoint;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider(){
		JWTAdminAuthenticationProvider provider = new JWTAdminAuthenticationProvider();
		provider.setUserDetailsService(jwtUserDetailsService);
		return provider;
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return web->web.ignoring().requestMatchers("/swagger-ui.html");
	}
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(jwtUserDetailsService).and().authenticationProvider(authenticationProvider());
	}
}

/**
 * customer api
 */
@Configuration
@Order(6)
public static class CustomeApiConfigurationAdapter{
	
	@Autowired
	private AuthenticationTokenFilter authenticationTokenFilter;
	
	@Autowired
	private UserDetailsService jwtCustomerDetailsService;
	
	public CustomeApiConfigurationAdapter(){
		super();
	}
	
	public SecurityFilterChain jwtAuthenticationFilterChain(HttpSecurity http) throws Exception{
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers(API_VERSION + "/auth/**").authenticated().requestMatchers(API_VERSION + "/auth/refresh").permitAll().requestMatchers(API_VERSION + "/auth/login").permitAll().requestMatchers(API_VERSION + "/auth/register").permitAll().requestMatchers(HttpMethod.OPTIONS, API_VERSION + "/auth/**").permitAll().requestMatchers(API_VERSION + "/auth/**").hasRole("AUTH_CUSTOMER").anyRequest().authenticated()).httpBasic(httpBasic->httpBasic.authenticationEntryPoint(apiCustomerAuthenticationEntryPoint())).addFilterAfter(authenticationTokenFilter, BasicAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationEntryPoint apiCustomerAuthenticationEntryPoint(){
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
		entryPoint.setRealmName("api-customer-realm");
		return entryPoint;
	}
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(jwtCustomerDetailsService);
	}
}
}

