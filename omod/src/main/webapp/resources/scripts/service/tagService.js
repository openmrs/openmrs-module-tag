angular.module('tagService', ['ngResource'])
    .factory('Tag', function($resource) {
        return $resource("/" + OPENMRS_CONTEXT_PATH  + "/ws/rest/v1/tag/:uuid", {
            uuid: '@uuid'
        },{
            query: { method:'GET', isArray:false },
            delete: {method:'DELETE'}
        });
    })
    .factory('TagService', function(Tag) {

        return {

            /**
             * Fetches Tags
             *
             * @param params to search against
             * @returns $promise of array of matching Tags (REST ref representation by default)
             */
            getTags: function(params) {
                return Tag.query(params).$promise.then(function(res) {
                    return res.results;
                });
            },

            /**
             * Creates a new tag
             *
             * @param tag
             * @returns {Tag}
             */
            createTag: function(tag) {
                var created = new Tag(tag);
                created.$save();
                return created;

            },

            /**
             * Deletes a tag
             * @param tag must have a uuid property, but may be a minimal representation
             */
            deleteTag: function(tag) {
                Tag.delete({uuid: tag.uuid, purge:true});
            }
        }
    });