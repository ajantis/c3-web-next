(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupController',['$scope', '$routeParams', 'GroupListService', function($scope, $routeParams, GroupListService) {
         $scope.group = {};
         var idGroup = $routeParams.id;

         $scope.load = function(){
            GroupListService.get(idGroup).then(function(result){
                $scope.group = result.data;
                $scope.selectedPage = $scope.pages[0];
            });
         };
        $scope.titles = [{ title: 'Grp' },
                         { title: 'Role_Profile_Edit_title' }];

        $scope.pages = [{ title: 'Group_Log', view: 'views/group/group-log.html', index: 0, active: true },
                        { title: 'Group_Files', view: 'views/group/group-files.html', index: 1, active: false},
                        { title: 'Group_Settings', view: 'views/group/group-settings.html', index: 2, active: false}];
         $scope.load();

        $scope.selectPage = function (index) {
         angular.forEach($scope.pages, function (value) {
             value.active = false;
         });

         $scope.pages[index].active = true;
         $scope.selectedPage = $scope.pages[index];
        };
     }]);
})(angular);