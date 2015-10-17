angular.module('C3web')
.config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
    $locationProvider.html5Mode(false);
    $routeProvider
        .when('/', {
            templateUrl: 'views/home.html'
        })
        .when('/group', {
            title: 'Group list',
            templateUrl: 'views/group/group-list.html',
            controller: 'group.groupListController'
        })
        .otherwise({
            redirectTo: '/'
        });
}]);