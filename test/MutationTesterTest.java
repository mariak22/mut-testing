import org.junit.Assert;
import org.junit.Test;

public class MutationTesterTest {
    @Test
    public void testArithTest_mutantKilled() {
        int res = MutationTester.test_arith(5, 3);
        Assert.assertEquals(8, res);
    }

    @Test
    public void testMultiArithTest_mutantKilled() {
        int res = MutationTester.test_multi_arith(5, 5, 10);
        Assert.assertEquals(0, res);
    }

    @Test
    public void testLogicalTest_mutantKilled() {
        boolean res = MutationTester.test_logical(true, false);
        Assert.assertEquals(false, res);
    }
}
