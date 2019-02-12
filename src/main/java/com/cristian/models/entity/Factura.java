package com.cristian.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String direccion;
	private String observacion;
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;

	/*
	 * fetch=FetchType.LAZY, es la busqueda perezosa de hibernate, lo cual no va a
	 * traer las relaciones de la clase entity consultada. Es la predeterminada
	 * 
	 * fetch=FetchType.EAGER, es la contraria, trae todas las relaciones de la
	 * entidad consultada
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id") // cuando la relación es un solo sentido, se debe referenciar a la llave foránea
										// que se encuentra en la entidad a relacionar, en este caso se encuentra en la
										// clase ItemFactura
	private List<ItemFactura> items;

	/*
	 * @PrePersist, va a permitir instanciar la fecha antes de que se cree la
	 * factura
	 */
	
	public Factura() {
	this.items= new ArrayList<>();
	}
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	public void addItemFactura(ItemFactura item) {
		items.add(item);
	}
	
	public double getTtotal() {
		double total=0;
		int size=items.size();
		for (int i = 0; i < size; i++) {
			total+=items.get(i).calcularImporte();
		}
		return total;
	}
}
