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
			"default": {
				src : [
					'c3web-server/src/main/resources/web/scripts/**/*.js',
					'c3web-server/src/main/resources/web/styles/**/*.css',
					'c3web-server/src/main/resources/web/scripts/**/*.html'
				]
			},
			"git-pre-commit": {
				src : [
					'c3web-server/src/main/resources/web/scripts/**/*.js',
					'c3web-server/src/main/resources/web/styles/**/*.css',
					'c3web-server/src/main/resources/web/scripts/**/*.html'
				],
				options : {
					mode:"VERIFY_ONLY"
				}
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-jshint');

	grunt.loadNpmTasks("grunt-jsbeautifier");

    // task setup 
    grunt.registerTask('default', []);
};