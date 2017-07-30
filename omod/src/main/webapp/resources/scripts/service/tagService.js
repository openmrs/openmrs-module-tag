angular.module('tagService', ['ngResource', 'uicommons.common'])
    .factory('Tag', function($resource) {
        return $resource("/" + OPENMRS_CONTEXT_PATH  + "/ws/rest/v1/tag/:uuid", {
            uuid: '@uuid'
        },{
            get: { method:'GET', isArray:false }
        });
    })
    .factory('TagService', function(Tag) {

        return {

            /**
             * Fetches Tag
             *
             * @param params to search against
             * @returns $promise of Tag object (REST ref representation by default)
             */
            getTag: function (params) {
                return Tag.query(params).$promise.then(function(res) {
                    return res.result;
                });
            },

            /**
             * Creates a new tag object
             *
             * @param tag the object to be created
             * @returns {*}
             */
            addTag: function (tag) {
                return new Tag(tag).$save();
            },

            /**
             * deletes the object with given uuid
             *
             * @param tag
             * @returns
             */
            deleteTag: function(tag) {
                var toDelete = new Tag({ uuid: tag.uuid });
                toDelete.$delete();
            }
        }
    });