function setTimeout() {

};

function clearTimeout(cancel) {

};

function setInterval(fn, delay /* [, args...] */) {

};

function clearInterval(cancel) {

};

var BI = {};

var global = {};

var window = {
    BI: BI,
    devicePixelRatio: 1,
    setTimeout: setTimeout,
    clearTimeout: clearTimeout,
    setInterval: setInterval,
    clearInterval: clearInterval
};

var document = {
    createElement: function () {
        return {
            getContext: true,
            style : {}
        }

    },

    documentElement: {
        style: {}
    }
};

var navigator = {

    userAgent: '',

    platform: ''

};

var console = {
    log: function (info) {
        this.log(info);
    }
};

NativeCanvasPrototype = {
    set width(v) {
        this.setWidth(v);
    }, get width() {
        return this.getWidth();
    },
    set height(v) {
        this.setHeight(v);
    }, get height() {
        return this.getHeight();
    },
};

NativeContextPrototype = {
    _lineWidth: '',
    set lineWidth(v) {
        this._lineWidth = this.setLineWidth(v);
    }, get lineWidth() {
        return this._lineWidth;
    },
    _lineCap: '',
    set lineCap(v) {
        this._lineCap = this.setLineCap(v);
    }, get lineCap() {
        return this._lineCap;
    },
    _lineJoin: '',
    set lineJoin(v) {
        this._lineJoin = this.setLineJoin(v);
    }, get lineJoin() {
        return this._lineJoin;
    },
    _fillStyle: "#000000",
    set fillStyle(v) {
        this._fillStyle = this.setFillStyle(v);
    }, get fillStyle() {
        return this._fillStyle;
    },
    _strokeStyle: "#000000",
    set strokeStyle(v) {
        this._strokeStyle = this.setStrokeStyle(v);
    }, get strokeStyle() {
        return this._strokeStyle;
    },
    _textAlign: '',
    set textAlign(v) {
        this._textAlign = this.setTextAlign(v);
    }, get textAlign() {
        return this._textAlign;
    },
    _textBaseline: '',
    set textBaseline(v) {
        this._textBaseline = this.setTextBaseline(v);
    }, get textBaseline() {
        return this._textBaseline;
    },
    _font: '',
    set font(v) {
        this._font = this.setFont(v);
    }, get font() {
        return this._font;
    },
    _miterLimit: 0,
    set miterLimit(v) {
        this._miterLimit = this.setMiterLimit(v)
    },
    get miterLimit() {
        return this._miterLimit;
    },
    _globalAlpha: 0,
    set globalAlpha(v) {
        this._globalAlpha = this.setGlobalAlpha(v);
    },
    get globalAlpha() {
        return this._globalAlpha;
    },
    _globalCompositeOperation: '',
    set globalCompositeOperation(v) {
        this._globalCompositeOperation = this.setGlobalCompositeOperation(v);
    },
    get globalCompositeOperation() {
        return this._globalCompositeOperation;
    },
};


NativeImageDataPrototype = {
    _width: 0,
    set width(v) {
    }, get width() {
        return _width = this.getWidth();
    },
    _height: 0,
    set height(v) {
    }, get height() {
        return _height = this.getHeight();
    },
};

NativeTextMetricsPrototype = {
    set width(v) {
    }, get width() {
        return this.getWidth();
    }
};