package edu.eci.arsw.cinema.filter;

public class CinemaFilterException extends Exception{
    public static final String FILTER_NUMERIC_EXPECTED = "Property Invalid";

    public CinemaFilterException(String message){
        super(message);
    }
}
