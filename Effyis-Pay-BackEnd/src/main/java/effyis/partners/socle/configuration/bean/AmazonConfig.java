package effyis.partners.socle.configuration.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {
	@Value("${aws.credentials.access-key")
	private String access_key;
	@Value("${aws.credentials.secret-key")
	private String secret_key;
	
	@Bean
	public AmazonS3 s3() {
		AWSCredentials aWSCredentials=new BasicAWSCredentials("AKIA2UINAVAJ2TLRG6AS","2miS3Zer534B656KTtt8WyBiPaGubEjptA24j+TV");
		 return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(aWSCredentials))
                .withRegion(Regions.EU_WEST_3)
                .build();
	}

}
