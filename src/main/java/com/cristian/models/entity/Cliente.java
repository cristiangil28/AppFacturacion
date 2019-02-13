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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // strategy=GenerationType.IDENTITY, estamos indicando que es un
														// valor incremental
	private Long id;
	@NotEmpty
	private String nombre;
	@NotEmpty
	@Column(name = "apellidos")
	private String apellido;
	@NotEmpty
	@Email
	private String correo;

	@NonNull
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) // va a tomar el valor de la fecha y lo va a comodar al estandar de la base de
									// datos
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	private String foto;

	/*
	 * mappedBy="cliente", es el atributo de la relación , en este caso se encuentra
	 * en la clase Factura.
	 * 
	 * fetch=FetchType.LAZY, es la busqueda perezosa de hibernate, lo cual no va a
	 * traer las relaciones de la clase entity consultada. Es la predeterminada.
	 * 
	 * fetch=FetchType.EAGER, es la contraria, trae todas las relaciones de la
	 * entidad consultada.
	 * 
	 * cascade=CascadeType.ALL, Al persistir una entidad, también persisten las
	 * entidades mantenidas en este campo.
	 */
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;

	

	public Cliente() {
		facturas= new ArrayList<Factura>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Factura> getClientes() {
		return facturas;
	}

	public void setClientes(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addfactura(Factura factura) {
		facturas.add(factura);
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

}
