package com.fr.graph.g2d.canvas.j2v8;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8Map;
import com.fr.graph.g2d.canvas.CanvasAdapter;
import com.fr.graph.g2d.canvas.CanvasPainter;
import com.fr.graph.g2d.canvas.Image;
import com.fr.graph.g2d.canvas.ImageProvider;

import java.awt.image.BufferedImage;

public class V8Painter extends CanvasPainter {

    private V8 v8;
    private CanvasAdapter canvas = new CanvasAdapter();

    private V8Map<ImageProvider> imageMap = new V8Map<ImageProvider>();

    public V8Painter() {
        init();
    }

    public V8Map<ImageProvider> getImageMap() {
        return imageMap;
    }

    public void init() {
        v8 = V8.createV8Runtime();
        //Canvas的构造函数
        V8Function constructor = new V8Function(v8, new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() == 2) {
                    int w = parameters.getInteger(0);
                    int h = parameters.getInteger(1);
                    return new V8Canvas(V8Painter.this, v8, new CanvasAdapter(w, h));
                } else {
                    return new V8Canvas(V8Painter.this, v8, new CanvasAdapter());
                }
            }
        });
        //Image的构造函数
        V8Function imageConstructor = new V8Function(v8, new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                return new V8Image(V8Painter.this, v8, new Image());
            }
        });
        v8.add("Canvas", constructor);
        v8.add("Image", imageConstructor);
        constructor.release();
        imageConstructor.release();
        release();
    }

    public BufferedImage paint() {
        acquire();
        try {
            execute();
            return canvas.getCanvas();
        } finally {
            clear();
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
            V8Canvas v8Canvas = new V8Canvas(this, v8, canvas);
            Object[] newParameters = new Object[parameters.length + 1];
            try {
                newParameters[0] = v8Canvas;
                System.arraycopy(parameters, 0, newParameters, 1, parameters.length);
                Object result = v8.executeJSFunction(functionName, newParameters);
                if (result instanceof V8Object) {
                    ((V8Object) result).release();
                }
            } finally {
                clear();
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
        clear();
        v8.release(true);
    }

    public void initCanvas() {
        acquire();
        try {
            V8Canvas v8Canvas = new V8Canvas(this, v8, canvas);
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

    private void clear() {
        imageMap.clear();
    }
}