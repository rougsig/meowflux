package com.github.rougsig;

public final class Deps {
    private static final String kotlinVersion = "1.3.21";

    public static final String kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + kotlinVersion;
    public static final String kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" + kotlinVersion;

    public static final String rxJava = "io.reactivex.rxjava2:rxjava:2.2.4";
    public static final String rxRelay = "com.jakewharton.rxrelay2:rxrelay:2.1.0";

    public static final String[] unitTestLibs = {
            "junit:junit:4.12",
            "org.assertj:assertj-core:3.6.2"
    };
}
