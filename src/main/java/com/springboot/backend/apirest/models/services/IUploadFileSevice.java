package com.springboot.backend.apirest.models.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileSevice {

	public Resource cargar (String FotoName) throws MalformedURLException ;
		
	public String copiar (MultipartFile archivo) throws IOException;
	
	public boolean eliminar (String NombreFoto);
	
	public Path getPath(String NombreFoto);
}
