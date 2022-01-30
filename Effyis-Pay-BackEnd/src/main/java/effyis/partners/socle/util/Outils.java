package effyis.partners.socle.Util;

import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class Outils {
	public String  generateOtp(int maxDigits) {
		Random rand = new Random();
		StringBuilder otp = new StringBuilder(maxDigits);
		for(int i=0;i<maxDigits;i++) {
		 otp.append(String.valueOf(rand.nextInt(9))); 
		}
		return new String(otp);
		
	}
	
}
