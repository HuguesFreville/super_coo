package BouncingBall;

import utils.Component;
import utils.Etat;

public class Neg extends Component {
    public Neg(String nom) {
        super(nom);
        etats.put("test",new Etat("test", Float.MAX_VALUE));
    }

    @Override
    protected Etat externalImpl(Etat q) throws Exception {
        switch (currentEtat.nom) {
            case "test":
                if (inputs.size() != 0) {
                    if((float)inputs.get("result")<0f){
                        return currentEtat;
                    }
                }
        }
        return currentEtat;
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        return currentEtat;
    }

    @Override
    protected void lambdaImpl(Etat s) {
        if(currentEtat.nom.equals("test")){
            outputs.put("result",inputs.get("result"));
        }
    }

    @Override
    protected void initImpl() {
        currentEtat=etats.get("test");
    }
}
