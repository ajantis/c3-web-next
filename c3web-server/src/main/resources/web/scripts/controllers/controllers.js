'use strict';

angular.module('c3web')

    .controller('MainCtrl', function ($rootScope, $location) {
        $rootScope.$path = $location.path.bind($location);
    });

