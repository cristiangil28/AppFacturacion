package com.cristian.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cristian.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto,Long> {
	
	/*
	 * la consulta es a nivel de entidad, no de tabla
	 */
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
}
