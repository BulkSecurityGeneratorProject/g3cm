'use strict';

g3charactermanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/campaign', {
                    templateUrl: 'views/campaigns.html',
                    controller: 'CampaignController',
                    resolve:{
                        resolvedCampaign: ['Campaign', function (Campaign) {
                            return Campaign.query().$promise;
                        }],
                        resolvedUser: ['User', function (User) {
                            return User.query().$promise;
                        }],
                        resolvedCharacter: ['Character', function (Character) {
                            return Character.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
