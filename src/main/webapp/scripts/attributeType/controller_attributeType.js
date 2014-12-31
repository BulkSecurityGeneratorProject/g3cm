'use strict';

g3charactermanagerApp.controller('AttributeTypeController', function ($scope, resolvedAttributeType, AttributeType) {

        $scope.attributeTypes = resolvedAttributeType;

        $scope.create = function () {
            AttributeType.save($scope.attributeType,
                function () {
                    $scope.attributeTypes = AttributeType.query();
                    $('#saveAttributeTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.attributeType = AttributeType.get({id: id});
            $('#saveAttributeTypeModal').modal('show');
        };

        $scope.delete = function (id) {
            AttributeType.delete({id: id},
                function () {
                    $scope.attributeTypes = AttributeType.query();
                });
        };

        $scope.clear = function () {
            $scope.attributeType = {name: null, id: null};
        };
    });
