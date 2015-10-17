(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('MainCtrl', function ($scope, $location, version) {
            $scope.$path = $location.path.bind($location);
            $scope.version = version;
        });
})