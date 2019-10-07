package ODE;

import utils.Scheduler;

class Main {

    private static Step step1 = new Step("step1", 1, -3, 0.65f, "step1");
    private static Step step2 = new Step("step2", 0, 1, 0.35f, "step2");
    private static Step step3 = new Step("step3", 0, 1, 1f, "step3");
    private static Step step4 = new Step("step4", 0, 4, 1.5f, "step4");

    private static Adder adder = new Adder("adder");
    private static IntegrateurTemps integratorT = new IntegrateurTemps("integrator", 0.001f);
    private static IntegrateurEvenements integratorE1 = new IntegrateurEvenements("integrator1", 0.001f, 0.001f);
    private static IntegrateurEvenements integratorE2 = new IntegrateurEvenements("integrator2", 0.001f, 0.001f);
    private static Constante constante = new Constante("constante");

    private static Scheduler schedulerT = new Scheduler(() -> {
        adder.updateIn(step1, step2, step3, step4);
        integratorT.updateIn(adder);
        step1.getOutputs().clear();
        step2.getOutputs().clear();
        step3.getOutputs().clear();
        step4.getOutputs().clear();
        adder.getOutputs().clear();
        integratorT.getOutputs().clear();
    }, step1, step2, step3, step4, adder, integratorT);

    private static Scheduler schedulerE = new Scheduler(() -> {
        adder.updateIn(step1, step2, step3, step4);
        integratorE1.updateIn(adder);
        step1.getOutputs().clear();
        step2.getOutputs().clear();
        step3.getOutputs().clear();
        step4.getOutputs().clear();
        adder.getOutputs().clear();
        integratorE1.getOutputs().clear();
    }, step1, step2, step3, step4, adder, integratorE1);

    private static Scheduler schedulerODE = new Scheduler(() -> {

        integratorE1.updateIn(constante);
        integratorE2.updateIn(integratorE1);
        integratorE1.getOutputs().clear();
        integratorE2.getOutputs().clear();
        constante.getOutputs().clear();
    }, constante, integratorE1, integratorE2);

    public static void main(String[] args) throws Exception {
        schedulerODE.run();
    }


}
