package gbp;

import utils.Component;
import utils.IO;
import utils.Scheduler;

public class Main {

    static G G = new G();
    static B B = new B();
    static P P = new P();

    static Scheduler scheduler = new Scheduler(new IO() {
        @Override
        public void updateInOut() {
            B.updateIn(G, P);
            P.updateIn(B);
            G.getOutputs().clear();
            B.getOutputs().clear();
            P.getOutputs().clear();
            System.out.println(B.q);
        }
    }, G, B, P);


    public static void main(String[] args) throws Exception {
        scheduler.run();
    }
}
