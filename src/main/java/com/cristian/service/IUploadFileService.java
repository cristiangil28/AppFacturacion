package com.cristian.service;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	public Resource load(String filename) throws MalformedURLException;
	
	public String copy(MultipartFile file);	
	public boolean delete(String filename);
}
