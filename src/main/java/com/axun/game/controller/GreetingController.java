package com.axun.game.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import com.axun.game.entity.Greeting;
import com.axun.game.entity.HelloMessage;
@Controller
public class GreetingController {


	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public String greeting(HelloMessage message) throws Exception {
//		Thread.sleep(1000); // simulated delay
		return HtmlUtils.htmlEscape(message.getName());
//		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

}
