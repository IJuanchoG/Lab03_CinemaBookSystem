package edu.eci.arsw.cinema.filter.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.filter.CinemaFilter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

@Component("FilterByGender")
public class FilterByGender  implements CinemaFilter{

    @Override
    public List<String[]> filterBy(String property, Set<Cinema> cinemas) {

        List<String[]> info = new LinkedList<>();
        cinemas.forEach(cinema -> {
            cinema.getFunctions().forEach(x -> {
                if(x.getMovie().getGenre().equals(property)){
                    info.add(new String[]{cinema.getName(),x.getMovie().getName(), x.getDate()});
                };
            });
        });
        return info;
    }
}
