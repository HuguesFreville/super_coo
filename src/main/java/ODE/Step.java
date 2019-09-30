package ODE;

import utils.Component;
import utils.Etat;

public class Step extends Component {

    private float xi;
    private float xf;
    private String out;

    Step(String nom, float valInit, float valFin, float ts, String out) {
        super(nom);
        this.out = out;
        xi = valInit;
        xf = valFin;
        etats.put("init", new Etat("init",0));
        etats.put("fin", new Etat("fin", Float.MAX_VALUE));
        etats.put("deb", new Etat("deb", ts));

    }


    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        return null;
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom) {
            case"init":
                return etats.get("deb");
            case "deb":
            case "fin":
                return etats.get("fin");
        }
        throw new Exception("pas possible d'arriver là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        switch (currentEtat.nom) {
            case "init":
                outputs.put(out, xi);
                break;
            case "deb":
                outputs.put(out, xf);
                break;
        }
    }

    @Override
    protected void initImpl() {
        currentEtat = etats.get("init");
    }
}
