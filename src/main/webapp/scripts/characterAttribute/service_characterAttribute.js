'use strict';

g3charactermanagerApp.factory('CharacterAttribute', function ($resource) {
        return $resource('app/rest/characterAttributes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
