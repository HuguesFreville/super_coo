package ODE;

import utils.Component;
import utils.Etat;

public class Adder extends Component {

    public float val;

    public Adder(String nom) {
        super(nom);
        etats.put("calcul", new Etat("calcul", 0));
        etats.put("attente", new Etat("attente", Float.MAX_VALUE));
    }

    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        switch (currentEtat.nom){
            case "attente":
                if(inputs.size() != 0){
                    return etats.get("calcul");
                }
        }
        return currentEtat;
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom){
            case "attente":
            case "calcul":
                return etats.get("attente");
        }
        throw new Exception("ca doit pas aller là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("calcul")) {
            for (Object o : inputs.values()) {
                float f = (float) o;
                val += f;
            }
            outputs.put("result", val);
            System.out.println("Valeur résultat out : " + val);
        }

    }

    @Override
    protected void initImpl() {
        currentEtat = etats.get("attente");
    }
}
