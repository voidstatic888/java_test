package mytest;


import jmockito_test.AllTest;
import jmockito_test.AnOrdinaryClass;
import jmockito_test.StaticClassTest;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.runner.RunWith;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//@RunWith(JMockit.class)
public class AllTestTest {

    private MockUp<StaticClassTest> mockUp;

    @Tested
    private AllTest allTest;

    @Mocked
    private AnOrdinaryClass ordinaryClass;

    @BeforeClass
    public void init(){
        mockUp = new MockUp<StaticClassTest>(){
            @Mock
            private String getStrVS(String str){
                return "mock后的结果";
            }

            @Mock
            public String getStrPS(String str){
                return "mock后的结果";
            }

            @Mock
            public String getStrNIO(){
                return "mock后的static结果";
            }

            @Mock
            public void getNIO(String str){
                return;
            }
        };
    }

    @AfterClass
    public void end(){
        mockUp.tearDown();
    }


    //private StaticClassTest staticClassTest = new StaticClassTest();


    @Test
    public void test1(){
        new Expectations(){
            {
                ordinaryClass.finalMethod();
                result = 2333;
            }
        };
        Assert.assertEquals(allTest.returnOrdinaryClassNum(), 2333);
    }




    @Test
    public void testtestStatic(){
        //staticClassTest.getStrPS("hello");
        //allTest.setTest(staticClassTest);
        //mockUp.tearDown();
        Assert.assertEquals(allTest.testStatic("1", new AllTest().new A("1")), "11mock后的static结果");
    }

    @Test
    public void test2(){
        /*final AllTest allTest1 = new AllTest();
        new NonStrictExpectations(allTest1){
            {
                Deencapsulation.invoke(allTest1, "testPV", "hello", new AllTest().new A(""));
                result = "hello";
            }
        };*/
        Assert.assertEquals(allTest.testPrivateTestPV("123"),"mock后的结果");
    }

    @Test
    public void test3(){
        Assert.assertEquals(allTest.testPS("hello", new AllTest().new A("str")), "mock后的结果");
    }





}
