'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/skill', {
                    templateUrl: 'views/skills.html',
                    controller: 'SkillController',
                    resolve:{
                        resolvedSkill: ['Skill', function (Skill) {
                            return Skill.query().$promise;
                        }],
                        resolvedAttributeType: ['AttributeType', function (AttributeType) {
                            return AttributeType.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
