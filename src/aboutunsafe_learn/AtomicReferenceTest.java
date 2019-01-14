package aboutunsafe_learn;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    private static AtomicReference<MyClass> atomicReference = new AtomicReference<>(new MyClass("src"));

    static class MyClass{

        private static String str;

        public MyClass(String str1){
            str = str1;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    public static void main(String[] args) {

        System.out.println(atomicReference.get().getStr());

        atomicReference.updateAndGet((t) -> {
            t.setStr("update");
            return t;
        });

        System.out.println(atomicReference.get().getStr());

    }
}
