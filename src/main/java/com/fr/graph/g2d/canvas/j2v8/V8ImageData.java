package com.fr.graph.g2d.canvas.j2v8;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8ArrayBuffer;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.V8TypedArray;
import com.eclipsesource.v8.V8Value;
import com.fr.graph.g2d.canvas.ImageData;

public class V8ImageData extends V8Object {

    public V8ImageData(final V8 v8, final ImageData imageData) {
        super(v8);
        int[] data = imageData.getData();
        V8ArrayBuffer v8ArrayBuffer = new V8ArrayBuffer(v8, data.length << 2);
        for (int i : data) {
            v8ArrayBuffer.putInt(i);
        }
        V8TypedArray v8TypedArray = new V8TypedArray(v8, v8ArrayBuffer, V8Value.INTEGER, 0, data.length);

        add("data", v8TypedArray);
        v8ArrayBuffer.close();
        v8TypedArray.close();

        add("width", imageData.getWidth());
        add("height", imageData.getHeight());
    }
}