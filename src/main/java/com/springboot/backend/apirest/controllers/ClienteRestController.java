package com.springboot.backend.apirest.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.backend.apirest.models.entity.Cliente;
import com.springboot.backend.apirest.models.services.IClienteService;
import com.springboot.backend.apirest.models.services.IUploadFileSevice;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileSevice uploadService;
	
	protected static final Log logger = LogFactory.getLog(ClienteRestController.class.getName());

	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
		
		
	}
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page){
		return clienteService.findAll(PageRequest.of(page, 4));
		
		
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		
		Cliente cliente =null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente=clienteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al relizar la consulta de la base de datos ");
			response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR) ;

		}
			if (cliente==null) {
				response.put("mensaje","El cliente con ID: ".concat(id.toString().concat(" no exite en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND) ;

			}	
				
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK) ;
	}
	
	
	@PostMapping("/clientes")
	public ResponseEntity<?>  create(@Valid @RequestBody Cliente cliente, BindingResult result){
		Cliente clientenew =null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
//			List<String> errors = new ArrayList<>();
//
//			for(FieldError err: result.getFieldErrors()) {
//				errors.add("El campo '"+err.getField()+"' " +err.getDefaultMessage());
//			}
			List<String> errors =result.getFieldErrors().stream().map(err ->"El campo '"+err.getField()+"' " +err.getDefaultMessage() ).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST) ;
		}
		try {
			clientenew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al relizar el insert de la base de datos ");
			response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR) ;
		}

		response.put("mensaje", "El cliente ah sido creado con exito");
		response.put("client", clientenew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
		
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id){
		
		Cliente clienteActual =  clienteService.findById(id);
		Cliente clienteUpdate=null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
//			List<String> errors = new ArrayList<>();
//
//			for(FieldError err: result.getFieldErrors()) {
//				errors.add("El campo '"+err.getField()+"' " +err.getDefaultMessage());
//			}
			List<String> errors =result.getFieldErrors().stream().map(err ->"El campo '"+err.getField()+"' " +err.getDefaultMessage() ).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST) ;
		}
		if (clienteActual==null) {
			response.put("mensaje"," Error no se pudo editar , El cliente con ID: ".concat(id.toString().concat(" no exite en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND) ;
		}
		try {
		
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setEmail(cliente.getEmail());
		clienteUpdate = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al actualizar el cliente de la base de datos ");
			response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
		response.put("mensaje", "El cliente ah sido actualizado con exito");
		response.put("client", clienteUpdate);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;
		
		
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			Cliente cliente = clienteService.findById(id);

			String nombreFotoAnterior =cliente.getFoto();
			
			if (nombreFotoAnterior !=null && nombreFotoAnterior.length() >0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			clienteService.delete(id);

		} catch (DataAccessException e) {
			response.put("mensaje","Error al eliminar el cliente de la base de datos ");
			response.put("Error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR) ;
		}
		
		response.put("mensaje", "El cliente eliminado con exito");
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);

		
		
	}
	
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = clienteService.findById(id);
		
		if (!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() +"_"+ archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			logger.info(rutaArchivo.toString());
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje","Error al subir la imagen del cliente: " + nombreArchivo);
				response.put("Error",e.getMessage().concat(": ").concat(e.getCause().getMessage()));

				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR) ;
			}
			
			String nombreFotoAnterior =cliente.getFoto();
			
			if (nombreFotoAnterior !=null && nombreFotoAnterior.length() >0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				
				if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "has subido correctamente la imagen: "+ nombreArchivo);
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED) ;

	}
	
	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource recurso=null;
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/img").resolve("noImg.png").toAbsolutePath();
			try {
				recurso = new UrlResource(rutaArchivo.toUri());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.error("Error: no se pudo cargar la imagen. " + nombreFoto);
			
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename()+ "\"");
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
		
	}
	
	
	
	
}
