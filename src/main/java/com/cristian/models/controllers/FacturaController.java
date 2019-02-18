package com.cristian.models.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristian.models.entity.Cliente;
import com.cristian.models.entity.Factura;
import com.cristian.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	@GetMapping("/form/{clienteID}")
	public String crear(@PathVariable(value="clienteId") Long clienteId,
			Map<String,Object> model, RedirectAttributes flash) {
		
		Cliente cliente=clienteService.findOne(clienteId);
		if (cliente==null) {
			flash.addFlashAttribute("error", "el cliente no exite en la base de datos");
			return "redirect:/listar";
		}else {
			Factura factura =  new Factura();
			factura.setCliente(cliente);
			model.put("factura", factura);
			model.put("titulo", "Crear Factura");
			return "factura/form";
		}
		
	}
}