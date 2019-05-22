# JCanvas

Graphics2D实现的HTML5 Canvas的接口，可以直接在Java中利用JavaScript绘制图形。

## 运行示例

执行test目录下的com.fr.graph.g2d.canvas.V8test即可看到效果。

```java
File imageFile;
CanvasPainter painter = null;
try {
     painter = CanvasPainter.newDefaultBuilder()
         .loadAndExecute("/js/1.js", "/js/2.js")
         .loadText("var op=123;")              
          build();
     ImageIO.write(painter.paint(), "PNG", imageFile);
} finally {
     if (painter != null) {
         painter.close();
     }
}
```

