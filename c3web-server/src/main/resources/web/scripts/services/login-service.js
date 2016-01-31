(function (angular, undefined) {
    'use strict';

    var loginService = function (jsonService) {

        var auth = function (user) {
            return jsonService.post('auth', user);
        };
        var list = function () {
            return jsonService.get('user');
        };
        return {
            auth: auth,
            list:list
        };
    };

    loginService.$inject = ['JsonService'];

    angular.module('C3web.services').factory('LoginService', loginService);
})(angular);