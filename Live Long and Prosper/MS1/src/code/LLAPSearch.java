package code;
public class LLAPSearch extends GenericSearch1 {
	
	public static int exp = 0;
    public LLAPSearch(String problem, String strategy) {
        super();
    }

    public static String solve(String initialState, String strategy, boolean visualize) {
        String sol = "";
        if (strategy == "BF") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueAtEnd");
            exp = g.exCount;
            sol = g.solution;
            // g.GenericSearchM("EnqueueAtEnd");
        }
        if (strategy == "DF") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueAtFront");
            exp = g.exCount;
            sol = g.solution;
            // g.GenericSearchM("EnqueueAtFront");
        }
        if (strategy == "ID") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueID-Front");
            exp = g.exCount;
            sol = g.solution;
            // g.GenericSearchM("EnqueueAtEnd");
        }
        if (strategy == "UC") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueCost");
            exp = g.exCount;
            sol = g.solution;
        }
        if (strategy == "GR1") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueProsperityGR1");
            exp = g.exCount;
            sol = g.solution;
        }
        if (strategy == "GR2") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueBuildsProsGR2");
            exp = g.exCount;
            sol = g.solution;
        }
        if (strategy == "AS1") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueProsperityAS1");
            exp = g.exCount;
            sol = g.solution;
        }
        if (strategy == "AS2") {
            GenericSearch1 g = new GenericSearch1(initialState, "EnqueueBuildsProsAS2");
            exp = g.exCount;
            sol = g.solution;
        }
        return sol;
    }

    public static void main(String[] args) {

        String init = "50;" +
                "12,12,12;" +
                "50,60,70;" +
                "30,2;19,2;15,2;" +
                "300,5,7,3,20;" +
                "500,8,6,3,40;";

        LLAPSearch g = new LLAPSearch(init,
                "DF");

        System.out.println(LLAPSearch.solve(init, "ID", false));
        // g.GenericSearchM();
    }
}