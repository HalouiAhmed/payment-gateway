package effyis.partners.socle.service.amazon;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
@Service
public class FileStoreSerciceImpl implements FileStoreService{
	@Autowired
	private AmazonS3 s3;
	public void save(String path,
            String fileName,
            Optional<Map<String, String>> optionalMetadata,
            java.io.InputStream inputStream) {
ObjectMetadata metadata = new ObjectMetadata();

optionalMetadata.ifPresent(map -> {
   if (!map.isEmpty()) {
       map.forEach(metadata::addUserMetadata);
   }
});

try{
   s3.putObject(path, fileName, inputStream, metadata);
} catch (AmazonServiceException e) {
   throw new IllegalStateException("Echec de stockage de fichier vers s3", e);
}
}

public byte[] download(String path, String key) {
try{
   S3Object object = s3.getObject(path, key);
   return IOUtils.toByteArray(object.getObjectContent());
} catch (AmazonServiceException | IOException e) {
   throw new IllegalStateException("Failed to download file from s3", e);
}
}
}
