/*
package effyis.partners.socle.unit.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import effyis.partners.socle.configuration.security.JWTProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

*/
/**
 * 
 * @author EL KOTB ZAKARIA
 *
 *//*

@SpringBootTest
public class JwtProviderTest {

	@Test
	public void generateJWTTest() {
		String jwt = JWTProvider.generateJWT("zakaria", "ADMIN", "secret", 120000, false);
		Assert.assertNotNull(jwt);
	}

	@Test
	public void parseJwtTest() {
		String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyaWVtYW5uIiwiZXhwIjoxNjE3MTQ2NjYyLCJyb2xlIjoiREVGQVVMVCJ9.xVUHEUlXF9xMX16rEH4ZLgzgNlZ0r_XjgeLEjs-L87b4jJI9CnOzCJDpT5Pneathne7en7J0Cz8-mmDq_oC7mg";
		Jws<Claims> parsedJwt = JWTProvider.parseJwt(jwt, "secret");
		Assert.assertEquals("riemann", parsedJwt.getBody().getSubject());
	}
}
*/
