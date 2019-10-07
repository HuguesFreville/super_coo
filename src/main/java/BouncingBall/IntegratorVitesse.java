package BouncingBall;

import ODE.IntegrateurEvenements;
import utils.Etat;

public class IntegratorVitesse extends IntegrateurEvenements {

    private float coefR;

    public IntegratorVitesse(String nom, float deltaT, float deltaQ, float coefR) {
        super(nom, deltaT, deltaQ);
        this.coefR=coefR;
    }
    @Override
    protected void lambdaImpl(Etat s) {
        if (currentEtat.nom.equals("calcul")) {
            if (inputs.isEmpty()) {
                val += deltaT*prev;
            } else {
                for (Object o : inputs.values()) {
                    float f = (float) o;
                    if(f<=0) {
                        prev = prev * coefR;
                        val=-val+prev;
                    }else{
                        val+=deltaT*prev;
                    }
                }
            }
            outputs.put("result", val);
            System.out.println("Valeur résultat de vitesse out : " + val);
        }

    }

    @Override
    protected void initImpl() {
        currentEtat = etats.get("attente");
        prev = -9.81f;
        val = 0;
    }
}
