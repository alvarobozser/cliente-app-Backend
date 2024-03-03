package com.springboot.backend.apirest.models.services;

import com.springboot.backend.apirest.models.entity.Usuario;

public interface UsuarioService {

	public Usuario findByUsername(String username);
}
