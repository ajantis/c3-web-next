(function(angular, undefined){
    'use strict';

    angular.module('app.components', []);
    angular.module('app.services', []);
    angular.module('app.directives', []);

    angular.module('app', [
        // ng
        'ng',
        'ngRoute',
        'ngResource',
        'ngAnimate',
        // app
        'app.components',
        'app.services',
        'app.directives'
    ]);
})(angular)