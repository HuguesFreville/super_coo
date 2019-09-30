package gbp;

import utils.Component;
import utils.IO;
import utils.Scheduler;

public class Main {

    private static G G = new G();
    private static B B = new B();
    private static P P = new P();

    private static Scheduler scheduler = new Scheduler(() -> {
        B.updateIn(G, P);
        P.updateIn(B);
        G.getOutputs().clear();
        B.getOutputs().clear();
        P.getOutputs().clear();
        System.out.println(B.q);
    }, G, B, P);


    public static void main(String[] args) throws Exception {
        scheduler.run();
    }
}
