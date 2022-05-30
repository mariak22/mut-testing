import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class MutatorTest {
    @Test
    public void mutateArithTest() {
        Set s = new HashSet<MutantOperation>();
        s.add(MutantOperation.ARITH);
        Mutator m = new Mutator(s, "test_arith",
                "src/MutationTester.java", "src/MutationArithTesterMutated.java");
        m.mutate();

        new File("src/MutationArithTesterMutated.java").delete();
    }

    @Test
    public void mutateLogicalTest() {
        Set s = new HashSet<MutantOperation>();
        s.add(MutantOperation.LOGICAL);
        Mutator m = new Mutator(s, "test_logical",
                "src/MutationTester.java", "src/MutationLogicalTesterMutated.java");
        m.mutate();
        new File("src/MutationLogicalTesterMutated.java").delete();
    }
}
