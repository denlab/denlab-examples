public class C {
    public static void main(String[] args) {
        try {
            new javax.script.ScriptEngineManager()
                .getEngineByName("JavaScript")
                .eval("load('https://lorenzoongithub.github.io/nudge4j/twigs/n4j.boot.js')");
        } catch (javax.script.ScriptException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello, the time is now: "+new java.util.Date());
    }
}
