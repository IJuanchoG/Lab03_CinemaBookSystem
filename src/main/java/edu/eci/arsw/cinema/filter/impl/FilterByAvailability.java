package edu.eci.arsw.cinema.filter.impl;

import edu.eci.arsw.cinema.filter.CinemaFilterException;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.filter.CinemaFilter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component("FilterByAvailability")
public class FilterByAvailability implements CinemaFilter{

    @Override
    public List<String[]> filterBy(String property, Set<Cinema> cinemas) throws  CinemaFilterException {
        final int numberOfSeats;
        try{
           numberOfSeats =  Integer.parseInt(property);
        } catch (NumberFormatException e){
            throw new CinemaFilterException(CinemaFilterException.FILTER_NUMERIC_EXPECTED);
        }
        List<String[]> info = new LinkedList<>();
        cinemas.forEach(cinema -> {
            cinema.getFunctions().forEach(x -> {
                AtomicInteger conta = new AtomicInteger();
                x.getSeats().forEach(row -> {
                    row.forEach(seat -> {if(seat.equals(true)) conta.getAndIncrement(); });
                });
                if (conta.get() >= numberOfSeats){
                    info.add(new String[]{cinema.getName(),x.getMovie().getName(), x.getDate()});
                }
            });
        });;
        return info;
    }
}
