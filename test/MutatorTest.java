import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class MutatorTest {
    @Test
    public void mutateArithTest() {
        Mutator m = new Mutator(MutantOperation.ARITH, "test_arith",
                "src/MutationTester.java", "test/MutationTesterTest.java",
                Optional.of("testArithTest_mutantKilled"));
        float score = m.mutate();
        Assert.assertEquals(100.0, score, 0.001);
//        new File(mutatedFile).delete();
    }

    @Test
    public void mutateMultiArithTest() {
        Mutator m = new Mutator(MutantOperation.ARITH, "test_multi_arith",
                "src/MutationTester.java", "test/MutationTesterTest.java",
                Optional.of("testMultiArithTest_mutantKilled"));
        float score = m.mutate();
        Assert.assertEquals(100, score, 0.001);
//        new File(mutatedFile).delete();
    }

    @Test
    public void mutateLogicalTest() {
        Mutator m = new Mutator(MutantOperation.LOGICAL, "test_logical",
                "src/MutationTester.java", "test/MutationTesterTest.java",
                Optional.empty());
        float mutatedFile = m.mutate();
//        new File(mutatedFile).delete();
    }
}
