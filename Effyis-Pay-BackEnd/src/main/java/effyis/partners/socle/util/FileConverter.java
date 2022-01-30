package effyis.partners.socle.Util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
*
* @author MAHLA Ilyasse Badreddine
*
*/
public class FileConverter {

	public static File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(UUID.randomUUID().toString() + " " + file.getOriginalFilename() ); // solution 1 avec UUID
//		File convertedFile = new File(Thread.currentThread().getName() + file.getOriginalFilename()); // solution 2 avec thread
		FileOutputStream writingoutput = new FileOutputStream(convertedFile);
        writingoutput.write(file.getBytes());
        writingoutput.close();
        return convertedFile;
    }

	public static String cleanTemporary(File file) throws IOException {
		boolean output = file.delete();
		String message;
		if(output) 
			message = "Temporary file deleted successfully";
		else 
			message = "Deleting operation failed";
			
		return message; 
	}

}
