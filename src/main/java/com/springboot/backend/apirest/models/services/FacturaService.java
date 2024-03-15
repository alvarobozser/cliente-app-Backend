package com.springboot.backend.apirest.models.services;

import java.util.List;

import com.springboot.backend.apirest.models.entity.Factura;


public interface FacturaService {
	public List<Factura> findAll();

}
