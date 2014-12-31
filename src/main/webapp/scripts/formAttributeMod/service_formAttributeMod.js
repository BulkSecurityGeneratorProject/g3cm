'use strict';

g3charactermanagerApp.factory('FormAttributeMod', function ($resource) {
        return $resource('app/rest/formAttributeMods/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
