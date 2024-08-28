package com.forest.forest_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.forest.forest_server.form.RegisterForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForestServerApplicationTests {

	@Test
	void contextLoads() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		String json = "{\"birthdate\":\"1999-07-29\",\"sex\":\"M\",\"aphasiaType\":\"type1\"}";
		try {
			RegisterForm form = mapper.readValue(json, RegisterForm.class);
			System.out.println("Deserialized Object: " + form.getBirthdate());

			String serializedJson = mapper.writeValueAsString(form);
			System.out.println("Serialized JSON: " + serializedJson);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
