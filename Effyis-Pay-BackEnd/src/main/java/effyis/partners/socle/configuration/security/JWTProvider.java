package effyis.partners.socle.configuration.security;

import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import effyis.partners.socle.exception.CustomAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@Component
public class JWTProvider {

	@Autowired
	private MessageSource messageSource;

	public static HttpServletResponse addAuthorizedHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE,PATCH,HEAD");
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With,"
				+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		response.addHeader("Access-Control-Expose-Headers",
				"Access-Contol-Allow-Origin," + " Access-Control-Allow-Credentials, Authorization");
		return response;
	}

	public static boolean ifOption(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(200);
			return true;
		}
		return false;
	}

	public static String resolveJWT(HttpServletRequest request) {
		String jwt = request.getHeader("Authorization");
		if ((jwt != null) && jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7);
			return jwt;
		}
		return "";
	}

	public boolean validateJwt(String jwt, String secret) throws SignatureException {
		Jws<Claims> parsedJwt = JWTProvider.parseJwt(jwt, secret);
		if (!JWTProvider.isExpired(parsedJwt.getBody().getExpiration())) {
			return true;
		} else {
			throw new CustomAuthenticationException(
					this.messageSource.getMessage("jwt.expired", null, LocaleContextHolder.getLocale()));
		}
	}

	public static Jws<Claims> parseJwt(String jwt, String secret) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(jwt);
	}

	private static boolean isExpired(Date date) {
		return date.before(new Date(System.currentTimeMillis())) ? true : false;
	}

	public static String generateJWT(String login, String role, String secret, long expirationTime,Boolean verifiedMail) {
		String jwt = Jwts.builder().setSubject(login)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
				.claim("role", role).claim("verifiedMail", verifiedMail).compact();
		return jwt;
	}

}
