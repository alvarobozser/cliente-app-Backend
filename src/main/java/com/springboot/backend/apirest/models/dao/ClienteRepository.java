package com.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.backend.apirest.models.entity.Cliente;
import com.springboot.backend.apirest.models.entity.Region;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("from Region")
	public List<Region> findAllRegiones();
}
