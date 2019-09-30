package utils;

import ODE.Adder;
import chart.Chart;
import chart.ChartFrame;
import gbp.B;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {

    private float t = 0;
    private final float tEnd = 20;
    private List<Component> components;
    private IO io;

    public Scheduler(IO io, Component... components) {
        this.io = io;
        this.components = Arrays.asList(components);
    }

    public void run() throws Exception {

        ChartFrame frame = new ChartFrame("oui", "oui");
        Chart chart = new Chart("q");
        frame.addToLineChartPane(chart);
        chart.setIsVisible(true);

        for (Component c : components)
            c.init();

        while (t < tEnd) {

            float trMin = (float) components.stream().mapToDouble(c -> c.tr).min().getAsDouble();
            Collection<Component> imms = components.stream().filter(c -> c.tr == trMin).collect(Collectors.toList());


            Adder adder = (Adder) components.get(4);
            if (adder.outputs.containsKey("result"))
                chart.addDataToSeries(t, (float) adder.outputs.get("result"));

            t += trMin;

            for (Component c : imms) {
                System.out.println("T" + t + " | Lambda sur le composant " + c.nom + " | " + c.currentEtat);
                c.lambda();
            }

            io.updateInOut();

            for (Component c : components) {
                boolean ins = !c.inputs.values().isEmpty();

                if (imms.contains(c)) {
                    if (!ins) {
                        String ancienEtat = c.currentEtat.nom;
                        c.internal();
                        System.out.println("T" + t + " | Internal sur le composant " + c.nom + " | " + ancienEtat + " -> " + c.currentEtat);
                        c.tl = t;
                        c.e = 0;
                        c.tr = c.currentEtat.duree;
                        c.tn = t + c.tr;
                    } else {
                        throw new Exception("conflit");
                    }
                } else if (ins) {
                    String ancienEtat = c.currentEtat.nom;
                    c.external();
                    System.out.println("T" + t + " | External sur le composant " + c.nom + " | " + ancienEtat + " -> " + c.currentEtat);
                    c.tl = t;
                    c.e = 0;
                    c.tr = c.currentEtat.duree;
                    c.tn = t + c.tr;
                } else {
                    //System.out.println("T" + t + " | composant " + c.nom + " | " + c.currentEtat);
                    c.e = t - c.tl;
                    c.tr = c.tr - trMin;
                }
            }


            //TODO
            //Faut foutre les entrées dans les sorties des autres mais c'est de la merde alors faut pouvoir stocker
            // qui va où et ca pue la merde


        }
    }
}
