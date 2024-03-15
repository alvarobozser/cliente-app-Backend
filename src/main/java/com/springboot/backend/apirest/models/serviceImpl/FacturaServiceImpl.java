package com.springboot.backend.apirest.models.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.apirest.models.dao.FacturaRepository;
import com.springboot.backend.apirest.models.entity.Factura;
import com.springboot.backend.apirest.models.services.FacturaService;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Factura> findAll() {
		return (List<Factura>) facturaRepository.findAll();
	}
}
