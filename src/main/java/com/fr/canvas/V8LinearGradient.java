package com.fr.canvas;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

public class V8LinearGradient extends V8Object {

    public V8LinearGradient(final V8 v8, final LinearGradientAdapter linearGradient) {
        super(v8);
        registerJavaMethod(linearGradient, "addColorStop", "addColorStop", new Class[]{double.class, String.class});
        registerJavaMethod(linearGradient, "toString", "toString", new Class[]{});
    }
}
