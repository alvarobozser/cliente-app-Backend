package com.bolsadeideas.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/uploads/img/**","/images/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("BASIC", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("BASIC", "ADMIN")
//		.antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
//		.antMatchers("/api/clientes/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    // Se crea una nueva configuración CORS
	    CorsConfiguration configuration = new CorsConfiguration();	    
	    // Se especifica el origen permitido, en este caso, solo se permite "http://localhost:4200"
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    // Se especifican los métodos HTTP permitidos
	    configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS"));	    
	    // Se permite enviar credenciales (como cookies) en la solicitud
	    configuration.setAllowCredentials(true);    
	    // Se especifican los encabezados permitidos en la solicitud
	    configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));	    
	    // Se crea una fuente de configuración basada en URL para aplicar la configuración CORS a todas las rutas
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();	    
	    // Se registra la configuración CORS para aplicarla a todas las rutas (/**)
	    source.registerCorsConfiguration("/**", configuration);    
	    // Se devuelve la fuente de configuración CORS
	    return source;
	}

	// Método para validar el token y filtrar cada vez que hagamos una petición
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
	    // Se crea un nuevo objeto FilterRegistrationBean que envuelve un CorsFilter, que utiliza la configuración CORS definida anteriormente
	    FilterRegistrationBean<CorsFilter> bean= new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));	    
	    // Se establece la prioridad de este filtro como la más alta, asegurando que se ejecute antes que otros filtros
	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    // Se devuelve el objeto FilterRegistrationBean configurado
	    return bean;
	}
}