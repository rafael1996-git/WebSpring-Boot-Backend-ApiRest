package com.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImplement implements IUploadFileSevice{
	protected static final Log logger = LogFactory.getLog(UploadFileServiceImplement.class.getName());
	private final static String DIRECTORIO_UPLOAD ="uploads";
	@Override
	public Resource cargar(String FotoName) throws MalformedURLException {
		Path rutaArchivo = getPath(FotoName);
		Resource recurso= new UrlResource(rutaArchivo.toUri());
		
		if (!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/main/resources/static/img").resolve("noImg.png").toAbsolutePath();
			recurso = new UrlResource(rutaArchivo.toUri());
			
			logger.error("Error: no se pudo cargar la imagen. " + FotoName);
			
		}
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString() +"_"+ archivo.getOriginalFilename().replace(" ", "");
		Path rutaArchivo = getPath(nombreArchivo);
		logger.info(rutaArchivo.toString());
		Files.copy(archivo.getInputStream(), rutaArchivo);
		
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String NombreFoto) {
		if (NombreFoto !=null && NombreFoto.length() >0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(NombreFoto).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			
			if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String NombreFoto) {
		// TODO Auto-generated method stub
		return Paths.get(DIRECTORIO_UPLOAD).resolve(NombreFoto).toAbsolutePath();
	}

}
