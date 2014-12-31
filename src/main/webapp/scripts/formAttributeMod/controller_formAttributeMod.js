'use strict';

g3charactermanagerApp.controller('FormAttributeModController', function ($scope, resolvedFormAttributeMod, FormAttributeMod, resolvedForm, resolvedAttributeType) {

        $scope.formAttributeMods = resolvedFormAttributeMod;
        $scope.forms = resolvedForm;
        $scope.attributeTypes = resolvedAttributeType;

        $scope.create = function () {
            FormAttributeMod.save($scope.formAttributeMod,
                function () {
                    $scope.formAttributeMods = FormAttributeMod.query();
                    $('#saveFormAttributeModModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.formAttributeMod = FormAttributeMod.get({id: id});
            $('#saveFormAttributeModModal').modal('show');
        };

        $scope.delete = function (id) {
            FormAttributeMod.delete({id: id},
                function () {
                    $scope.formAttributeMods = FormAttributeMod.query();
                });
        };

        $scope.clear = function () {
            $scope.formAttributeMod = {mod: null, id: null};
        };
    });
