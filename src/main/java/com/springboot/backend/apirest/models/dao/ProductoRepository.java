package com.springboot.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.apirest.models.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Long>{
		
	@Query(value = "SELECT * FROM productos p WHERE p.nombre LIKE %?1%", nativeQuery = true)
    List<Producto> findByNombre(String termino);

}
