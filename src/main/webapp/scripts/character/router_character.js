'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/character', {
                    templateUrl: 'views/characters.html',
                    controller: 'CharacterController',
                    resolve:{
                        resolvedCharacter: ['Character', function (Character) {
                            return Character.query().$promise;
                        }],
                        resolvedCampaign: ['Campaign', function (Campaign) {
                            return Campaign.query().$promise;
                        }],
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }],
                        resolvedCharacterSkill: ['CharacterSkill', function (CharacterSkill) {
                            return CharacterSkill.query().$promise;
                        }],
                        resolvedCharacterAttribute: ['CharacterAttribute', function (CharacterAttribute) {
                            return CharacterAttribute.query().$promise;
                        }],
                        resolvedCharacterAdvantages: ['CharacterAdvantages', function (CharacterAdvantages) {
                            return CharacterAdvantages.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
