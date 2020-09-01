package edu.eci.arsw.cinema.filter;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;

import java.util.List;
import java.util.Set;

/**
 * The interface Cinema filter.
 */
public interface CinemaFilter {

    /**
     * Filter by list.
     *
     * @param property the property
     * @param cinemas  the cinemas
     * @return the list
     * @throws CinemaFilterException the cinema filter exception
     */
    List<CinemaFunction> filterBy(List<CinemaFunction> functions,String property) throws CinemaFilterException;
}
