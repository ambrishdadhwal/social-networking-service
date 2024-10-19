package com.social.network.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileUtil
{

	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

	public static boolean uploadImage(MultipartFile file) throws IOException
	{
		StringBuilder fileNames = new StringBuilder();
		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		fileNames.append(file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());

		return false;
	}
}
