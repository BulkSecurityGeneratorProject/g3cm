'use strict';

g3charactermanagerApp.controller('AdvantageController', function ($scope, resolvedAdvantage, Advantage) {

        $scope.advantages = resolvedAdvantage;

        $scope.create = function () {
            Advantage.save($scope.advantage,
                function () {
                    $scope.advantages = Advantage.query();
                    $('#saveAdvantageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.advantage = Advantage.get({id: id});
            $('#saveAdvantageModal').modal('show');
        };

        $scope.delete = function (id) {
            Advantage.delete({id: id},
                function () {
                    $scope.advantages = Advantage.query();
                });
        };

        $scope.clear = function () {
            $scope.advantage = {name: null, description: null, basePoints: null, id: null};
        };
    });
