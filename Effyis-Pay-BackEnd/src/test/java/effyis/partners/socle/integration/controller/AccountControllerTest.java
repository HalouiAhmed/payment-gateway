/*
package effyis.partners.socle.integration.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import effyis.partners.socle.SocleEffyisApplication;
import effyis.partners.socle.dto.AuthenticationDTO;
import effyis.partners.socle.dto.JwtDTO;

*/
/**
 * 
 * @author EL KOTB ZAKARIA
 *
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SocleEffyisApplication.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	public void authenticationTest() throws Exception {
		AuthenticationDTO authenticationDTO = new AuthenticationDTO();
		authenticationDTO.setLogin("zakaria");
		authenticationDTO.setPassword("password");
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer();
		String requestBody = writer.writeValueAsString(authenticationDTO);
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post("/effyis/api/accounts/login")
				.contentType("application/json").content(requestBody)).andReturn();
		JwtDTO resultDto = mapper.readValue(result.getResponse().getContentAsString(), JwtDTO.class);
		Assert.assertNotNull(resultDto.getJwt());
	}
	
}
*/
