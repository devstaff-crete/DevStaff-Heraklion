window.cordova = { yup: true };
ionic.Platform.isReady = true;

angular.module('ionicApp', ['ionic'])

.config(function($stateProvider, $urlRouterProvider){

 $stateProvider

  .state('index', {
    url: '/',
    templateUrl: 'templates/page1.html'
  })

  .state('page2', {
    url: '/page2',
    templateUrl: 'templates/page2.html'
  });

  $urlRouterProvider.otherwise("/");

});

