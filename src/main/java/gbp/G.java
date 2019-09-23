package gbp;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.Component;
import utils.Etat;

public class G extends Component {

    G() {
        super();
        etats.put("s", new Etat("s", 2));
        init();
    }

    @Override
    public Etat externalImpl(Etat q) {
        throw new NotImplementedException();
    }

    @Override
    public Etat internalImpl(Etat etat) {
        return etats.get("s");
    }

    @Override
    public void lambdaImpl(Etat s) {
        outputs.put("job", true);
    }

    @Override
    public void init() {
        currentEtat = etats.get("s");
    }
}
