module.exports = function(grunt) {

	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),

		jshint: {
			dev: {        
				src: ['c3web-server/src/main/resources/web/scripts/**/*.js']
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-jshint');

    // task setup 
    grunt.registerTask('default', []);
};