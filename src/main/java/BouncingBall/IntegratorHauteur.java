package BouncingBall;

import ODE.IntegrateurEvenements;
import utils.Etat;

public class IntegratorHauteur extends IntegrateurEvenements {


    private final float hauteurInit;

    public IntegratorHauteur(String nom, float deltaT, float deltaQ, float h0) {
        super(nom, deltaT, deltaQ);
        this.hauteurInit=h0;
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
            if(val<0) val=0;
            outputs.put("result", val);
            System.out.println("Valeur résultat d'hauteur out : " + val);
        }

    }

    @Override
    protected void initImpl() {
        currentEtat = etats.get("attente");
        prev = 0;
        val = hauteurInit;
    }
}
