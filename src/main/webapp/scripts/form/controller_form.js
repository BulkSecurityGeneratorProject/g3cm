'use strict';

g3charactermanagerApp.controller('FormController', function ($scope, resolvedForm, Form, resolvedAdvantage, resolvedFormAttributeMod, resolvedFormSkillMod) {

        $scope.forms = resolvedForm;
        $scope.advantages = resolvedAdvantage;
        $scope.formAttributeMods = resolvedFormAttributeMod;
        $scope.formSkillMods = resolvedFormSkillMod;

        $scope.create = function () {
            Form.save($scope.form,
                function () {
                    $scope.forms = Form.query();
                    $('#saveFormModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.form = Form.get({id: id});
            $('#saveFormModal').modal('show');
        };

        $scope.delete = function (id) {
            Form.delete({id: id},
                function () {
                    $scope.forms = Form.query();
                });
        };

        $scope.clear = function () {
            $scope.form = {name: null, incon: null, physicalChange: null, sortOrder: null, id: null};
        };
    });
