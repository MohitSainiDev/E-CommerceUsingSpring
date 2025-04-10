package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// File names of original File
		String originalFileName = file.getOriginalFilename();
		// Generate a unique file name
		String randomId = UUID.randomUUID().toString();

		String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
		String filePath = path + File.separator + fileName;// we could have done like path +"/"+ fileName , but it
															// may not work on all OS
		// check if path exists and create
		File folder = new File(path);
		if (!folder.exists())
			folder.mkdir();
		// upload to server
		Files.copy(file.getInputStream(), Paths.get(filePath));
		// returning file name
		return fileName;
	}
}
