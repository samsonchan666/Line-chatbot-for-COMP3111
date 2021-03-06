package com.example.bot.spring;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.DatabaseEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.data.repository.CrudRepository;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = { KitchenSinkTester.class, DatabaseEngine.class })
@SpringBootTest(classes = { KitchenSinkTester.class, SQLDatabaseEngine.class })
public class KitchenSinkTester {

	@Autowired
	private DatabaseEngine databaseEngine;

	@Test
	public void testNotFound() throws Exception {
		boolean thrown = false;
		try {
			this.databaseEngine.search("no");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}

	@Test
	public void testFound() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("abc");
		} catch (Exception e) {
			thrown = true;
		}

		assertThat(!thrown);
		assertThat(result.equals("def"));
		assert(result.equals("def"));
	}


	@Test
	public void anothertestFound() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("I am fine");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(!thrown);
		assertThat(result.equals("Great!"));
	}
	
	@Test
	public void anothertestFound2() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("Abc");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(!thrown);
		assertThat(result.equals("def"));
	}

	@Test
	public void tourTest() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("Yangshan Hot Spring Tour");
		} catch (Exception e) {
			thrown = true;
		}
		assert(result.equals("1.  2D002\tYangshan Hot Spring Tour\n"));
	}

}
