(function (angular, undefined) {
    'use strict';

    var controller = function ($scope, $rootScope, LoginService, Data) {
        $scope.IsLogin = false;
        $scope.login = {};
        $scope.signup = {};

        $scope.doLogin = function (user) {
            LoginService.auth(user).then(function (results) {
                if (results.status == "200") {
                    $scope.IsLogin = true;
                    $scope.user = {
                        username : results.data.username,
                        token: results.data.api_token
                    };
                }
            });
        };

        $scope.logout = function () {
            Data.get('logout').then(function (results) {
                Data.toast(results);
                $scope.IsLogin = false;
            });
        };

        $scope.load = function () {
            $scope.IsLogin= false;
            Data.get('ping').then(function (results) {
                if (results.uid) {
                    $scope.IsLogin= true;
                    $scope.user = {
                        username : results.data.username,
                        token:results.data.api_token
                    };
                } else {
                   $scope.IsLogin= false;
               }
           });
        };
        //$scope.load();
    };

    controller.$inject = ['$scope', '$rootScope', 'LoginService', 'Data'];

    angular.module('C3web.directives')
        .controller('loginFormController', controller)
        .directive('loginForm', function () {
            return {
                restrict: 'E',
                replace: true,
                scope:
                {
                    labelName: '@labelName',
                    user : '=user'
                },
                templateUrl: 'views/login-form.html',
                controller: 'loginFormController'
            };
        });
})(angular);