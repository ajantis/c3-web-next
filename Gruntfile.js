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
		        			files: [
		        				'scripts/services/**/*.js',
		        				'scripts/directives/**/*.js',
		        				'scripts/controllers/**/*.js'
		        			]
		        		},
		        		setup: {
							cwd: '<%= globalConfig.dist %>',
		        			files: ['scripts/setup.js', 'scripts/app.js']
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
	        	src: '<%= globalConfig.dist %>/index.html',
		        dest: '<%= globalConfig.dist %>',
		        options: {
		        	beautify: true,
		        	scripts: {
		        		app: {
		        			cwd: '<%= globalConfig.dist %>',
		        			files: 'scripts/**/*.js'
		        		},
		        		// setup: {
		        		// 	cwd: '<%= globalConfig.dist %>',
		        		// 	files: ['scripts/**/*.js']	
		        		// }
		        		vendor: {
		        			files: 'vendor.js'
		        		}
		        	},
		        	styles: {
		        		app: {
		        			cwd: '<%= globalConfig.dist %>',
		        			files: 'styles/**/*.css'
		        		},
		        		vendor: {
		        			files: 'vendor.css'
		        		}

		        	}
		        }
	        }
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

		uglify: {
			options: {

			},
			prod: {
				files: [{
					expand: true,
			        cwd: '<%= globalConfig.src %>',
			        src: 'scripts/*.js',
			        dest: 'dest/scripts/config.js'
				}, {
					expand: true,
			        cwd: '<%= globalConfig.src %>',
			        src: 'scripts/controllers/**/*.js',
			        dest: 'dest/scripts/controllers.js'
				}, {
					expand: true,
			        cwd: '<%= globalConfig.src %>',
			        src: 'scripts/services/**/*.js',
			        dest: 'dest/scripts/services.js'
				}, {
					expand: true,
			        cwd: '<%= globalConfig.src %>',
			        src: 'scripts/directives/**/*.js',
			        dest: 'dest/scripts/directives.js'
				}]
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
    			//'usemin',
    			//'uglify',
    			//'htmlbuild:prod'
    		]);
    	} else {
    		grunt.log.error('Argument ' + arg + ' is not defined');
    	}
    });
};
