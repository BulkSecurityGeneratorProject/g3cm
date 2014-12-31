'use strict';

g3charactermanagerApp.controller('FormSkillModController', function ($scope, resolvedFormSkillMod, FormSkillMod, resolvedForm, resolvedSkill) {

        $scope.formSkillMods = resolvedFormSkillMod;
        $scope.forms = resolvedForm;
        $scope.skills = resolvedSkill;

        $scope.create = function () {
            FormSkillMod.save($scope.formSkillMod,
                function () {
                    $scope.formSkillMods = FormSkillMod.query();
                    $('#saveFormSkillModModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.formSkillMod = FormSkillMod.get({id: id});
            $('#saveFormSkillModModal').modal('show');
        };

        $scope.delete = function (id) {
            FormSkillMod.delete({id: id},
                function () {
                    $scope.formSkillMods = FormSkillMod.query();
                });
        };

        $scope.clear = function () {
            $scope.formSkillMod = {levelChange: null, id: null};
        };
    });
