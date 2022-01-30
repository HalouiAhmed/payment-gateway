package effyis.partners.socle.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerMapping;

import effyis.partners.socle.dto.ExceptionDTO;
import io.jsonwebtoken.JwtException;

/**
 * 
 * @author Elkotb Zakaria
 *
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@Autowired
	private HttpServletRequest request;

	
	@ExceptionHandler(ElementNotFoundException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO idNotFoundException(ElementNotFoundException elementNotFoundException){
		ControllerExceptionHandler.LOG.error(elementNotFoundException.getMessage());
		ExceptionDTO exceptionDTO = this.initExceptionDTO(elementNotFoundException, elementNotFoundException.getMessage() ,400,	"Bad Request");
		return exceptionDTO;
	}

	
	@ExceptionHandler(CustomAuthenticationException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ExceptionDTO customExceptionHandler(CustomAuthenticationException customException) {
		ControllerExceptionHandler.LOG.error(customException.getMessage());
		ExceptionDTO exceptionDTO = this.initExceptionDTO(customException, customException.getMessage(), 401,
				"Bad Request");
		return exceptionDTO;
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO customExceptionHandler(MethodArgumentNotValidException e) {
		ControllerExceptionHandler.LOG.error(e.getMessage(),e);
		ExceptionDTO exceptionDTO = this.initExceptionDTO(e, e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), 401,
				"Bad Request");
		return exceptionDTO;
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO customExceptionHandler(DataIntegrityViolationException e) {
		ControllerExceptionHandler.LOG.error(e.getMessage());
		ExceptionDTO exceptionDTO = this.initExceptionDTO(e, e.getCause().getCause().getMessage(), 401,
				"Unauthorized");
		return exceptionDTO;
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ExceptionDTO accessDeniedException(AccessDeniedException exception) {
		ControllerExceptionHandler.LOG.error("unxpected error has happend ", exception);
		ExceptionDTO exceptionDTO = this.initExceptionDTO(exception, exception.getMessage(), 401, "Unauthorized");
		return exceptionDTO;
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ExceptionDTO authenticationException(AuthenticationException exception) {
		ControllerExceptionHandler.LOG.error("unxpected error has happend ", exception);
		ExceptionDTO exceptionDTO = this.initExceptionDTO(exception, exception.getMessage(), 403, "Forbidden");
		return exceptionDTO;
	}

	@ExceptionHandler(JwtException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ExceptionDTO jwtException(JwtException exception) {
		ControllerExceptionHandler.LOG.error("unxpected error has happend ", exception);
		ExceptionDTO exceptionDTO = this.initExceptionDTO(exception, exception.getMessage(), 403, "Forbidden");
		return exceptionDTO;
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionDTO maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
		ControllerExceptionHandler.LOG.error("unxpected error has happend ", e.getCause());
		ExceptionDTO exceptionDTO = this.initExceptionDTO(e, "Max Upload Size Exceeded", 400, "Bad Request");
		return exceptionDTO;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionDTO exception(Exception exception) {
		ControllerExceptionHandler.LOG.error("unxpected error has happend ", exception);
		ExceptionDTO exceptionDTO = this.initExceptionDTO(exception, "Internal Error", 500, "Internal Server error");
		return exceptionDTO;
	}

	private ExceptionDTO initExceptionDTO(Exception e, String message, int status, String error) {
		ExceptionDTO exceptionDTO = new ExceptionDTO();
		exceptionDTO.setTimestamp(LocalDateTime.now().toString());
		exceptionDTO.setStatus(status);
		exceptionDTO.setError(error);
		String path = (String) this.request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		exceptionDTO.setPath(path);
		exceptionDTO.setMessage(message);
		return exceptionDTO;
	}
}
