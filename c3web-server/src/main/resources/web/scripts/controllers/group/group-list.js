(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupListController',['$scope', 'GroupListService',function($scope, GroupListService) {
         $scope.model = {
             greeting: 'Hello',
             list: [
                 'One',
                 'Two',
                 'Three'
             ]
         };
         $scope.model = {};

         $scope.load = function(){
            GroupListService.list().then(function(data){
                $scope.model = data;
            })
         };

         $scope.load();
     }]);
})(angular)