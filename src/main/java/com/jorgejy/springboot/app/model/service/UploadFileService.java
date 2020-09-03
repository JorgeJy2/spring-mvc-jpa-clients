package com.jorgejy.springboot.app.model.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
	public Resource load(String fileName) throws MalformedURLException ;
	public String copy(MultipartFile file) throws IOException;
	public boolean delete(String fileName);
	public void deleteAll();
	public void init() throws IOException;
}
