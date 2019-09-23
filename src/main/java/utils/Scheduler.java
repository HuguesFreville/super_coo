package utils;

import java.util.Collection;
import java.util.stream.Collectors;

public class Scheduler {

    private float t = 0;
    private final float tEnd = 10;

    private Collection<Component> components;

    void init() {
        for (Component c : components) {
            c.init();
        }
    }

    void run() {
        while (t < tEnd) {

            float trMin = (float) components.stream().mapToDouble(c -> c.currentEtat.duree).min().getAsDouble();
            Collection<Component> imms = components.stream().filter(c -> c.currentEtat.duree == trMin).collect(Collectors.toList());

            for (Component c : imms)
                c.lambda();

            //TODO
            //Faut foutre les entrées dans les sorties des autres mais c'est de la merde alors faut pouvoir stocker
            // qui va où et ca pue la merde


        }
    }
}
