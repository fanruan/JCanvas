var Canvas = Java.type("com.fr.graph.g2d.canvas.nashorn.NashornCanvas");

function do_nothing() {

}

setTimeout = clearTimeout = setInterval = clearInterval = do_nothing;


var window = {
    devicePixelRatio: 1,
    setTimeout: setTimeout,
    clearTimeout: clearTimeout,
    setInterval: setInterval,
    clearInterval: clearInterval
};


var console = {
    log: do_nothing
};

