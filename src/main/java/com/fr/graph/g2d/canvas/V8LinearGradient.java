package com.fr.graph.g2d.canvas;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class V8LinearGradient extends V8Object {

    public V8LinearGradient(final V8 v8, final LinearGradientAdapter linearGradient) {
        super(v8);
        registerJavaMethod(linearGradient, "toString", "toString", new Class[]{});

        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 1) {
                    Object offset = parameters.get(0);
                    String color = parameters.getString(1);
                    linearGradient.addColorStop(offset.toString(), color);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'addColorStop': 2 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }

        }, "addColorStop");
    }
}
