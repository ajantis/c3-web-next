'use strict';

angular.module('c3web')

    .controller('MainCtrl', function ($scope, $location, version) {
        $scope.$path = $location.path.bind($location);
        $scope.version = version;
    });
