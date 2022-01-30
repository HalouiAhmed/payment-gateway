package effyis.partners.socle.service.amazon;


import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {
	public void save(String path,
            String fileName,
            Optional<Map<String, String>> optionalMetadata,
            InputStream inputStream);
	public byte[] download(String path, String key);
}
