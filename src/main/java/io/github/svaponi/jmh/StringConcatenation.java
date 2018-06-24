package io.github.svaponi.jmh;

public class StringConcatenation {

    private final boolean print;
    private final long n;
    private final String form;
    private final int line = 25;

    public StringConcatenation() {
        this(3, false);
    }

    public StringConcatenation(final int exp) {
        this(exp, false);
    }

    public StringConcatenation(final int exp, final boolean print) {
        n = (long) Math.pow(10, (double) exp);
        form = "%" + (exp + 1) + "s";
        this.print = print;
//        System.out.println("n=" + n);
//        System.out.println("print=" + print);
    }

    public void concatString() {
        String s = "";
        for (long i = 0; i < n; i++) {
            final String tmp = build(i);
            s += tmp;
        }
        if (print) {
            System.out.println(s);
        }
    }

    public void concatStringBuilder() {
        final StringBuilder s = new StringBuilder();
        for (long i = 0; i < n; i++) {
            final String tmp = build(i);
            s.append(tmp);
        }
        if (print) {
            System.out.println(s);
        }
    }

    private String build(final long i) {
        if (i % line == line - 1) {
            return String.format(form, i).concat("\n");
        } else {
            return String.format(form, i);
        }
    }
}
