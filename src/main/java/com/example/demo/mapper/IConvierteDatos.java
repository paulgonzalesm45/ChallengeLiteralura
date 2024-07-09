package com.example.demo.mapper;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
