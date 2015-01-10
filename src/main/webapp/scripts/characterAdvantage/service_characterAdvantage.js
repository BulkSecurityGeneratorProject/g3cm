'use strict';

g3charactermanagerApp.factory('CharacterAdvantage', function ($resource) {
	
        return $resource('app/rest/characterAdvantages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
//g3charactermanagerApp.factory('CharacterAdvantageByCharacter', function ($resource) {
//	
//    return $resource('app//rest/character/:characterid/characterAttributes/:id', {}, {
//        'query': { method: 'GET', isArray: true},
//        'get': { method: 'GET'}
//    });
//});


