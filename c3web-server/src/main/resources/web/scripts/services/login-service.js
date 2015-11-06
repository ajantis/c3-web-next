(function () {
    'use strict';

    angular.module('C3web.services')
    .factory('LoginService', ['JsonService', function (jsonService) {

        var auth = function (user) {
            return jsonService.post('auth', user);
        };
        var getUser = function () {
            return jsonService.get('users');
        };
        return {
            auth: auth,
            getUser:getUser
        };
    }]);
})();