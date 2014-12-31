'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/characterAttribute', {
                    templateUrl: 'views/characterAttributes.html',
                    controller: 'CharacterAttributeController',
                    resolve:{
                        resolvedCharacterAttribute: ['CharacterAttribute', function (CharacterAttribute) {
                            return CharacterAttribute.query().$promise;
                        }],
                        resolvedAttributeType: ['AttributeType', function (AttributeType) {
                            return AttributeType.query().$promise;
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
