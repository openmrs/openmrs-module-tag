
var app  = angular.module('Tag',['tagService']);
    app.controller('tagCtrl',function ($scope,TagService) {
       $scope.tag1="NO"
        $scope.init = function(personUuid) {
            $scope.thisPersonUuid = personUuid;
            TagService.getTags({objectType:'org.openmrs.Person', objectUuid:personUuid}).then(function(results) {
                $scope.tag1 = "YES";
                $scope.tags = results;
             });
/*            var Tag1 = {tag:'Created', objectType: 'org.openmrs.Person', objectUuid: personUuid};
            TagService.createTag(Tag1).then(function (result) {
                $scope.tag1="Inside";
                $scope.cp=result;
            });
  */      }
        if ($scope.tags == null){
            $scope.tags = null;
        }
    });

