package com.springboot.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.apirest.models.entity.Factura;
import com.springboot.backend.apirest.models.services.FacturaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class FacturaRestController {

	@Autowired
	private FacturaService facturaService;

	@GetMapping("/facturas")
	public List<Factura> index() {
		return facturaService.findAll();
	}
}
