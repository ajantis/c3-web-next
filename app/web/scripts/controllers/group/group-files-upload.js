(function(angular){
    'use strict';
    angular.module('C3web.controllers')
        .controller('fileUploadController', ['$scope', '$routeParams', 'FileUploader', '$sce', function($scope, $routeParams, FileUploader, $sce) {
            var uploader = $scope.uploader = new FileUploader({
                url: '/api/file'
            });
            var currentPath = $sce.trustAsResourceUrl($routeParams.path);
            // FILTERS

            uploader.filters.push({
                name: 'customFilter',
                fn: function(item /*{File|FileLikeObject}*/, options) {
                    return this.queue.length < 10;
                }
            });

            // CALLBACKS

            uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
                console.info('onWhenAddingFileFailed', item, filter, options);
            };
            uploader.onAfterAddingFile = function(fileItem) {
                console.info('onAfterAddingFile', fileItem);
            };
            uploader.onAfterAddingAll = function(addedFileItems) {
                console.info('onAfterAddingAll', addedFileItems);
            };
            uploader.onBeforeUploadItem = function(item) {
                console.info('onBeforeUploadItem', item);
                item.formData.push({url: "/"+currentPath+"/"+item.file.name});
                item.formData.push({fileName: item.file.name});
                item.formData.push({fileSize: item.file.size});
                item.formData.push({fileTags: ""});
                item.formData.push({fileType: "Other"});
                item.formData.push({contentType: item.file.type });
                item.formData.push({isFolder: false});
            };
            uploader.onProgressItem = function(fileItem, progress) {
                console.info('onProgressItem', fileItem, progress);
            };
            uploader.onProgressAll = function(progress) {
                console.info('onProgressAll', progress);
            };
            uploader.onSuccessItem = function(fileItem, response, status, headers) {
                console.info('onSuccessItem', fileItem, response, status, headers);

                $.ajax({
                    url: "/api/annotation/"+encodeURIComponent("/"+currentPath+"/"+fileItem.file.name),
                    cache: false
                }).done(function(response) {
                    console.info('OnAnnotaitonDone', response);
                    $scope.tags = response;
                    $('input').focus();
                });

            };
            uploader.onErrorItem = function(fileItem, response, status, headers) {
                console.info('onErrorItem', fileItem, response, status, headers);
            };
            uploader.onCancelItem = function(fileItem, response, status, headers) {
                console.info('onCancelItem', fileItem, response, status, headers);
            };
            uploader.onCompleteItem = function(fileItem, response, status, headers) {
                console.info('onCompleteItem', fileItem, response, status, headers);
            };
            uploader.onCompleteAll = function() {
                console.info('onCompleteAll');
            };

            console.info('uploader', uploader);
        }]);
})(angular);
