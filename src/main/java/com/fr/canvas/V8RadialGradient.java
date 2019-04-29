package com.fr.canvas;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

public class V8RadialGradient extends V8Object {

    public V8RadialGradient(final V8 v8, final RadialGradientAdapter radialGradient) {
        super(v8);
        registerJavaMethod(radialGradient, "addColorStop", "addColorStop", new Class[]{double.class, String.class});
        registerJavaMethod(radialGradient, "toString", "toString", new Class[]{});
    }
}
