package edu.fisa.lab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimeController {

	@GetMapping("getdata")
	public String get() {
		System.out.println("***");
		return "simple get";
	}
}

