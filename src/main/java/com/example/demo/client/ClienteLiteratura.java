package com.example.demo.client;

import com.example.demo.entity.AutorEntity;
import com.example.demo.entity.LibroEntity;
import com.example.demo.mapper.ConvierteDatos;
import com.example.demo.model.Respuesta;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.LibroRepository;
import com.example.demo.service.ConsumoAPI;

import java.util.List;
import java.util.Scanner;

public class ClienteLiteratura {
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public ClienteLiteratura(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    //MENU PRINCIPAL
    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
					--------------Elija una opción--------------
						1.- Buscar libro por titulo
						2.- Lista libros registrados
						3.- Lista autores registrados
						4.- Lista autores vivos en un determinado año
						5.- Listar libros por idioma
						0 - Salir
					---------------------------------------------
					
					""";
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibros();
                    break;
                case 3:
                    buscarAutores();
                    break;
                case 4:
                    buscarAutoresVivo();
                    break;
                case 5:
                    buscarPorIdiomas();
                    break;
                case 0:
                    System.out.println("Adios");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }
    //OPCIONES DEL MENU
    //BUSCAR LIBRO EN LA API
    private void buscarLibroWeb() {

        Respuesta datos = getDatosLibro();

        if (!datos.results().isEmpty()) {
            LibroEntity libro = new LibroEntity(datos.results().get(0));
            libro = libroRepositorio.save(libro);
        }
        else{
            System.out.println("----- NO SE ENCONTRARON RESULTADOS ----");
        }

        System.out.println("\n\n---------- LIBRO -------\n");
        System.out.println(" Titulo: " + datos.results().get(0).title());
        System.out.println(" Autor: " +datos.results().get(0).autores());
        System.out.println(" Idioma: " + datos.results().get(0).languages());
        System.out.println(" Descargas: " + datos.results().get(0).download());
        System.out.println("\n-------------------------\n\n");
    }
    //BUSCAR LIBROS EN LA BD
    private void buscarLibros() {

        List<LibroEntity> libros = libroRepositorio.findAll();
        System.out.println("\n\n------------------------ LIBROS ------------------------ \n");
        if (!libros.isEmpty()) {

            for (LibroEntity libro : libros) {
                System.out.println("\n\n---------- LIBRO -------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }
            System.out.println("\n\n-------------------------------------------------------- \n");
        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");
        }

    }
    //BUSCAR AUTORES EN LA BD
    private void buscarAutores() {
        List<AutorEntity> autores = autorRepositorio.findAll();

        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n------------------------------------------------ \n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n\n------------------------------------------------ \n");
            }

        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");

        }

    }
    //BUSCAR AUTORES VIVOS EN UN DETERMINADO AÑO
    private void buscarAutoresVivo() {
        System.out.println("Escriba el año: ");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<AutorEntity> autores = autorRepositorio.findForYear(anio);
        System.out.println("\n\n------------------------ AUTORES VIVOS ------------------------ \n");
        if (!autores.isEmpty()) {
            for (AutorEntity autor : autores) {
                System.out.println("\n\n---------- AUTOR-------\n");
                System.out.println(" Nombre: " + autor.getNombre());
                System.out.println(" Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println(" Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println(" Libros: " + autor.getLibros().getTitulo());
                System.out.println("\n-------------------------\n\n");
            }
            System.out.println("\n\n-------------------------------------------------------- \n");
        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");

        }
    }
    //BUSCAR LIBROS POR IDIOMA
    private void buscarPorIdiomas() {

        var menu = """
				Seleccione un Idioma:
					1.- Español
					2.- Ingles
				
					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String seleccion = "";

        if(idioma == 1) {
            seleccion = "es";
        } else 	if(idioma == 2) {
            seleccion = "en";
        }

        List<LibroEntity> libros = libroRepositorio.findForLanguaje(seleccion);

        if (!libros.isEmpty()) {

            for (LibroEntity libro : libros) {
                System.out.println("\n\n---------- LIBROS POR IDIOMA-------\n");
                System.out.println(" Titulo: " + libro.getTitulo());
                System.out.println(" Autor: " + libro.getAutor().getNombre());
                System.out.println(" Idioma: " + libro.getLenguaje());
                System.out.println(" Descargas: " + libro.getDescargas());
                System.out.println("\n-------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");
        }


    }

    private Respuesta getDatosLibro() {
        System.out.println("Ingrese nombre del libro : ");
        var titulo = teclado.nextLine();
        titulo = titulo.replace(" ", "%20");
        System.out.println("Titulo : " + titulo );
        System.out.println(URL_BASE + titulo);
        var json = consumoApi.obtenerDatos(URL_BASE + titulo);
        Respuesta datos = conversor.obtenerDatos(json, Respuesta.class);
        return datos;
    }
}


