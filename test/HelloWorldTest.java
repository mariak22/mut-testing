import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {
    @Test
    public void test1() {
        int actual = 4;
        Assert.assertEquals(4, actual);
    }

    @Test
    public void test2() {
        String actual = "hello";
        Assert.assertEquals("hello", actual);
    }
}
