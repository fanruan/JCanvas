package com.fr.graph.g2d.canvas.j2v8;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.fr.graph.g2d.canvas.CanvasAdapter;
import com.fr.graph.g2d.canvas.CanvasPainter;

import java.awt.image.BufferedImage;

public class V8Painter extends CanvasPainter {

    private V8 v8;
    private CanvasAdapter canvas = new CanvasAdapter();

    public V8Painter() {
        init();
    }

    public void init() {
        v8 = V8.createV8Runtime();
        V8Function constructor = new V8Function(v8, new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() == 2) {
                    int w = parameters.getInteger(0);
                    int h = parameters.getInteger(1);
                    return new V8Canvas(v8, new CanvasAdapter(w, h));
                } else {
                    return new V8Canvas(v8, new CanvasAdapter());
                }
            }
        });
        v8.add("Canvas", constructor);
        constructor.release();
        release();
    }

    public BufferedImage paint() {
        acquire();
        try {
            execute();
            return canvas.getCanvas();
        } finally {
            release();
        }
    }

    public Object executeFunction(String functionName, Object... parameters) {
        acquire();
        try {
            return v8.executeJSFunction(functionName, parameters);
        } finally {
            release();
        }
    }

    public BufferedImage paint(String functionName, Object... parameters) {
        acquire();
        try {
            CanvasAdapter canvas = new CanvasAdapter();
            V8Canvas v8Canvas = new V8Canvas(v8, canvas);
            Object[] newParameters = new Object[parameters.length + 1];
            try {
                newParameters[0] = v8Canvas;
                System.arraycopy(parameters, 0, newParameters, 1, parameters.length);
                Object result = v8.executeJSFunction(functionName, newParameters);
                if (result instanceof V8Object) {
                    ((V8Object) result).release();
                }
            } finally {
                v8Canvas.release();
            }
            BufferedImage image = canvas.getCanvas();
            canvas.dispose();
            return image;
        } finally {
            release();
        }
    }

    public void close() {
        acquire();
        v8.release(true);
    }

    public void initCanvas() {
        acquire();
        try {
            V8Canvas v8Canvas = new V8Canvas(v8, canvas);
            v8.add("canvas", v8Canvas);
            v8Canvas.release();
        } finally {
            release();
        }
    }

    public void execute(String script) {
        acquire();
        try {
            v8.executeVoidScript(script);
        } finally {
            release();
        }
    }

    private void execute() {
        v8.executeVoidScript(getSb().toString());
        getSb().setLength(0);
    }

    private void acquire() {
        v8.getLocker().acquire();
    }

    private void release() {
        v8.getLocker().release();
    }
}
