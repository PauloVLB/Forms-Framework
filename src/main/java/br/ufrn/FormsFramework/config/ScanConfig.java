package br.ufrn.FormsFramework.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ScanConfig {

    @Profile("DASH")
    @Configuration
    @ComponentScan(basePackages = {"br.ufrn.FormsFramework","br.ufrn.instancies.DASH"})
    @EntityScan(basePackages = {"br.ufrn.FormsFramework","br.ufrn.instancies.DASH"})
    class DashConfig {

    }

    @Profile("GRAB")
    @Configuration
    @ComponentScan(basePackages = {"br.ufrn.FormsFramework","br.ufrn.instancies.GRAB"})
    @EntityScan(basePackages = {"br.ufrn.FormsFramework","br.ufrn.instancies.GRAB"})
    class GrabConfig {

    }
    
    @Profile("JUMP")
    @Configuration
    @ComponentScan(basePackages = {"br.ufrn.FormsFramework","br.ufrn.instancies.JUMP"})
    @EntityScan(basePackages = {"br.ufrn.FormsFramework","br.ufrn.instancies.JUMP"})
    class JumpConfig {

    }
}
