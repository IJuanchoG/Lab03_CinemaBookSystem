package edu.eci.arsw.cinema.ui;

import edu.eci.arsw.cinema.services.CinemaServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CinemaUI {

    public static void main(String a[]) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices gc = ac.getBean(CinemaServices.class);
        System.out.println(gc.getCinemaByName("HO"));
    }
}
