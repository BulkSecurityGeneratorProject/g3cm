'use strict';

g3charactermanagerApp.factory('CharacterAdvantage', function ($resource) {
        return $resource('app/rest/characterAdvantages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
