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
        .when('/group/:id', {
            templateUrl: 'views/group/group.html',
            controller: 'group.groupController'
        })
        .when('/upload', {
            templateUrl: 'views/group/group-file-upload.html',
            controller: 'fileUploadController'
        })
        .when('/r_suite',{
                      title: 'R_suite',
                      templateUrl: 'views/r_suite.html'

        })
         .when('/k_base',{
                              title: 'K_base',
                              templateUrl: 'views/k_base.html',
                              controller: 'CollectCtrl'

         })
         .when('/experiments',{
                                     title: 'exp',
                                     templateUrl: 'views/experiments.html'

                                 })
        .otherwise({
            redirectTo: '/'
        });
}]);