'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/characterSkill', {
                    templateUrl: 'views/characterSkills.html',
                    controller: 'CharacterSkillController',
                    resolve:{
                        resolvedCharacterSkill: ['CharacterSkill', function (CharacterSkill) {
                            return CharacterSkill.query().$promise;
                        }],
                        resolvedCharacter: ['Character', function (Character) {
                            return Character.query().$promise;
                        }],
                        resolvedSkill: ['Skill', function (Skill) {
                            return Skill.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
