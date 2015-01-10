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
                        resolvedCharacterAdvantage: ['CharacterAdvantage', function (CharacterAdvantage) {
                            return CharacterAdvantage.query().$promise;
                        }],
                        resolvedCharacterSkill: ['CharacterSkill', function (CharacterSkill) {
                            return CharacterSkill.query().$promise;
                        }],
                        resolvedCharacterAttribute: ['CharacterAttribute', function (CharacterAttribute) {
                            return CharacterAttribute.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }],
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
                        }],
                        resolvedCampaign: ['Campaign', function (Campaign) {
                            return Campaign.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
            .when('/character/:characterId', {
                    templateUrl: 'views/character.html',
                    controller: 'CharacterDetailController',
                    resolve:{
                        resolvedCharacter: ['Character', function (Character) {
                            return Character.query().$promise;
                        }],
                        resolvedAdvantage: ['Advantage', function (Advantage) {
                            return Advantage.query().$promise;
                        }],
                        resolvedCharacterAdvantage: ['CharacterAdvantage', function (CharacterAdvantage) {
                            return CharacterAdvantage.query().$promise;
                        }],
//                        resolvedCharacterAdvantageByCharacter: ['CharacterAdvantageByCharacter', function (CharacterAdvantageByCharacter) {
//                            return CharacterAdvantageByCharacter.query().$promise;
//                        }],
                        resolvedCharacterSkill: ['CharacterSkill', function (CharacterSkill) {
                            return CharacterSkill.query().$promise;
                        }],
                        resolvedCharacterAttribute: ['CharacterAttribute', function (CharacterAttribute) {
                            return CharacterAttribute.query().$promise;
                        }],
                        resolvedAttributeType: ['AttributeType', function (AttributeType) {
                            return AttributeType.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }],
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
                        }],
                        resolvedCampaign: ['Campaign', function (Campaign) {
                            return Campaign.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })

        });
