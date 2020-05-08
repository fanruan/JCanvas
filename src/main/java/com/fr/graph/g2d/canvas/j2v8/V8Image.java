package com.fr.graph.g2d.canvas.j2v8;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.fr.graph.g2d.canvas.Image;

/**
 * @author Bjorn
 * @version 10.0
 * Created by Bjorn on 2019-11-14
 */
public class V8Image extends V8Object {

    public V8Image(final V8Painter v8Painter, V8 v8, final Image image) {
        super(v8);
        V8Object proto = v8.getObject("NativeImagePrototype");
        setPrototype(proto);
        proto.release();
        registerJavaMethod(new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0 && parameters.get(0) instanceof String) {
                    image.setSrc((String) parameters.get(0));
                    if (image.getImage() != null) {
                        receiver.add("width", image.getImage().getWidth());
                        receiver.add("height", image.getImage().getHeight());
                    }
                    V8Function function = (V8Function) receiver.getObject("onload");
                    try {
                        function.call(null, null);
                    } finally {
                        function.release();
                    }
                }
            }
        }, "setSrc");
        v8Painter.getImageMap().put(this, image);
    }
}