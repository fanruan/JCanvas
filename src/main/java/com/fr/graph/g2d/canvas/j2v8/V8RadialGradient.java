package com.fr.graph.g2d.canvas.j2v8;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.fr.graph.g2d.canvas.RadialGradientAdapter;

public class V8RadialGradient extends V8Object {

    public V8RadialGradient(final V8 v8, final RadialGradientAdapter radialGradient) {
        super(v8);
        registerJavaMethod(radialGradient, "toString", "toString", new Class[]{});

        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 1) {
                    Object offset = parameters.get(0);
                    String color = parameters.getString(1);
                    radialGradient.addColorStop(offset.toString(), color);
                } else {
                    throw new IllegalArgumentException("Failed to execute 'addColorStop': 2 arguments required, but only "
                            + parameters.length() + " present.");
                }
            }
        }, "addColorStop");
    }
}
