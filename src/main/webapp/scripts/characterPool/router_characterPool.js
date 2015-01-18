'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/characterPool', {
                    templateUrl: 'views/characterPools.html',
                    controller: 'CharacterPoolController',
                    resolve:{
                        resolvedCharacterPool: ['CharacterPool', function (CharacterPool) {
                            return CharacterPool.query().$promise;
                        }],
                        resolvedCharacter: ['Character', function (Character) {
                            return Character.query().$promise;
                        }],
                        resolvedPool: ['Pool', function (Pool) {
                            return Pool.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
