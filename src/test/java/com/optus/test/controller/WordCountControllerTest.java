package com.optus.test.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.optus.rest.controller.WordCountController;

@RunWith(SpringRunner.class)
@WebMvcTest(WordCountController.class)
public class WordCountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private WordCountController controller;

	@Test
	public void searchURLActive() throws Exception {
		
		/*this.mockMvc.perform(get("/counter-api/top/20").header("authorization", "Basic YWRtaW46YWRtaW4xMjM=").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
*/
	}
}
