package org.example.lojajoias;

import jakarta.annotation.Resource;
import org.example.lojajoias.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class LojaJoiasApplication {

    public static void main(String[] args) {

        SpringApplication.run(LojaJoiasApplication.class, args);
    }
}
/*
    @Resource
    FileStorageService storageService;

    @Autowired
    UsuarioService service;

    @Override
    public void run(String... args) throws Exception {

        var criptografa = new BCryptPasswordEncoder();

        storageService.deleteAll();
        storageService.init();

        Usuario u1 = new Usuario("user", criptografa.encode("user"), false);
        Usuario u2 = new Usuario("admin", criptografa.encode("admin"), true);

        service.create(u1);
        service.create(u2);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }
}

}
*/