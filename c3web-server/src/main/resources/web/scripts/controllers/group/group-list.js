(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupListController',['$scope', 'GroupListService',function($scope, GroupListService) {
         $scope.groups = {};
         $scope.load = function(){
            GroupListService.list().then(function(result){
                $scope.groups = result.data;
            });
         };
         $scope.load();
     }]);
})(angular);