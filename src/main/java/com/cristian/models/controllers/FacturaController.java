package com.cristian.models.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristian.models.entity.Cliente;
import com.cristian.models.entity.Factura;
import com.cristian.models.entity.ItemFactura;
import com.cristian.models.entity.Producto;
import com.cristian.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Factura factura= clienteService.fecthByIdWithClienteWithItemFacturaWithProducto(id);/*clienteService.findFacturaById(id);*/
		
		if(factura==null) {
			flash.addFlashAttribute("error", "La factura no exite en la base de datos");
			return "redirect:/listar";
		}
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura".concat(factura.getDescripcion()));
		return "factura/ver";
	}

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);
		if (cliente == null) {
			flash.addFlashAttribute("error", "el cliente no exite en la base de datos");
			return "redirect:/listar";
		} else {
			Factura factura = new Factura();
			factura.setCliente(cliente);
			model.put("factura", factura);
			model.put("titulo", "Crear Factura");
			return "factura/form";
		}

	}

	/*
	 * @ResponseBody transforma la salida en un JSON y la publica en el body,
	 * suprimiendo cargar en la vista de thymeleaf
	 */
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@PostMapping("/form")

	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "crear factura");
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "crear factura");
			model.addAttribute("error", "Error: La factura no puede estar vacia!");
			return "factura/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
		}
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("sucess", "factura creada con exito");
		return "redirect:/ver/" + factura.getCliente().getId();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value="id")Long id,RedirectAttributes flash) {
	Factura factura=clienteService.findFacturaById(id);
	
	if(factura!=null) {
		clienteService.deleteFactura(id);
		flash.addFlashAttribute("sucess", "Factura eliminada con éxito");
		
		return "redirect:/ver/"+ factura.getCliente().getId();
	}
	
	flash.addFlashAttribute("error", "Factura no se pudo eliminar porque no existe en la base de datos");
	
	return "redirect:/listar";
	}
}
