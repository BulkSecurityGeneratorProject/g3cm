'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/characterAdvantage', {
                    templateUrl: 'views/characterAdvantages.html',
                    controller: 'CharacterAdvantageController',
                    resolve:{
                        resolvedCharacterAdvantage: ['CharacterAdvantage', function (CharacterAdvantage) {
                            return CharacterAdvantage.query().$promise;
                        }],
                        resolvedAdvantage: ['Advantage', function (Advantage) {
                            return Advantage.query().$promise;
                        }],
                        resolvedCharacter: ['Character', function (Character) {
                            return Character.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
