package com.overflow.stack.update.es;

public class Test {
    @org.junit.Test
    public void test(){
        System.out.println(match("HELLO WORLD","*H*E*"));
    }

    public boolean match(String text, String pattern) {
        return text.matches(pattern.replace("?", ".?").replace("*", ".*?"));
    }
}
