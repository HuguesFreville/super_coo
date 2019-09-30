package ODE;

import utils.Component;
import utils.Etat;

import java.util.HashMap;

public class Adder extends Component {

    public HashMap<String, Float> valMap;

    public Adder(String nom) {
        super(nom);
        valMap = new HashMap<>();
        etats.put("calcul", new Etat("calcul", 0));
        etats.put("attente", new Etat("attente", Float.MAX_VALUE));
    }

    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        switch (currentEtat.nom) {
            case "attente":
                if (inputs.size() != 0) {
                    for (String s : inputs.keySet()) {
                        valMap.put(s, (float) inputs.get(s));
                    }
                    return etats.get("calcul");
                }
        }
        return currentEtat;
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom) {
            case "attente":
            case "calcul":
                return etats.get("attente");
        }
        throw new Exception("ca doit pas aller là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("calcul")) {

            float val = 0;
            for (Object o : valMap.values()) {
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
