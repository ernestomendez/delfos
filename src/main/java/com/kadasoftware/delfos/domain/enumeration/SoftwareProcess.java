package com.kadasoftware.delfos.domain.enumeration;

/**
 * Created by ernesto on 25/10/16.
 */
public enum SoftwareProcess {
        AD("Analisys/Design"),
        UI_DESIGN("UI Design"),
        CODING("Coding"),
        UNIT_TEST("Unit test"),
        CODE_REVIEW("Code review"),
        FUNCTIONAL_TESTING("Functional testing"),
        UI_TESTING("UI testing");

    private String name;

    SoftwareProcess(String step) {
        this.name = step;
    }

    public String getName() {
        return this.name;
    }

}
