(function(angular, undefined){
    'use strict';

    angular.module('app')
        .config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
            $locationProvider.html5Mode(false);
            $routeProvider
                .when('/', {
                    templateUrl: 'views/home.html',
                    controller: 'MainCtrl'
                })
                .when('/group', {
                    templateUrl: 'scripts/app/components/group/group-list.html',
                    controller: 'app.components.group.groupListController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        }]);
})(angular)