package ODE;

import utils.Scheduler;

class Main {

    private static Step step1 = new Step("step1", 1, -3, 0.65f, "step1");
    private static Step step2 = new Step("step2", 0, 1, 0.35f, "step2");
    private static Step step3 = new Step("step3", 0, 1, 1f, "step3");
    private static Step step4 = new Step("step4", 0, 4, 1.5f, "step4");

    private static Adder adder = new Adder("adder");
    private static MegaIntegrator integrator=new MegaIntegrator("integrator",0.0001f);

    private static Scheduler scheduler = new Scheduler(() -> {
        adder.updateIn(step1, step2, step3, step4);
        integrator.updateIn(adder);
        step1.getOutputs().clear();
        step2.getOutputs().clear();
        step3.getOutputs().clear();
        step4.getOutputs().clear();
        adder.getOutputs().clear();
        integrator.getOutputs().clear();
    }, step1, step2, step3, step4, adder, integrator);

    public static void main(String[] args) throws Exception {
        scheduler.run();
    }


}
