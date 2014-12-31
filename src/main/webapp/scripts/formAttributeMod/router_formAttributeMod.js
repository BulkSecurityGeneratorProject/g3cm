'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/formAttributeMod', {
                    templateUrl: 'views/formAttributeMods.html',
                    controller: 'FormAttributeModController',
                    resolve:{
                        resolvedFormAttributeMod: ['FormAttributeMod', function (FormAttributeMod) {
                            return FormAttributeMod.query().$promise;
                        }],
                        resolvedForm: ['Form', function (Form) {
                            return Form.query().$promise;
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
