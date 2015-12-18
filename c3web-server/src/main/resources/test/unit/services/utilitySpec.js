//'use strict';

describe('utility', function() {
	var utility;

  	beforeEach(module('C3web.services'));
  	
  	// beforeEach(inject());

  	// Test service availability
  	it('should inject utiliity service', inject(function(utility){
  		expect(utility).toBeDefined();
  	}));
});