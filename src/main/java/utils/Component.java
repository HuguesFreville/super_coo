package utils;

import java.util.*;

public abstract class Component {


    String nom;
    public float tl;
    public float tn;
    public float tr;
    public float e;
    protected HashMap<String, Object> inputs;

    public HashMap<String, Object> getOutputs() {
        return outputs;
    }

    protected HashMap<String, Object> outputs;
    protected HashMap<String, Etat> etats;
    protected Etat currentEtat;

    public Component(String nom) {
        this.nom = nom;
        inputs = new HashMap<>();
        outputs = new HashMap<>();
        etats = new HashMap<>();
    }

    protected abstract Etat externalImpl(Etat q) throws Exception;

    void external() throws Exception {
        currentEtat = externalImpl(currentEtat);
    }

    protected abstract Etat internalImpl(Etat etat) throws Exception;

    void internal() throws Exception {
        currentEtat = internalImpl(currentEtat);
    }

    protected abstract void lambdaImpl(Etat s);

    void lambda() {
        lambdaImpl(currentEtat);
    }

    protected abstract void initImpl();

    public void init(){
        initImpl();
        tr=currentEtat.duree;
        tn=tr;
    }

    public Etat getCurrentEtat() {
        return currentEtat;
    }

    public void updateIn(Component... components) {
        inputs.clear();
        for (Component c : components) {
            for (String key : c.outputs.keySet()) {
                inputs.put(key, c.outputs.get(key));
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Component o_obj = (Component) obj;

            return o_obj.nom.equals(nom);
        } catch (Exception e) {
            return false;
        }
    }

}
