'use strict';

g3charactermanagerApp.factory('FormSkillMod', function ($resource) {
        return $resource('app/rest/formSkillMods/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
