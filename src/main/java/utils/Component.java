package utils;

import java.util.*;

public abstract class Component {

    public HashMap<String, Object> inputs;
    public HashMap<String, Object> outputs;
    protected HashMap<String, Etat> etats;
    protected Etat currentEtat;

    public Component() {
        inputs = new HashMap<>();
        outputs = new HashMap<>();
        etats = new HashMap<>();
    }

    protected abstract Etat externalImpl(Etat q) throws Exception;

    protected Etat external(Etat etat) throws Exception {
        currentEtat = externalImpl(etat);
        return currentEtat;
    }

    protected abstract Etat internalImpl(Etat etat) throws Exception;

    protected Etat internal(Etat etat) throws Exception {
        currentEtat = internalImpl(etat);
        return currentEtat;
    }

    protected abstract void lambdaImpl(Etat s);

    public void lambda() {
        lambdaImpl(currentEtat);
    }

    public abstract void init();

    public Etat getCurrentEtat() {
        return currentEtat;
    }
}
