package com.fr.graph.g2d.canvas;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class V8ImageData extends V8Object {

    public V8ImageData(final V8 v8, final ImageData imageData) {
        super(v8);
        V8Object proto = v8.getObject("NativeImageDataPrototype");
        setPrototype(proto);
        proto.release();
        V8Array v8Array = new V8Array(v8);
        for (int i : imageData.getData()) {
            v8Array.push(i);
        }
        add("data", v8Array);
        v8Array.release();
        registerJavaMethod(imageData, "getWidth", "getWidth", new Class[]{});
        registerJavaMethod(imageData, "getHeight", "getHeight", new Class[]{});
    }
}
