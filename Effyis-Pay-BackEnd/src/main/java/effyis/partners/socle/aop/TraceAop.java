package effyis.partners.socle.aop;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import effyis.partners.socle.dto.TraceDTO;

/**
 * 
 * @author EL KOTB ZAKARIA
 *
 */
@Aspect
@Component
public class TraceAop {

	private Logger LOGGER = LoggerFactory.getLogger(TraceAop.class);

	@AfterReturning(pointcut = "execution(* effyis.partners.*.controller.*.*(..))", returning = "result")
	@Async
	public void getControllerTrace(JoinPoint joinpoint, Object result) {
		String methodName = joinpoint.getSignature().getName();
		List<String> input = Arrays.asList(joinpoint.getArgs()).stream().map(Object::toString)
				.collect(Collectors.toList());
		TraceDTO traceDTO = new TraceDTO();
		traceDTO.setInput(input);
		traceDTO.setMethodName(methodName);
		if (result != null) {
			traceDTO.setOutput(result.toString());
		}
		this.LOGGER.info(traceDTO.toString());
	}
}
