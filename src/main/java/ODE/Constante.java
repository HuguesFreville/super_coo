package ODE;

import utils.Component;
import utils.Etat;

public class Constante extends Component {


    public Constante(String nom) {
        super(nom);
        etats.put("1", new Etat("1", 0));
        etats.put("2", new Etat("2", Float.MAX_VALUE));
    }

    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        return etats.get("2");
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        return etats.get("2");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("1")) {
            outputs.put("out", -9.81f);
        }
    }

    @Override
    protected void initImpl() {
        currentEtat = etats.get("1");
    }
}
