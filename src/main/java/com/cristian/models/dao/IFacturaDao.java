package com.cristian.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cristian.models.entity.Factura;

public interface IFacturaDao extends PagingAndSortingRepository<Factura, Long> {

	
}
