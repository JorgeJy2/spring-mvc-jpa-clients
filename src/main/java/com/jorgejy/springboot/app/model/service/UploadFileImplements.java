package com.jorgejy.springboot.app.model.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileImplements implements UploadFileService {

	private final static String UPLOADS_FOLDER = "//home//jorge//spring-uploads";

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Resource load(String fileName) throws MalformedURLException {
		Path pathPicture = getPath(fileName);
		log.info("pathFoto: " + pathPicture);
		Resource resource = null;

		resource = new UrlResource(pathPicture.toUri());
		if (!resource.exists() || !resource.isReadable()) {
			throw new RuntimeException("Not load image:  " + pathPicture.toString());
		}

		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueNamePicture = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		byte[] pictureBytes = file.getBytes();
		Path routeComplete = getPath(uniqueNamePicture);
		Files.write(routeComplete, pictureBytes);
		return uniqueNamePicture;
	}

	@Override
	public boolean delete(String fileName) {
		Path rootPath = getPath(fileName);
		File filePicture = rootPath.toFile();
		if (filePicture.exists() && filePicture.canRead()) {
			if (filePicture.delete()) {
				return true;
			}
		}
		return false;
	}

	private Path getPath(String fileName) {
		return Paths.get(UPLOADS_FOLDER).resolve(fileName).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}

}
