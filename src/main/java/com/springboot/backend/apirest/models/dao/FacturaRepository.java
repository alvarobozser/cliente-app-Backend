package com.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.backend.apirest.models.entity.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

	public List<Factura> findAll();
}
