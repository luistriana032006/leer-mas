package com.devsenior.luistriana.leer_mas.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // CUANDO EL SERVIDOR ARRANQUE ANOTA ESTA CLASE COMO UNA CLASE DE CONFIGURACION
public class ModelMapperConfig {

@Bean // CREA UN BEAN PARA QUE SPRING BOOT LO PUEDA GESTIONAR
public ModelMapper modelMapper(){

ModelMapper mapper = new ModelMapper();
mapper.getConfiguration().setSkipNullEnabled(true) // IGNORAR CAMPOS NULLOS 
.setMatchingStrategy(MatchingStrategies.STRICT); // SOLO MAPEO CAMPOS CON NOMBRE EXACTOS

return mapper;
}
}
