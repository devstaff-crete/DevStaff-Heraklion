'use strict';

module.exports = function(grunt) {
  require('jit-grunt')(grunt, {
    'validate-package': 'grunt-nsp-package',
    'validate-shrinkwrap': 'grunt-nsp-shrinkwrap'
  });

  grunt.initConfig({
    jshint: {
      options: {
        jshintrc: '.jshintrc'
      },
      gruntfile: {
        src: 'Gruntfile.js'
      },
      lib: {
        src: ['lib/**/*.js']
      },
      test: {
        src: ['test/**/*.js']
      },
    },

    jscs: {
      options: {
        config: '.jscsrc'
      },
      gruntfile: {
        files: {
          src: '<%= jshint.gruntfile.src %>'
        }
      },
      lib: {
        files: {
          src: '<%= jshint.lib.src %>'
        }
      },
      test: {
        files: {
          src: '<%= jshint.test.src %>'
        }
      }
    },

    mochaTest: {
      options: {
        globals: ['expect'],
        timeout: 3000,
        ignoreLeaks: false,
        ui: 'bdd',
        reporter: 'list',
        clearRequireCache: true
      },
      all: {
        src: ['test/**/*_spec.js']
      }
    }
  });

  grunt.registerTask('default', ['jscs', 'jshint', 'mochaTest']);
  grunt.registerTask('security', ['validate-package', 'validate-shrinkwrap']);
};
