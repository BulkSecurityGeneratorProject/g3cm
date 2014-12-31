'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/attributeType', {
                    templateUrl: 'views/attributeTypes.html',
                    controller: 'AttributeTypeController',
                    resolve:{
                        resolvedAttributeType: ['AttributeType', function (AttributeType) {
                            return AttributeType.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
