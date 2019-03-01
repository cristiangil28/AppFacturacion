package com.cristian.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cristian.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Factura fecthByIdWithClienteWithItemFacturaWithProducto(Long id);
}
