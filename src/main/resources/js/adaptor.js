window.Van.env.browser = {};
window.Van.env.os = {};
window.Van.env.node = true;
window.Van.env.worker = false;
window.Van.env.canvasSupported = true;
window.Van.env.svgSupported = true;


window.Van.$override('createCanvas', function canvasCreator() {
    return new Canvas();
});

