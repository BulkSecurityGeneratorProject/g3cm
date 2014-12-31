'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/form', {
                    templateUrl: 'views/forms.html',
                    controller: 'FormController',
                    resolve:{
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
                        }],
                        resolvedAdvantage: ['Advantage', function (Advantage) {
                            return Advantage.query().$promise;
                        }],
                        resolvedFormAttributeMod: ['FormAttributeMod', function (FormAttributeMod) {
                            return FormAttributeMod.query().$promise;
                        }],
                        resolvedFormSkillMod: ['FormSkillMod', function (FormSkillMod) {
                            return FormSkillMod.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
