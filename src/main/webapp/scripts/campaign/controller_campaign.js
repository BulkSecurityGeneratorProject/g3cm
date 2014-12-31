'use strict';

g3charactermanagerApp.controller('CampaignController', function ($scope, resolvedCampaign, Campaign, resolvedUser, resolvedCharacter) {

        $scope.campaigns = resolvedCampaign;
        $scope.users = resolvedUser;
        $scope.characters = resolvedCharacter;

        $scope.create = function () {
            Campaign.save($scope.campaign,
                function () {
                    $scope.campaigns = Campaign.query();
                    $('#saveCampaignModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.campaign = Campaign.get({id: id});
            $('#saveCampaignModal').modal('show');
        };

        $scope.delete = function (id) {
            Campaign.delete({id: id},
                function () {
                    $scope.campaigns = Campaign.query();
                });
        };

        $scope.clear = function () {
            $scope.campaign = {name: null, description: null, note: null, id: null};
        };
    });
