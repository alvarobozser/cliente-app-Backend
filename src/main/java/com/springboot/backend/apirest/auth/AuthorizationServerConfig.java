package com.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private InfoAdicionalToken infoAdicionalToken;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	    // Configura el acceso al endpoint para obtener la clave pública del token como permitido para todos
	    security.tokenKeyAccess("permitAll()")
	    // Configura el acceso al endpoint para verificar si un token es válido como autenticado
	    .checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	    // Configura los detalles del cliente que se almacenarán en memoria
	    clients.inMemory()
	        // Especifica el cliente "angularapp"
	        .withClient("angularapp")
	        // Especifica la contraseña (secreto) del cliente
	        .secret(passwordEncoder.encode("12345"))
	        // Especifica los alcances (scopes) que el cliente puede solicitar
	        .scopes("read", "write")
	        // Especifica los tipos de concesión autorizados para el cliente
	        .authorizedGrantTypes("password", "refresh_token")
	        // Especifica la validez del token de acceso en segundos
	        .accessTokenValiditySeconds(1800)
	        // Especifica la validez del token de actualización en segundos
	        .refreshTokenValiditySeconds(1800);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    // Configura los puntos finales del servidor de autorización
	    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
	    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
	    
	    endpoints.authenticationManager(authenticationManager)
	        // Configura el almacenamiento del token
	        .tokenStore(tokenStore())
	        // Configura el convertidor de token de acceso JWT
	        .accessTokenConverter(accessTokenConverter())
	        // Configura el realzador del token con cadena de datos incorporada
	        .tokenEnhancer(tokenEnhancerChain);
	}

	@Bean
	public JwtTokenStore tokenStore() {
	    // Devuelve un objeto JwtTokenStore que utiliza el convertidor de token de acceso JWT , si no por defecto lo pone.
	    return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
	    // Crea y configura un convertidor de token de acceso JWT
	    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
	    // Establece la clave de firma del token JWT
	    jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);
	    // Establece la clave de verificación del token JWT
	    jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);
	    // Devuelve el convertidor de token de acceso JWT configurado
	    return jwtAccessTokenConverter;
	}

}
