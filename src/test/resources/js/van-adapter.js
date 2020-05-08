var BI = {};

var FR = {};

var global = {};

window.BI = BI;

process = {versions: {}};
var oldToString = Object.prototype.toString;
Object.prototype.toString = function () {
    if (this === process) {
        return '[object process]';
    }
    return oldToString.call(this);
};

var document = {
    createElement: function () {
        return {
            getContext: function () {

            },
            style: {}
        }

    },

    documentElement: {
        style: {}
    }
};

var navigator = {

    userAgent: '',

    platform: ''
}