import edu.eci.arsw.cinema.filter.CinemaFilter;
import edu.eci.arsw.cinema.filter.CinemaFilterException;
import edu.eci.arsw.cinema.filter.impl.FilterByAvailability;
import edu.eci.arsw.cinema.filter.impl.FilterByGender;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;


public class FilterTest {

    CinemaFilter filterByGenre = new FilterByGender();

    CinemaFilter filterByAvailability = new FilterByAvailability();

    Cinema c = null;
    CinemaFunction funct1,funct2;

    @Before
    public void setup() {

        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate);
        funct2 = new CinemaFunction(new Movie("The Night", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        c = new Cinema("cinemaX", functions);
    }

    @Test
    public void debeFiltrarPorGenero() {
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functionsResult = new ArrayList<>();
        List<CinemaFunction> functionsExpected = new ArrayList<>();
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        try {

            functionsResult.addAll(filterByGenre.filterBy(c.getFunctions(), "Action"));


        } catch (CinemaFilterException e) {
            fail();
        }
        for (int i = 0; i < functionsExpected.size(); i++) {
            Assert.assertEquals(functionsExpected.get(i).toString(), functionsResult.get(i).toString());
        }
    }

    @Test
    public void noDebeFiltrarPorGenero() {
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functionsResult = new ArrayList<>();
        List<CinemaFunction> functionsExpected = new ArrayList<>();
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        try {

            functionsResult.addAll(filterByGenre.filterBy(c.getFunctions(), "Horror"));


        } catch (CinemaFilterException e) {
            fail();
        }
        Assert.assertNotEquals(functionsResult, functionsExpected);
    }

    @Test
    public void debeFiltrarPorDisponibilidad() {
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functionsResult = new ArrayList<>();
        List<CinemaFunction> functionsExpected = new ArrayList<>();
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        functionsExpected.add(new CinemaFunction(new Movie("The Night", "Horror"), functionDate));
        try {
            functionsResult.addAll(filterByAvailability.filterBy(c.getFunctions(), "5"));


        } catch (CinemaFilterException e) {
            fail();
        }
        functionsExpected.sort((x,y) -> x.hashCode());
        functionsResult.sort((x,y) -> x.hashCode());
        for (int i = 0; i < functionsExpected.size(); i++) {
            Assert.assertEquals(functionsExpected.get(i).toString(), functionsResult.get(i).toString());
        }
    }

    @Test
    public void noDebeFiltrarPorDisponibilidad() {
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functionsResult = new ArrayList<>();
        List<CinemaFunction> functionsExpected = new ArrayList<>();
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        try {
            functionsResult.addAll(filterByAvailability.filterBy(c.getFunctions(), "7000"));

        } catch (CinemaFilterException e) {
            fail();
        }
        Assert.assertNotEquals(functionsResult, functionsExpected);
    }

    @Test
    public void noDebeFiltrarPorDisponibilidad2() {
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functionsResult = new ArrayList<>();
        List<CinemaFunction> functionsExpected = new ArrayList<>();
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        functionsExpected.add(new CinemaFunction(new Movie("SuperHeroes Movie", "Action"), functionDate));
        try {

            functionsResult.addAll(filterByAvailability.filterBy(c.getFunctions(), "No Soy un Numero"));
            fail();

        } catch (CinemaFilterException e) {
            Assert.assertEquals(CinemaFilterException.FILTER_NUMERIC_EXPECTED, e.getMessage());
        }
    }
}
