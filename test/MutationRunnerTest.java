import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class MutationRunnerTest {
    @Test
    public void testMutationRunner() {
        Mutator m = new Mutator(MutantOperation.ARITH, "test_arith",
                "src/MutationTester.java", "test/MutationTesterTest.java",
                Optional.of("testArithTest_mutantKilled"));
        Float score = m.mutate();
        Assert.assertEquals(100.0, score, 0.001);
//        new File(mutatedFile).delete();
//        new File(mutatedTestFile).delete();
    }
}
