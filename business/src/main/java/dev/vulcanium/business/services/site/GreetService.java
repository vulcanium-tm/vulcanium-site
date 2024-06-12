package dev.vulcanium.business.services.site;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@VaadinSessionScope
public class GreetService{
public String greet(String name){
	if (name==null || name.isEmpty()){
		return "Welcome to Vulcanium!";
	}
	else{
		return "Hello " + name + ",welcome to vulcanium";
	}
}
}
