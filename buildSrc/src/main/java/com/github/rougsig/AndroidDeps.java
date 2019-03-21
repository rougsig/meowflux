package com.github.rougsig;

public final class AndroidDeps {
    public static final String buildTools = "com.android.tools.build:gradle:3.3.2";

    private static final String epoxyVersion = "3.3.0";
    public static final String epoxyProcessor = "com.airbnb.android:epoxy-processor:" + epoxyVersion;
    public static final String epoxy = "com.airbnb.android:epoxy:" + epoxyVersion;

    public static final String[] coreLibs = {
            Deps.kotlinStdLib,
            Deps.rxJava,
            Deps.rxRelay,
            "com.jakewharton.threetenabp:threetenabp:1.2.0",
            "io.reactivex.rxjava2:rxandroid:2.1.0",
            "com.jakewharton.timber:timber:4.7.1"
    };

    public static final String supportAnnotations = "androidx.annotation:annotation:1.1.0-alpha02";

    public static final String[] supportCoreLibs = {
            supportAnnotations,
            "androidx.core:core:1.1.0-alpha05",
            "androidx.core:core-ktx:1.1.0-alpha05"
    };

    public static final String[] supportUiLibs = {
            "androidx.appcompat:appcompat:1.1.0-alpha03",
            "androidx.recyclerview:recyclerview:1.1.0-alpha03",
            "com.google.android.material:material:1.1.0-alpha04"
    };

    public static final String constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3";
    public static final String[] rxBindingLibs = {
            "com.jakewharton.rxbinding2:rxbinding-kotlin:2.1.1"
    };

    public static final String[] conductorLibs = {
            "com.bluelinelabs:conductor:2.1.5",
            "com.bluelinelabs:conductor-support:2.1.5"
    };

    public static final String[] mviLibs = {
            "com.hannesdorfmann.mosby3:mvi:3.1.1",
            "com.hannesdorfmann.mosby3:mvi-conductor:3.1.0"
    };

    private static final String toothpickVersion = "2.1.0";
    public static final String javaxInject = "javax.inject:javax.inject:1";
    public static final String toothpickRuntime = "com.github.stephanenicolas.toothpick:toothpick-runtime:" + toothpickVersion;
    public static final String toothpickProcessor = "com.github.stephanenicolas.toothpick:toothpick-compiler:" + toothpickVersion;

    public static final String diffDispatcherAnnotations = "com.github.dimsuz:diff-dispatcher-annotations:0.9.3";
    public static final String diffDispatcherProcessor = "com.github.dimsuz:diff-dispatcher-processor:0.9.3";

    public static final String actionsReducerAnnotations = "com.github.rougsig:actions-dispatcher-annotations:1.0.9";
    public static final String actionsReducerRuntime = "com.github.rougsig:actions-dispatcher-runtime:1.0.9";
    public static final String actionsReducerProcessor = "com.github.rougsig:actions-dispatcher-processor:1.0.9";
}
