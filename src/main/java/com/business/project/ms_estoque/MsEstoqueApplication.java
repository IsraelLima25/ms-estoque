package com.business.project.ms_estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsEstoqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEstoqueApplication.class, args);
    }

    /**
     * 1 - Criar a instancia EC2 2 - Configurar o security group (abrir a porta 8080) 3 - Instalar o
     * git na instancia EC2 4 - Instalar o java 21 na instancia EC2 5 - Instalar o maven na
     * instancia EC2 6 - Clonar o repositorio do projeto na instancia EC2 7 - Construir o projeto
     * com o maven (mvn clean install) 8 - Rodar a aplicacao (java -jar
     * target/ms-estoque-0.0.1-SNAPSHOT.jar) 9 - Testar a aplicacao 10 - Configurar o banco de dados
     * RDS (MySQL) 11 - Configurar as variaveis de ambiente na instancia EC2 (DB_URL, DB_USERNAME,
     * DB_PASSWORD) 12 - Reiniciar a aplicacao 13 - Testar a aplicacao novamente
     */
}
