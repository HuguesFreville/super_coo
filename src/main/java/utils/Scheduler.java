package utils;

import ODE.Adder;
import ODE.IntegrateurEvenements;
import ODE.IntegrateurTemps;
import chart.Chart;
import chart.ChartFrame;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {

    private float t = 0;
    private final float tEnd = 3f;
    private List<Component> components;
    private IO io;

    public Scheduler(IO io, Component... components) {
        this.io = io;
        this.components = Arrays.asList(components);
    }

    public void run() throws Exception {

        ChartFrame frame = new ChartFrame("oui", "oui");
        Chart chart1 = new Chart("q");

        Chart chart2 = new Chart("integE");
        frame.addToLineChartPane(chart2);
        chart2.setIsVisible(true);

        Chart chart3 = new Chart("integT");


        for (Component c : components)
            c.init();

        while (t < tEnd) {

            float trMin = (float) components.stream().mapToDouble(c -> c.tr).min().getAsDouble();
            Collection<Component> imms = components.stream().filter(c -> c.tr == trMin).collect(Collectors.toList());

            t += trMin;

            for (Component c : imms) {
                System.out.println("T" + t + " | Lambda sur le composant " + c.nom + " | " + c.currentEtat);
                c.lambda();
            }


            //A décommenter pour avoir l'affichage des intégrateurs
/*
            Adder adder = (Adder) components.get(4);
            if (adder.outputs.containsKey("result"))

                frame.addToLineChartPane(chart1);
                chart1.setIsVisible(true);
                chart1.addDataToSeries(t, (float) adder.outputs.get("result"));

            if (components.get(5).outputs.containsKey("result")) {
                Float val = (Float) components.get(5).outputs.get("result");
                chart2.addDataToSeries(t, val);
            }
            if(components.size()>6){
                frame.addToLineChartPane(chart3);
                chart3.setIsVisible(true);
                if (components.get(6).outputs.containsKey("result")) {
                    Float val2 = (Float) components.get(6).outputs.get("result");
                    chart3.addDataToSeries(t, val2);
                }
            }
*/




            //A décommenter pour avoir l'affichage de l'ODE du second ordre

            if (components.get(2).outputs.containsKey("result")) {
                Float val = (Float) components.get(2).outputs.get("result");
                chart2.addDataToSeries(t, val);
            }
            if (components.get(2).outputs.containsKey("result")) {
                Float val = (Float) components.get(2).outputs.get("result");
                chart2.addDataToSeries(t, val);
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


        }
    }
}
