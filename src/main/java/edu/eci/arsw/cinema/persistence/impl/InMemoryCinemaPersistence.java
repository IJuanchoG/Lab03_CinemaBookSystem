/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author cristian
 */
@Component("InMemoryCinema")
public class InMemoryCinemaPersistence implements CinemaPersitence{
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("cinemaX",functions);
        cinemas.put("cinemaX", c);
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {

        List<CinemaFunction> cinemaFunctions =cinemas.get(cinema).getFunctions();
        Optional<CinemaFunction> cf = cinemaFunctions.stream().filter(cinemaFunction -> {
            if(cinemaFunction.getMovie().getName().equals(movieName) && cinemaFunction.getDate().equals(date)) {
                return true;
            }
            return false;
        }).findFirst();
        if(cf.isPresent()){
            CinemaFunction cinemaFunction = cf.get();
            cinemaFunction.buyTicket(row, col);
            System.out.println("Compra exitosa, para la funcion: "+cinemaFunction.getMovie().getName()+" con horario: "+cinemaFunction.getDate());
        }else{
            throw new CinemaException(CinemaPersistenceException.NO_FOUND_CINEMA_FUNCTION);
        }
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
        List<CinemaFunction> cinemaFunctions = cinemas.get(cinema).getFunctions();
        List<CinemaFunction> cinemaFunctionList =
                cinemaFunctions.stream().filter(cinemaFunction -> cinemaFunction.getDate().equals(date)).collect(Collectors.toList());
        return cinemaFunctionList;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
        return cinemas.get(name);
    }

    @Override
    public Set<Cinema> getAllCinemas() throws CinemaPersistenceException {
        return (Set<Cinema>) cinemas.values();
    }

    @Override
    public List<String[]> getFunctionsbyGenre(String genre) {
        Collection<Cinema> allcinemas = cinemas.values();
         List<String[]> info = new LinkedList<>();
        allcinemas.forEach(cinema -> {
            cinema.getFunctions().forEach(x -> {
                if(x.getMovie().getGenre().equals(genre)){
                    info.add(new String[]{cinema.getName(),x.getMovie().getName(), x.getDate()});
                };
            });
        });
        return info;
    }
}
