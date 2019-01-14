package jmockito_test;

public class AllTest {

    private StaticClassTest test = new StaticClassTest();

    private AnOrdinaryClass ordinaryClass = new AnOrdinaryClass();

    public int returnOrdinaryClassNum(){
        return ordinaryClass.finalMethod();
    }

    public void setTest(StaticClassTest test) {
        this.test = test;
    }

    public class A{
        private String str;

        public A(String str){
            this.str = str;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    private String testPV(String str, A a){
        return test.getStrPS(str);
    }

    public String testPrivateTestPV(String str){
        return testPV(str, null);
    }

    public String testPS(String str, A a){
        return test.getStrPS(str);
    }

    public String testStatic(String str, A a){
        StaticClassTest.getNIO(str);
        StaticClassTest.getStrNIO();
        return str + a.getStr() + StaticClassTest.getStrNIO();
    }
}
