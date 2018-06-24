// Program containing marker annotations - Page 170
package org.effectivejava.examples.chapter06.item35;

public class Sample {
    @XTest
    public static void m1() {
    } // Test should pass

    public static void m2() {
    }

    @XTest
    public static void m3() { // Test Should fail
        throw new RuntimeException("Boom");
    }

    public static void m4() {
    }

    @XTest
    public void m5() {
    } // INVALID USE: nonstatic method

    public static void m6() {
    }

    @XTest
    public static void m7() { // Test should fail
        throw new RuntimeException("Crash");
    }

    public static void m8() {
    }
}