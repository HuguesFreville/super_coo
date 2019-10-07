package ODE;

import utils.Component;
import utils.Etat;

import static java.lang.Math.abs;

public class IntegrateurEvenements extends Component {

    public float val;
    protected float deltaT;
    private float deltaQ;
    protected float prev;

    public IntegrateurEvenements(String nom, float deltaT, float deltaQ) {
        super(nom);
        this.deltaT = deltaT;
        this.deltaQ = deltaQ;
        this.prev = 0;
        etats.put("calcul", new Etat("calcul", deltaT*0.001f));
        etats.put("attente", new Etat("attente", deltaT));
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
                deltaT = deltaQ / abs(prev);
                return new Etat("attente", deltaT);
        }
        throw new Exception("ca doit pas aller là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("calcul")) {
            if (inputs.isEmpty()) {
                val += deltaT*prev;
            } else {
                for (Object o : inputs.values()) {
                    float f = (float) o;
                    prev = f;
                    val += deltaT * f;
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

