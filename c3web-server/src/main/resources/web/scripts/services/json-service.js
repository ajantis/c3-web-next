(function () {
    'use strict';
    angular.module('C3web.services')
.factory('JsonService', ['$http', '$location', 'toastr', function ($http, $location, toastr) {

    var serviceBase = 'api/';

    var setOptionToastr = function() {
        toastr.options.timeOut = 1000;
        toastr.options.extendedTimeOut = 100; //time out after visit
        toastr.options.positionClass = "toast-top-right";
        toastr.options.closeButton = true;
    };
    var getPromise = function (url, type, jsonData, onValidationError, jsonAction, noAlert) {

        var onSuccessCallBack = function () {
            if (type != 'GET' && !noAlert) {
                setOptionToastr();
                toastr.success("success_message", "success_title");
            }
        };

        var onErrorCallBack = function (data, status) {
            //ignore request cancellation
            if (status == 0) {
                return;
            }
            var timeout = 3000;
            if (status == '555') {
                var message = "";
                for (var i = 0; i < data.Errors.length; i++) {
                    message = message + " " + data.Errors[i].error + ";";
                }
                setOptionToastr();
                toastr.error("error_message" + message, "error_title");
            } else if (status == '101') {
                setOptionToastr();
                toastr.error("error_message" + status, "error_title");
            } else if (status == '302') {
                $location.reload(true);
            } else {
                setOptionToastr();
                toastr.error("error_message" + status, "error_title");
            }
        };

        if (type == 'GET') {
            return $http.get(serviceBase + url, jsonData ? JSON.stringify(jsonData) : null).success(onSuccessCallBack).error(onErrorCallBack);
        }
        if (type == 'DELETE') {
            return $http.delete(serviceBase + url, jsonData ? JSON.stringify(jsonData) : null).success(onSuccessCallBack).error(onErrorCallBack);
        }
        if (type == 'PUT') {
            return $http.put(serviceBase + url, jsonData ? JSON.stringify(jsonData) : null).success(onSuccessCallBack).error(onErrorCallBack);
        }
        else {
            return $http.post(serviceBase + url, jsonData ? jsonData : null).success(onSuccessCallBack).error(onErrorCallBack);
        }
    };

    var get = function (url, global) {
        return getPromise(url, 'GET');
    };
    var remove = function (url, id) {
        url = url+ '/'+ id;
        return getPromise(url, 'DELETE');
    };

    var post = function (url, jsonData, onValidationError, jsonAction, noAlert) {
        return getPromise(url, 'POST', jsonData, onValidationError, jsonAction, noAlert);
    };

    var put = function (url, jsonData, onValidationError, jsonAction, noAlert) {
        return getPromise(url, 'PUT', jsonData, onValidationError, jsonAction, noAlert);
    };
    return {
        get: get,
        post: post,
        put: put,
        remove: remove
    };
}]);
})();