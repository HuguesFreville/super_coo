package gbp;

import utils.Component;
import utils.Etat;

public class P extends Component {

    P() {
        super();
        etats.put("free", new Etat("free", Integer.MAX_VALUE));
        etats.put("busy", new Etat("busy", 3));
        init();
    }


    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        switch (currentEtat.nom) {
            case "free":
                if (inputs.containsKey("req")) {
                    return etats.get("busy");
                }
                break;
            case "busy":
                break;
        }
        throw new Exception("Pas possible d'arriver là :/");
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom) {
            case "free":
                break;
            case "busy":
                return etats.get("free");
        }
        throw new Exception("Pas possible d'arriver là :/");
    }

    @Override
    public void lambdaImpl(Etat s) {
        switch (currentEtat.nom) {
            case "free":
                break;
            case "busy":
                outputs.put("done", true);
                break;
        }
    }

    @Override
    public void init() {
        currentEtat = etats.get("free");
    }
}
