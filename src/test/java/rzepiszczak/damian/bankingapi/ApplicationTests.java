package rzepiszczak.damian.bankingapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void WhenSendWrongRegisterData_ThenClientNotRegister() throws Exception {

		String registerData = "{ \"firstName\": \"Damian\", \"lastName\": \"Rzepiszczak\", \"login\":\"Aga\", \"password\":\"example\", \"confirmPassword\":\"example\", \"dayOfBirth\":\"2019-09-03\", \"acceptedPersonalData\": true }";

		this.mvc.perform(post("/client/register").contentType("application/json").content(registerData))
				.andExpect(status().isOk());
	}
}
