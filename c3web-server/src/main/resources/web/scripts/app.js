'use strict';

angular.module('c3web', ['ngAnimate', 'ngRoute', 'ngResource'])

    .config(function ($locationProvider, $routeProvider) {

        $locationProvider.html5Mode(false);

        $routeProvider
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'MainCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });

    });
