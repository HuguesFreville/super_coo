package ODE;

import utils.Component;
import utils.Etat;

public class IntegrateurTemps extends Component {

    public float val;
    private float hstep;
    private float prev;

    public IntegrateurTemps(String nom, float hstep) {
        super(nom);
        this.hstep = hstep;
        this.prev = 1;
        etats.put("calcul", new Etat("calcul", hstep*0.001f));
        etats.put("attente", new Etat("attente", hstep));
    }

    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        switch (currentEtat.nom) {
            case "attente":
                if (inputs.size() != 0) {
                    return etats.get("calcul");
                }
        }
        return currentEtat;
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom) {
            case "attente":

                return etats.get("calcul");
            case "calcul":
                return etats.get("attente");
        }
        throw new Exception("ca doit pas aller là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("calcul")) {
            if (inputs.isEmpty()) {
                val += hstep * prev;
            } else {
                for (Object o : inputs.values()) {
                    float f = (float) o;
                    prev = f;
                    val += hstep * f;
                }
            }
            outputs.put("result", val);
            System.out.println("Valeur résultat out : " + val);
        }

    }

    @Override
    protected void initImpl() {
        currentEtat = etats.get("attente");
        prev = 0;
        val = 0;
    }
}

