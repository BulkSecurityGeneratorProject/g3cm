'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/formSkillMod', {
                    templateUrl: 'views/formSkillMods.html',
                    controller: 'FormSkillModController',
                    resolve:{
                        resolvedFormSkillMod: ['FormSkillMod', function (FormSkillMod) {
                            return FormSkillMod.query().$promise;
                        }],
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
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
