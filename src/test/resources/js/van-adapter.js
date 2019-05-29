var BI = {};

var FR = {};

var global = {};

window.BI = BI;

var document = {
    createElement: function () {
        return {
            getContext: true,
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