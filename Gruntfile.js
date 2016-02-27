module.exports = function(grunt) {

	var globalConfig = {
		src: 'c3web-server/src/main/resources/web',
		dist: 'c3web-server/src/main/resources/dist',
		resources: 'c3web-server/src/main/resources'
	};

	require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),

		globalConfig: globalConfig,

		jshint: {
			dev: {        
				src: [
					'<%= globalConfig.src %>/scripts/**/*.js',
					'<%= globalConfig.src %>/styles/**/*.css'
				]
			}
		},

		jsbeautifier: {
			dev: {
				files: [
					'<%= globalConfig.src %>/scripts/**/*.js',
					'<%= globalConfig.src %>/styles/**/*.css',
					'<%= globalConfig.src %>/scripts/**/*.html'
				]
			},
			prod: {
				files: [
					'<%= globalConfig.src %>/scripts/**/*.js',
					'<%= globalConfig.src %>/styles/**/*.css',
					'<%= globalConfig.src %>/scripts/**/*.html'
				],
				options: {
					mode:"VERIFY_ONLY"
				}
			}
		},

		wiredep: {
			dev: {
			    src: [
			      '<%= globalConfig.dist %>/index.html'
			    ],
			    directory: '<%= globalConfig.dist %>/bower_components'
		  	}
		},

		htmlbuild: {
			dev: {
				src: '<%= globalConfig.dist %>/index.html',
		        dest: '<%= globalConfig.dist %>',
		        options: {
		        	logOptionals: true,
		        	beautify: true,
		        	scripts: {
		        		app: {
		        			cwd: '<%= globalConfig.dist %>',
		        			files: 'scripts/**/*.js'
		        		},
		        		vendor: {
		        			files: '!*'
		        		}
		        	},
		        	styles: {
		        		app: {
		        			cwd: '<%= globalConfig.dist %>',
		        			files: 'styles/**/*.css'
		        		},
		        		vendor: {
		        			files: '!*'
		        		}

		        	}
		        }	
			},
	        prod: {
	        }
	        // options: {
	        //     scripts: {
	        //         libs: 'libs/*.js',
	        //         app: 'app/*.js'
	        //     },
	        //     styles: {
	        //         libs: [ 
	        //             'css/lib1.css',
	        //             'css/lib2.css'
	        //         ],
	        //         app: 'css/app.css'
	        //     }
	        // }
    	},

		copy: {
		  	dev: {
		    	files: [
		    		{ 
		    			expand: true, 
		    			src: [
		    				'**'
		    			], 
		    			cwd: '<%= globalConfig.src %>',
		    			dest: '<%= globalConfig.dist %>'
		    		}
		    	]
		  	},
		  	prod: {

		  	}
		},

		githooks: {
		    all: {
		    //  'pre-commit': 'git-pre-commit'
		    }
		}
	});

    // task setup 
    grunt.registerTask('default', []);

    grunt.registerTask('build', 'Build project', function (arg){
    	if(arg === 'dev'){
    		grunt.task.run([
    			//'jsbeautifier:dev'
    			'copy:dev',
    			'wiredep:dev',
    			'htmlbuild:dev'
    		]);
    	} else if (arg === 'prod') {
    		grunt.task.run([
    		]);
    	} else {
    		grunt.log.error('Argument ' + arg + ' is not defined');
    	}
    });
};
