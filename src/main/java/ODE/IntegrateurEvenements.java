package ODE;

import utils.Component;
import utils.Etat;

import static java.lang.Math.abs;

public class IntegrateurEvenements extends Component {

    public float val;
    private float hstep;
    private float deltaQ;
    private float prev;

    public IntegrateurEvenements(String nom, float hstep, float deltaQ) {
        super(nom);
        this.hstep=hstep;
        this.deltaQ = deltaQ;
        this.prev=1;
        etats.put("calcul", new Etat("calcul", 0));
        etats.put("attente", new Etat("attente", hstep));
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
                return etats.get("calcul");
            case "calcul":
                hstep=deltaQ/abs(prev);
                return new Etat("attente", hstep);
        }
        throw new Exception("ca doit pas aller là");
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("calcul")) {
            if(inputs.isEmpty()){
                val += hstep;
            }else{
                for (Object o : inputs.values()) {
                    float f = (float) o;
                    prev=f;
                    val += hstep*f;
                }
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

