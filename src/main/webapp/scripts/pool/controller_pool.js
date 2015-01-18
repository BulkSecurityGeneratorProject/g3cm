'use strict';

g3charactermanagerApp.controller('PoolController', function ($scope, resolvedPool, Pool, resolvedAttributeType) {

        $scope.pools = resolvedPool;
        $scope.attributeTypes = resolvedAttributeType;

        $scope.create = function () {
            Pool.save($scope.pool,
                function () {
                    $scope.pools = Pool.query();
                    $('#savePoolModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.pool = Pool.get({id: id});
            $('#savePoolModal').modal('show');
        };

        $scope.delete = function (id) {
            Pool.delete({id: id},
                function () {
                    $scope.pools = Pool.query();
                });
        };

        $scope.clear = function () {
            $scope.pool = {name: null, color: null, id: null};
        };
    });
