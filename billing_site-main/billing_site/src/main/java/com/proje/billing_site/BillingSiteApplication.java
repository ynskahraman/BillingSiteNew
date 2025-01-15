package com.proje.billing_site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public abstract class BillingSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingSiteApplication.class, args);

		//	CALISTIGINI TAM ANLAMAK ICIN PRINT ETTIRIYORUM
		System.out.println("Billing Site Application Started");
	}

}
