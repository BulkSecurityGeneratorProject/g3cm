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
                        resolvedCharacterAdvantage: ['CharacterAdvantage', function (CharacterAdvantage) {
                            return CharacterAdvantage.query().$promise;
                        }],
                        resolvedCharacterSkill: ['CharacterSkill', function (CharacterSkill) {
                            return CharacterSkill.query().$promise;
                        }],
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
