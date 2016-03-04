module.exports = function (config) {
    config.set({

        basePath: '../',

        files: [
            'web/bower_components/angular/angular.js',
            'web/bower_components/angular-route/angular-route.js',
            'web/bower_components/angular-resource/angular-resource.js',
            'web/bower_components/angular-animate/angular-animate.js',
            'web/bower_components/angular-mocks/angular-mocks.js',
            'web/bower_components/lodash/lodash.js',

            'web/scripts/setup.js',
            'web/scripts/app.js',
            'web/scripts/components/**/*.js',
            'web/scripts/controllers/**/*.js',
            'web/scripts/directives/**/*.js',
            'web/scripts/services/**/*.js',

            'test/unit/**/*.js'
        ],

        autoWatch: true,

        frameworks: ['jasmine'],

        browsers: [
            'Chrome'
            //'Firefox'
        ],

        plugins: [
            'karma-chrome-launcher',
            //'karma-firefox-launcher',
            'karma-jasmine'
        ],

        junitReporter: {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }

    });
};