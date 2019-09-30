package ODE;

import utils.Component;
import utils.Etat;

public class Step extends Component {

    public float xi;
    public float xf;



    public Step(float valInit, float valFin, float ts) {
        super("step");
        xi = valInit;
        xf = valFin;
        etats.put("fin", new Etat("fin", Float.MAX_VALUE));
        etats.put("deb", new Etat("deb", ts));
    }

    public Step() {
        super("step");
        xi = 0;
        xf = 0;
        etats.put("fin", new Etat("fin", Float.MAX_VALUE));
        etats.put("deb", new Etat("deb", 0));
    }

    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        return null;
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom){
            case "deb":
                return etats.get("fin");
            case "fin":
                break;
        }
        throw new Exception("pas possible d'arriver là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        switch (currentEtat.nom){
            case "deb":
                outputs.put("out",xi);
            case "fin":
                outputs.put("out",xf);
        }
    }

    @Override
    protected void initImpl() {
        currentEtat= etats.get("deb");
    }
}
