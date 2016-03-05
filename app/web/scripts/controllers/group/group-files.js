(function(angular) {
    'use strict';
    angular.module('C3web.controllers')
        .controller('group.groupFilesController', ['$scope', '$routeParams', 'GroupFilesService', function($scope, $routeParams, FilesService) {
            /*$scope.elements=[{uid:'1', type:'dir',name:'TcpConnection terminated, stopping', size:'300 MB', owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'},
            {uid:'2', type:'img', size:'10 MB',name:'TcpConnection terminated',  owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'},
            {uid:'3', type:'txt', size:'1.2 MB',name:'TcpConnection terminated',  owner:'Delar', ctime:'Tue, 17 Jun 2014 13:04:55 GMT'}];*/

            $scope.elements = {};
            $scope.rootFolder = "/" + $routeParams.id.toString();
            $scope.currentPath = "/" + $routeParams.id.toString();

            $scope.updateFiles = function() {
                $scope.load = function() {
                    FilesService.list(encodeURIComponent($scope.currentPath)).then(function(result) {
                        var elementArray = [];
                        for (var i = 0; i < result.data.length; i++) {
                            var file = result.data[i];
                            var fileData = {};
                            fileData.uid = i + 1;
                            fileData.type = file.contentType;
                            fileData.size = file.metadata.size;
                            fileData.name = file.metadata.title;
                            fileData.url = file.url;
                            fileData.isFolder = file.isFolder;
                            fileData.owner = file.metadata.owner;
                            fileData.ctime = file.metadata.creationTime;
                            elementArray.push(fileData);
                        }
                        $scope.elements = elementArray;
                    });
                };

                $scope.detailInfo = false;

                $scope.load();

            };

            $scope.updateFiles();

            $scope.showDetails = function(name) {
                for (var i = 0; i < $scope.elements.length; i++) {
                    if (name === $scope.elements[i].name) {
                        $scope.element = $scope.elements[i];
                    }
                }
                if ($scope.element.isFolder) {
                    $scope.currentPath = $scope.element.url;
                    $scope.updateFiles();
                } else {
                    $scope.detailInfo = true;
                }
            };

            $scope.goBack = function() {
                if ($scope.currentPath != $scope.rootFolder) {
                    $scope.currentPath = "/" + $scope.currentPath.split('/').slice(1, -1).join('/');
                    $scope.updateFiles();
                }
            };

            $scope.closeDetails = function() {
                $scope.detailInfo = false;
            };

            $scope.download = function(url) {
                var getFileUrl = "/api/download/" + encodeURIComponent(url);
                window.open(getFileUrl);
            };
        }]);
})(angular);
