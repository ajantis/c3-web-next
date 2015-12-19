'use strict';

describe('utility', function () {
    var _, utility;

    beforeEach(module('C3web.services'));

    beforeEach(inject(['utility',
        function (u) {
            utility = u;
        }
    ]));

    // Test service availability
    it('should inject utiliity service', function () {
        expect(utility).toBeDefined();
    });

    describe('equalsIgnoreCase', function () {
        it('should be true when strings are empty', function () {
            expect(utility.equalsIgnoreCase(null, null)).toBeTruthy();
        });

        it('should be true when strings are in different cases', function () {
            expect(utility.equalsIgnoreCase('str', 'STR')).toBeTruthy();
        });

        it('should be false when strings are not equal', function(){
            expect(utility.equalsIgnoreCase('str', 'strl')).toBeFalsy();
        });
    })
});