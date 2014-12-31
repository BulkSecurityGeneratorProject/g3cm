'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/advantage', {
                    templateUrl: 'views/advantages.html',
                    controller: 'AdvantageController',
                    resolve:{
                        resolvedAdvantage: ['Advantage', function (Advantage) {
                            return Advantage.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
