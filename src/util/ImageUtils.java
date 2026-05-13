package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImageUtils {

	private static final String DIRECTORY_PATH = "product-images";

	public static String copyAndSaveImage(String sourceFilePath, String maSanPham) {
		if (sourceFilePath == null || sourceFilePath.trim().isEmpty()) {
			return null;
		}

		File sourceFile = new File(sourceFilePath);

		if (!sourceFile.exists()) {
			return sourceFilePath;
		}

		try {
			File directory = new File(DIRECTORY_PATH);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String fileName = sourceFile.getName();
			String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : ".jng";
			String newFileName = maSanPham + extension;

			File destinationFile = new File(directory, newFileName);

			Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			return newFileName;
		} catch (IOException e) {
			return null;
		}
	}
}