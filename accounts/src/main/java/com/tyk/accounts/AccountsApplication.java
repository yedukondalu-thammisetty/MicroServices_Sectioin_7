package com.tyk.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tyk.accounts.dto.AccountsContactInfoDto;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(title = "Accounts microServices REST API Documentation",
                                 description = "TYKRHIT Account Microserevices Of REST Api Documentation",
                                 version = "v1",
                                 contact = @Contact(name = "Yedukondalu Thammisetty",email = "yedukondalu8978@gmail.com",url = "www.tykrohithacount.com"),
                                 license = @License(name = "Apache 2.0",url = "www.tykrohithacount.com"))
                                 )
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
