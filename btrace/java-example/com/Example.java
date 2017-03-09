package com;

class Example {


    public void m111() {}
    public void m211() {}
    public void m121() {}
    public void m221() {}
    public void m112() {}
    public void m212() {}
    public void m122() {}
    public void m222() {}

    public void m22() {
        m211();
        m221();
    }

    public void m21() {
        m211();
        m222();
    }

    public void m12() {
        m121();
        m121();
    }

    public void m11() {
        m111();
        m111();
    }

    public void m2() {
        m21(); 
        m22(); 
    }

    public void m1() {
        m11(); 
        m12(); 
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("start at: "+new java.util.Date());
            Example e = new Example();
            e.m1();
            e.m2();
            System.out.println("end at  : "+new java.util.Date());
            Thread.sleep(1000);
        }
    }
}
