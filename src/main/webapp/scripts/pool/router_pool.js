'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/pool', {
                    templateUrl: 'views/pools.html',
                    controller: 'PoolController',
                    resolve:{
                        resolvedPool: ['Pool', function (Pool) {
                            return Pool.query().$promise;
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
