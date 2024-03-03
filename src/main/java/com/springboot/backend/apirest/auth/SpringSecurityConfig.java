package com.springboot.backend.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService usuarioService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    // Configura el AuthenticationManagerBuilder para usar un servicio personalizado de detalles de usuario y un codificador de contraseñas
	    auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	}

	@Bean("authenticationManager")
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
	    // Retorna el AuthenticationManager configurado
	    return super.authenticationManager();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
	    // Configura la seguridad HTTP
	    http.authorizeRequests()
	        // Requiere que todas las solicitudes estén autenticadas
	        .anyRequest().authenticated()
	        .and()
	        // Deshabilita CSRF (Cross-Site Request Forgery)
	        // CSRF (Cross-Site Request Forgery) es un ataque en el que un sitio malicioso puede aprovechar 
	        //la sesión de un usuario autenticado en otro sitio para realizar acciones no deseadas.
	        .csrf().disable()
	        // CORS (Cross-Origin Resource Sharing) es un mecanismo que permite que los recursos de un sitio 
	        //web sean solicitados desde otro dominio, evitando las políticas de seguridad de mismo origen en los navegadores.
	        
	        // Configura la gestión de la sesión como sin estado (STATELESS)
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
