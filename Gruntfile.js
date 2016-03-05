module.exports = function(grunt) {

	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),

		jshint: {
			dev: {        
				src: [
					'c3web-server/src/main/resources/web/scripts/**/*.js'
				]
			}
		},

		jsbeautifier : {
			"beautify": {
				src : [
					'c3web-server/src/main/resources/web/scripts/**/*.js',
					'c3web-server/src/main/resources/web/styles/**/*.css',
					'c3web-server/src/main/resources/web/scripts/**/*.html'
				]
			},
			"check": {
				src : [
					'c3web-server/src/main/resources/web/scripts/**/*.js',
					'c3web-server/src/main/resources/web/styles/**/*.css',
					'c3web-server/src/main/resources/web/scripts/**/*.html'
				],
				options : {
					mode:"VERIFY_ONLY"
				}
			}
		},

		githooks: {
		    all: {
		      'pre-commit': 'git-pre-commit'
		    }
		}
	});

	require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // task setup 
    grunt.registerTask('default', []);

    grunt.registerTask('git-pre-commit', [
    	'jsbeautifier:beautify'
    ]);
};