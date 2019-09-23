package gbp;

import utils.Component;
import utils.Etat;

public class B extends Component {

    int q = 0;

    public B() {
        super();
        etats.put("a", new Etat("a", Integer.MAX_VALUE));
        etats.put("b", new Etat("b", 0));
        etats.put("c", new Etat("c", Integer.MAX_VALUE));
        init();
    }

    @Override
    protected Etat externalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom) {
            case "a":
            case "b":
                if (inputs.containsKey("job")) {
                    q++;
                    return etats.get("b");
                }
                break;
            case "c":
                if (inputs.containsKey("job")) {
                    q++;
                    return etats.get("c");
                }
                if (inputs.containsKey("done")) {
                    if (q > 0) {
                        return etats.get("b");
                    }
                    if (q == 0) {
                        return etats.get("a");
                    }
                }
                break;
        }
        throw new Exception("Pas possible d'arriver là :/");
    }

    @Override
    protected Etat internalImpl(Etat etat) throws Exception {
        switch (currentEtat.nom) {
            case "a":
                break;
            case "b":
                q--;
                return etats.get("c");
            case "c":
                break;
        }
        throw new Exception("Pas possible d'arriver là :/");
    }

    @Override
    public void lambdaImpl(Etat s) {
        switch (currentEtat.nom) {
            case "a":
            case "c":
                break;
            case "b":
                outputs.put("req", true);
                break;
        }
    }

    @Override
    public void init() {
        currentEtat = etats.get("a");
        q = 0;
    }
}
